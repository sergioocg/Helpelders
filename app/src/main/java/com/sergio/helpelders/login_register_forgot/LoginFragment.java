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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthEmailException;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.firestore.DocumentSnapshot;
import com.ontbee.legacyforks.cn.pedant.SweetAlert.SweetAlertDialog;
import com.ornach.nobobutton.NoboButton;
import com.sergio.helpelders.R;
import com.sergio.helpelders.Util;

import java.util.Calendar;

import es.dmoral.toasty.Toasty;

public class LoginFragment extends Util {
    /**
     * Atributos
     */
    private NoboButton loginButton, registerButton;
    private TextView forgotPass, tituloTextView;

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
    }

    /**
     * A través de una instancia del Calendario obtenemos la hora actual.
     * Dependiendo de la hora, se cambiará el fondo y texto del layout de Login
     */
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

    // https://www.techotopia.com/index.php/Handling_Firebase_Authentication_Errors_and_Failures
    public void iniciarUsuarioFirebase() {
        mAuth.signInWithEmailAndPassword(emailEditText.getText().toString(), passEditText.getText().toString())
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {
                    showAlertDialog(SweetAlertDialog.SUCCESS_TYPE, "¡ Bienvenido !", ""); // ¿AlertDialog o Toasty?
                    Navigation.findNavController(getView()).navigate(R.id.homeFragment);
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) { // Llevar a registrar email
                if(e instanceof FirebaseAuthInvalidUserException) {
                    showErrorToast("Email incorrecto o no está registrado");
                }
                else {
                    if(e instanceof FirebaseAuthInvalidCredentialsException) {
                        showErrorToast("Contraseña incorrecta");
                    }
                }
            }
        });
    }

    /**
     * Métodos de Fragment
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setInitWidgets(view);
        setLoginScreen(tituloTextView);
        setListeners();
    }
}