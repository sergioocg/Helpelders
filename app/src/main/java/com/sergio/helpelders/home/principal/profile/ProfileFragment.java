package com.sergio.helpelders.home.principal.profile;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.sergio.helpelders.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {
    private TextView nombreTextView, btnVerPerfil;
    private ImageView btnGoBack;
    private LinearLayout lCerrarSesion;

    public ProfileFragment() {}


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        btnGoBack = view.findViewById(R.id.btn_volver);
        btnGoBack.setOnClickListener(view1 -> Navigation.findNavController(view1).popBackStack());

        nombreTextView = view.findViewById(R.id.nombre);
        btnVerPerfil = view.findViewById(R.id.verPefilCompleto);
        lCerrarSesion = view.findViewById(R.id.linearCerrarSesion);

        btnVerPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.fullProfileFragment);
            }
        });

        FirebaseFirestore fStore = FirebaseFirestore.getInstance();
        FirebaseAuth mAuth = FirebaseAuth.getInstance();

        DocumentReference user = fStore.collection("usuarios").document(mAuth.getCurrentUser().getEmail());
        user.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()) {
                    DocumentSnapshot doc = task.getResult();
                    nombreTextView.setText(doc.getString("nombre") + " " + doc.getString("apellidos"));
                }
            }
        });

        lCerrarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                Navigation.findNavController(v).navigate(R.id.loginFragment);
            }
        });
    }
}
