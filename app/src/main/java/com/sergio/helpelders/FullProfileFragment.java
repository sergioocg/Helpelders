package com.sergio.helpelders;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;


/**
 * A simple {@link Fragment} subclass.
 */
public class FullProfileFragment extends Fragment {
    private ImageView btnVolver;
    private TextView nombreApellidosTextView, localidadTextView, fechaNacTextView,
            direccionTextView, codPostTextView, sexoTextView, tipoUsuarioTextView, emailTextView;

    public FullProfileFragment() {}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_full_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        btnVolver = view.findViewById(R.id.btn_volver);
        nombreApellidosTextView = view.findViewById(R.id.nombre_apellidos);
        localidadTextView = view.findViewById(R.id.localidad);
        fechaNacTextView = view.findViewById(R.id.fechaNacimiento);
        direccionTextView = view.findViewById(R.id.direccion);
        codPostTextView = view.findViewById(R.id.codPost);
        sexoTextView = view.findViewById(R.id.tipoSexo);
        tipoUsuarioTextView = view.findViewById(R.id.tipoUsuario);
        emailTextView = view.findViewById(R.id.email);


        btnVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).popBackStack();
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
}
