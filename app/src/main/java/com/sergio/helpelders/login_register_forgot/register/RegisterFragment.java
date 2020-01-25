package com.sergio.helpelders.login_register_forgot.register;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.Navigation;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.firestore.DocumentReference;
import com.ornach.nobobutton.NoboButton;
import com.sergio.helpelders.R;
import com.sergio.helpelders.Util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class RegisterFragment extends Util {
    private NoboButton registerButton;

    private void setInitWidgets(@NonNull View view) {
        linearLayout = view.findViewById(R.id.container);

        volverButton = view.findViewById(R.id.btn_volver);

        nombreEditText = view.findViewById(R.id.text_nombre);
        apellidosEditText = view.findViewById(R.id.text_apellidos);

        direccionEditText = view.findViewById(R.id.text_direccion);
        cpEditText = view.findViewById(R.id.text_direccion_cp);
        localidadEditText = view.findViewById(R.id.text_direccion_localidad);

        fechaNacEditText = view.findViewById(R.id.text_fechanac);

        emailEditText = view.findViewById(R.id.text_ntelf);
        passEditText = view.findViewById(R.id.text_contrasena);

        registerButton = view.findViewById(R.id.btn_registrar);

        hombreRb = view.findViewById(R.id.hombre);
        abueloRb = view.findViewById(R.id.abuelo);
    }

    private void setListeners() {
        volverButton.setOnClickListener(view12 -> getFragmentManager().popBackStack());
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(comprobarDatosUsuario()) {
                    registrarUsuarioFirebase();
                    registrarUsuarioFirestore();
                }

            }
        });
    }

    private void setFormatoFecha() {
        String myFormat = "dd/MM/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.UK);

        fechaNacEditText.setText(sdf.format(calendar.getTime()));
    }

    private void setFechaNacimiento() {
        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                setFormatoFecha();
            }
        };

        fechaNacEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(requireContext(), date, calendar
                        .get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }

    // Esto quizá se pueda hacer en if y queda más corto
    private boolean comprobarEditText(EditText campoEditText, String campoString) {
        String nombreCampo = "";
        if(campoEditText.getText().toString().length() > 0) {
            switch(campoString) {
                case "nombre":
                    nombre = campoEditText.getText().toString();
                    break;

                case "apellidos":
                    apellidos = campoEditText.getText().toString();
                    break;

                case "fechaNac":
                    fechaNac = campoEditText.getText().toString();
                    break;

                case "direccion":
                    direccion = campoEditText.getText().toString();
                    break;

                case "localidad":
                    localidad = campoEditText.getText().toString();
                    break;

                case "codPost":
                    codPost = campoEditText.getText().toString();
                    break;
            }
        }
        else {
            switch(campoString) {
                case "nombre":
                    nombreCampo = "Nombre";
                    break;

                case "apellidos":
                    nombreCampo = "Apellidos";
                    break;

                case "fechaNac":
                    nombreCampo = "Fecha de nacimiento";
                    break;

                case "direccion":
                    nombreCampo = "Dirección";
                    break;

                case "localidad":
                    nombreCampo = "Localidad";
                    break;

                case "codPost":
                    nombreCampo = "Código Postal";
                    break;
            }

            showErrorToast("El campo " + nombreCampo.toUpperCase() + " no puede estar vacío");
            return false;
        }
        return true;
    }

    private boolean comprobarDatosUsuario() {
        boolean usuarioOK = false;
        if(comprobarEditText(nombreEditText, "nombre")) {
            if(comprobarEditText(apellidosEditText, "apellidos")) {
                if(comprobarEditText(fechaNacEditText, "fechaNac")) {
                    if(comprobarEditText(direccionEditText, "direccion")) {
                        if(comprobarEditText(localidadEditText, "localidad")) {
                            if(comprobarEditText(cpEditText, "codPost")) {
                                if(comprobarEmail()) {
                                    if(comprobarPass()) {
                                        usuarioOK = true;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return usuarioOK;
    }

    private void registrarUsuarioFirebase() {
        mAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {
                    showSuccessToast("Usuario registrado");
                    Navigation.findNavController(getView()).popBackStack();
                }
                else {
                    if(task.getException() instanceof FirebaseAuthUserCollisionException) { // email ya registrado
                        showErrorToast("El email ya existe");
                    }
                }
            }
        });
    }

    /**
     * https://gerardfp.github.io/mp08/p13/#3
     */
    private void guardarUsuarioFirestone(Usuario u) {
        DocumentReference documentReference = fStore.collection("usuarios").document(u.getEmail());
        Map<String, Object> user = new HashMap<>();
        user.put("nombre", u.getNombre());
        user.put("apellidos", u.getApellidos());
        user.put("fechaNac", u.getFechaNac());
        user.put("sexo", u.getSexo());
        user.put("rolUsuario", u.getRolUsuario());
        user.put("direccion", u.getDireccion());
        user.put("localidad", u.getLocalidad());
        user.put("codPost", u.getCp());
        user.put("email", u.getEmail());

        documentReference.set(u).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d("TAG", "Usuario registrado en Firestore");
            }
        });
    }

    private void registrarUsuarioFirestore() {
        Usuario u = new Usuario(nombre, apellidos, fechaNac, sexo, rolUsuario, direccion, localidad, codPost, email);
        guardarUsuarioFirestone(u);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_register, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setInitWidgets(view);
        setRegisterScreen();
        setListeners();

        setFechaNacimiento();

    }
}
