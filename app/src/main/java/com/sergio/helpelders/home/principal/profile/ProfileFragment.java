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
import com.sergio.helpelders.Util;


public class ProfileFragment extends Util {
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
        linearLayout = view.findViewById(R.id.linearCerrarSesion);
    }

    private void setListeners(View view) {
        btnVerPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.fullProfileFragment);
            }
        });

        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                Navigation.findNavController(v).navigate(R.id.loginFragment);
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
    }
}
