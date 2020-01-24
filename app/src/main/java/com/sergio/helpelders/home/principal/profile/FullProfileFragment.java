package com.sergio.helpelders.home.principal.profile;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.Navigation;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.sergio.helpelders.R;
import com.sergio.helpelders.Util;

public class FullProfileFragment extends Util {
    private TextView localidadTextView, fechaNacTextView,
            direccionTextView, codPostTextView, sexoTextView, tipoUsuarioTextView, emailTextView;

    public FullProfileFragment() {}

    private void setInitWidgets(View view) {
        volverButton = view.findViewById(R.id.btn_volver);
        nombreApellidosTextView = view.findViewById(R.id.nombre_apellidos);
        localidadTextView = view.findViewById(R.id.localidad);
        fechaNacTextView = view.findViewById(R.id.fechaNacimiento);
        direccionTextView = view.findViewById(R.id.direccion);
        codPostTextView = view.findViewById(R.id.codPost);
        sexoTextView = view.findViewById(R.id.tipoSexo);
        tipoUsuarioTextView = view.findViewById(R.id.tipoUsuario);
        emailTextView = view.findViewById(R.id.email);

        mAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
    }

    private void setListeners() {
        volverButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).popBackStack();
            }
        });
    }

    // Creo una colección llamada usuarios, y cada usuario será identificado con el email.
    // Así en Authentication de Firebase es más sencillo de identificar.
    private void rellenarPerfil() {
        user = fStore.collection("usuarios").document(mAuth.getCurrentUser().getEmail());
        user.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()) {
                    DocumentSnapshot doc = task.getResult();

                    nombreApellidosTextView.setText(doc.getString("nombre") + " " + doc.getString("apellidos"));
                    localidadTextView.setText(doc.getString("localidad"));
                    fechaNacTextView.setText(doc.getString("fechaNac"));
                    direccionTextView.setText(doc.getString("direccion"));
                    codPostTextView.setText(doc.getString("codPost"));
                    sexoTextView.setText(doc.getString("sexo"));
                    tipoUsuarioTextView.setText(doc.getString("rolUsuario"));
                    emailTextView.setText(doc.getString("email"));
                }
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_full_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setInitWidgets(view);
        setListeners();
        rellenarPerfil();
    }
}
