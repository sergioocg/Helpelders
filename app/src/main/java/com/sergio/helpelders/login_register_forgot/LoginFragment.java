package com.sergio.helpelders.login_register_forgot;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;

import com.google.android.material.textfield.TextInputEditText;
import com.sergio.helpelders.R;
import com.sergio.helpelders.viewmodel.AutenticacionViewModel;

import java.util.Calendar;

public class LoginFragment extends Fragment {
    /**
     * Atributos
     */
    private ConstraintLayout constraintLayout;
    private AutenticacionViewModel autenticacionViewModel;
    private TextInputEditText nTelfEditText, passEditText;
    private Button loginButton, registerButton;
    private TextView forgotPass, tituloTextView;

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

        nTelfEditText = view.findViewById(R.id.text_ntelf);
        passEditText = view.findViewById(R.id.text_contrasena);

        loginButton = view.findViewById(R.id.btn_iniciar);
        registerButton = view.findViewById(R.id.btn_registrar);
        forgotPass = view.findViewById(R.id.btn_recuperar_contrasena);

        autenticacionViewModel = ViewModelProviders.of(requireActivity()).get(AutenticacionViewModel.class);
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
        Log.i("TIME", String.valueOf(timeOfDay));

        if(timeOfDay >= 7 && timeOfDay < 12) {
            // Morning
            constraintLayout.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.good_morning_img));
            tituloTextView.setText("¡ Buenos días !");
        }
        else {
            if(timeOfDay >= 12 && timeOfDay < 20) {
                // Afternoon - Algo de bienvenida

            }
            else {
                // Night
                constraintLayout.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.good_night_img));
                tituloTextView.setText("¡ Buenas noches !");
            }
        }
    }

    private void setListeners() {
        registerButton.setOnClickListener(view12 -> Navigation.findNavController(view12).navigate(R.id.registerFragment));

        loginButton.setOnClickListener(view1 -> {
            autenticacionViewModel.iniciarSesion(nTelfEditText.getText().toString(), passEditText.getText().toString());

            autenticacionViewModel.estadoDeLaAutenticacion.observe(getViewLifecycleOwner(), estadoDeLaAutenticacion -> {
                switch(estadoDeLaAutenticacion){
                    case AUTENTICADO:
                        Navigation.findNavController(view1).navigate(R.id.homeFragment);
                        break;

                    case AUTENTICACION_INVALIDA:
                        Toast.makeText(getContext(), "CREDENCIALES NO VALIDAS", Toast.LENGTH_SHORT).show();
                        break;
                }
            });
        });

        forgotPass.setOnClickListener(view13 -> Navigation.findNavController(view13).navigate(R.id.forgotРasswordFragment));

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
    }
}
