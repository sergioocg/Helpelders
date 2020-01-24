package com.sergio.helpelders.login_register_forgot;

import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.ornach.nobobutton.NoboButton;
import com.sergio.helpelders.R;

import java.util.Calendar;

import es.dmoral.toasty.Toasty;


public class LoginFragment extends Fragment {
    /**
     * Atributos
     */
    private ConstraintLayout constraintLayout;
    private TextInputEditText emailEditText, passEditText;
    private NoboButton loginButton, registerButton;
    private TextView forgotPass, tituloTextView;

    private FirebaseAuth mAuth;

    private String email, pass;

    /**
     * Constructor
     */
    public LoginFragment() { }

    /**
     * Inicializa todos los recursos de la interfaz
     */
    private void setInitWidgets(@NonNull View view) {
        constraintLayout = view.findViewById(R.id.container);
        tituloTextView = view.findViewById(R.id.text_titulo);

        emailEditText = view.findViewById(R.id.text_email);
        passEditText = view.findViewById(R.id.text_pass);

        loginButton = view.findViewById(R.id.btn_iniciar);
        registerButton = view.findViewById(R.id.btn_registrar);
        forgotPass = view.findViewById(R.id.btn_recuperar_contrasena);

        mAuth = FirebaseAuth.getInstance();
    }

    /**
     * A través de una instancia del Calendario obtenemos la hora actual.
     * Dependiendo de la hora, se cambiará el fondo y texto del layout de Login
     */
    // Agradecimientos
    // https://www.youtube.com/watch?v=Tk_zDJCrRSY&t=1423s
    // https://www.uplabs.com/posts/login-ui-ux-504e2015-ee34-45ec-ae1a-286d166ed6e6
    private void setLoginScreen() {
        Calendar c = Calendar.getInstance();
        int timeOfDay = c.get(Calendar.HOUR_OF_DAY);
        //Log.i("TIME", String.valueOf(timeOfDay));

        if(timeOfDay >= 7 && timeOfDay < 12) {
            // Morning
            constraintLayout.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.good_morning_img));
            tituloTextView.setText("¡ Buenos días !");
        }
        else {
            if(timeOfDay >= 12 && timeOfDay < 20) {
                // Afternoon - Algo de bienvenida

            }
            else {
                // Night
                constraintLayout.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.good_night_img));
                tituloTextView.setText("¡ Buenas noches !");
            }
        }
    }

    private void setListeners() {
        registerButton.setOnClickListener(view12 -> Navigation.findNavController(view12).navigate(R.id.registerFragment));

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(comprobarEmail()) {
                    if(comprobarPass()) {
                        iniciarUsuarioFirebase();
                    }
                }
            }
        });

        forgotPass.setOnClickListener(view13 -> Navigation.findNavController(view13).navigate(R.id.forgotРasswordFragment));
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

    public void iniciarUsuarioFirebase() {
        mAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {
                    Toasty.success(requireContext(), "Usuario correcto").show();
                    Navigation.findNavController(getView()).navigate(R.id.homeFragment);
                }
                else {
                    // / Mostrar AlerDiaglo, quieres registrarte, y llevar a Registrar,
                    Toasty.error(requireContext(), "Credenciales invalidas").show();
                }
            }
        });
    }

    /**
     * Métodos sobreescritos de la clase Fragment
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setInitWidgets(view);
        setLoginScreen();
        setListeners();

        //emailEditText.setText("admin@admin.es");
        //passEditText.setText("123456");

    }
}