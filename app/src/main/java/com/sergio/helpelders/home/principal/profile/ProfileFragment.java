package com.sergio.helpelders.home.principal.profile;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.ontbee.legacyforks.cn.pedant.SweetAlert.SweetAlertDialog;
import com.sergio.helpelders.R;
import com.sergio.helpelders.Util;
import com.sergio.helpelders.messages.ListAdapterChats;

import es.dmoral.toasty.Toasty;


public class ProfileFragment extends Util {
    // Adapter
    ListView listReviews;
    ListAdapterReviews reviewsAdapter;
    String[] name,message;

    int[] image;

    /**
     * Atributos
     */
    private TextView btnVerPerfil;

    /**
     * Constructor
     */
    public ProfileFragment() {}

    /**
     * Métodos
     */
    private void setInitWidgets(View view) {
        nombreApellidosTextView = view.findViewById(R.id.nombre);
        btnVerPerfil = view.findViewById(R.id.verPefilCompleto);
    }

    private void setListeners(View view) {
        btnVerPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.fullProfileFragment);
            }
        });
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setInitWidgets(view);
        setListeners(view);

        fStore = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        user = fStore.collection("usuarios").document(mAuth.getCurrentUser().getEmail());
        user.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()) {
                    DocumentSnapshot doc = task.getResult();
                    nombreApellidosTextView.setText(doc.getString("nombre") + " " + doc.getString("apellidos"));
                }
            }
        });

        // Tendría que poner algo para cambiar el color de los comentarios
        name = new String[]{ "Moshi", "Mushu", "Georgina"};

        message = new String[]{" servicio excelente.", " hemos pasado un rato entretenido.", " menuda tarde más amena."};


        image = new int[] {R.drawable.profile_image,
                R.drawable.profile_image, R.drawable.profile_image};


        // Locate the ListView in listReviewsview_main.xml
        listReviews = (ListView)view.findViewById(R.id.listReviews);

        // Pass results to ListViewAdapter Class
        reviewsAdapter = new ListAdapterReviews(requireContext(), name,message,image);
        // Binds the Adapter to the ListView
        listReviews.setAdapter(reviewsAdapter);
        // Capture ListView item click
        listReviews.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                showAlertDialog(SweetAlertDialog.NORMAL_TYPE, "Comentario de " + name[position], message[position]);
            }

        });

    }
}
