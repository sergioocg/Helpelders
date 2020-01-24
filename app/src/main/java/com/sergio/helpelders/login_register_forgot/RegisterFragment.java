package com.sergio.helpelders.login_register_forgot;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.ornach.nobobutton.NoboButton;
import com.sergio.helpelders.R;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import es.dmoral.toasty.Toasty;

public class RegisterFragment extends Fragment {
    private LinearLayout linearLayout;


    private ImageView volverButton;
    private EditText nombreEditText, apellidosEditText, direccionEditText, cpEditText, localidadEditText, fechaNacEditText, emailEditText, passEditText;
    private NoboButton registerButton;
    private RadioButton hombreRb, mujerRb, abueloRb, voluntarioRb;

    final Calendar myCalendar = Calendar.getInstance();

    private FirebaseAuth mAuth;
    private FirebaseFirestore fStore;
    private Boolean usuarioOk = false;

    private String email, pass;


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
        mujerRb = view.findViewById(R.id.mujer);

        abueloRb = view.findViewById(R.id.abuelo);
        voluntarioRb = view.findViewById(R.id.voluntario);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
    }

    private void setLoginScreen() {
        Calendar c = Calendar.getInstance();
        int timeOfDay = c.get(Calendar.HOUR_OF_DAY);

        if(timeOfDay >= 7 && timeOfDay < 12) {
            // Morning
            linearLayout.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.good_morning_img));
        }
        else {
            if(timeOfDay >= 12 && timeOfDay < 20) {
                // Afternoon - Algo de bienvenida

            }
            else {
                // Night
                linearLayout.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.good_night_img));
            }
        }
    }

    private void setListeners() {
        volverButton.setOnClickListener(view12 -> getFragmentManager().popBackStack());
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Firebase
                if(comprobarEmail()) {
                    if(comprobarPass()){
                        registrarUsuarioFirebase(); // Esto guarda el email del usuario en Firebase
                        registrarUsuario(); // En Firestore
                    }
                }

            }
        });
    }

    // https://gerardfp.github.io/mp08/p13/#3
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

    // Aquí hacer comprobaciones en un futuro
    private void registrarUsuario() {
        String nombre = "", apellidos = "", fechaNac = "", sexo = "", rolUsuario = "", direccion = "",
                localidad = "", codPost = "", email = "";

        if(!nombreEditText.getText().toString().isEmpty()) {
            nombre = nombreEditText.getText().toString();
        }
        else {
            Toasty.error(requireContext(), "El campo Nombre no puede estar vacío");
        }

        if(!apellidosEditText.getText().toString().isEmpty()) {
            apellidos = apellidosEditText.getText().toString();
        }
        else {
            Toasty.error(requireContext(), "El campo Apellidos no puede estar vacío");
        }

        if(!fechaNacEditText.getText().toString().isEmpty()) {
            fechaNac = fechaNacEditText.getText().toString();
        }
        else {
            Toasty.error(requireContext(), "El campo Fecha de nacimiento no puede estar vacío");
        }

        if(hombreRb.isChecked()) {
            sexo = "Hombre";
        }
        else {
            sexo = "Mujer";
        }

        if(abueloRb.isChecked()) {
            rolUsuario = "Abuelo";
        }
        else {
            rolUsuario = "Voluntario";
        }

        if(!direccionEditText.getText().toString().isEmpty()) {
            direccion = direccionEditText.getText().toString();
        }
        else {
            Toasty.error(requireContext(), "El campo Dirección no puede estar vacío");
        }

        if(!localidadEditText.getText().toString().isEmpty()) {
            localidad = localidadEditText.getText().toString();
        }
        else {
            Toasty.error(requireContext(), "El campo Localidad no puede estar vacío");
        }

        if(!cpEditText.getText().toString().isEmpty()) {
            codPost = cpEditText.getText().toString();
        }
        else {
            Toasty.error(requireContext(), "El campo CP no puede estar vacío");
        }

        if(!emailEditText.getText().toString().isEmpty()) {
            email = emailEditText.getText().toString();
        }
        else {
            Toasty.error(requireContext(), "El campo Localidad no puede estar vacío");
        }

        Usuario u = new Usuario(nombre, apellidos, fechaNac, sexo, rolUsuario, direccion, localidad, codPost, email);

        guardarUsuarioFirestone(u);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_register, container, false);
    }

    private void updateLabel() {
        String myFormat = "dd/MM/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.UK);

        fechaNacEditText.setText(sdf.format(myCalendar.getTime()));
    }

    private boolean comprobarEmail() {
        boolean userOk = false;

        try {
            email = emailEditText.getText().toString();
            //Log.e("LOGIN", "Email: " + email.length());

            if(email.length() == 0) { // Email vacío
                emailEditText.requestFocus();
                Toasty.error(requireContext(), "El email no puede estar vacío").show();
            }
            else {
                if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    emailEditText.requestFocus();
                    Toasty.error(requireContext(), "Email invalido").show();
                }
                else {
                    userOk = true;
                    passEditText.requestFocus();
                }
            }
        }catch(Exception e) {
            e.printStackTrace();
        }

        return userOk;
    }

    private boolean comprobarPass() {
        boolean passOk = false;

        try {
            pass = passEditText.getText().toString();

            if(pass.length() == 0) { // Email vacío
                passEditText.requestFocus();
                Toasty.error(requireContext(), "La contraseña no puede estar vacía").show();
            }
            else {
                if(pass.length() < 6) {
                    passEditText.requestFocus();
                    Toasty.error(requireContext(), "La contraseña debe tener 6 carácteres").show();
                }
                else {
                    passOk = true;
                }
            }

        }catch(Exception e) {
            e.printStackTrace();
        }

        return passOk;
    }

    private void registrarUsuarioFirebase() {
        mAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {
                    Toasty.success(getContext(), "Usuario registrado.").show();
                    Navigation.findNavController(getView()).popBackStack();
                    usuarioOk = true;
                }
                else {
                    if(task.getException() instanceof FirebaseAuthUserCollisionException) { // email ya registrado
                        Toasty.error(requireContext(), "El email ya exitse.").show();
                        emailEditText.requestFocus();
                        usuarioOk = false;
                    }
                }
            }
        });
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setInitWidgets(view);
        setLoginScreen();
        setListeners();

        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, month);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }
        };

        fechaNacEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(requireContext(), date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        // Log de fragments
        //log(view);

    }
}
