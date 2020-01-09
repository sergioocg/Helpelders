package com.sergio.helpelders.login_register_screen;

import android.os.Build;
import android.os.Bundle;
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
    private ConstraintLayout constraintLayout;

    private AutenticacionViewModel autenticacionViewModel;

    private TextInputEditText nTelfEditText, passEditText;
    private Button loginButton, registerButton;
    private TextView forgotРass, tituloTextView;


    ////////////////////////////////////////////////////////////////////////////////////////////////

    public LoginFragment() { }

    /////////////////////////////////////// MÉTODOS ////////////////////////////////////////////////


    // Agradecimientos
    // https://www.youtube.com/watch?v=Tk_zDJCrRSY&t=1423s
    // https://www.uplabs.com/posts/login-ui-ux-504e2015-ee34-45ec-ae1a-286d166ed6e6
    public void setLoginScreen(@NonNull View view) {
        constraintLayout = view.findViewById(R.id.container);
        tituloTextView = view.findViewById(R.id.text_titulo);

        Calendar c = Calendar.getInstance();
        int timeOfDay = c.get(Calendar.HOUR_OF_DAY);

        if(timeOfDay >= 0 && timeOfDay < 12) {
            // Morning
            constraintLayout.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.good_morning_img));
            tituloTextView.setText("¡ Buenos días !");
        }
        else {
            if(timeOfDay >= 12 && timeOfDay < 20) {
                // Afternoon

            }
            else {
                if(timeOfDay >= 20 && timeOfDay <= 24) {
                    // Night
                    constraintLayout.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.good_night_img));
                    tituloTextView.setText("¡ Buenas noches !");
                }
            }
        }

        if(Build.VERSION.SDK_INT >= 21) {
            view.setSystemUiVisibility(View.GONE);

        }

    }

    ////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_login, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setLoginScreen(view);


        autenticacionViewModel = ViewModelProviders.of(requireActivity()).get(AutenticacionViewModel.class);

        nTelfEditText = view.findViewById(R.id.text_ntelf);
        passEditText = view.findViewById(R.id.text_contrasena);
        loginButton = view.findViewById(R.id.btn_iniciar);
        registerButton = view.findViewById(R.id.btn_registrar);
        forgotРass = view.findViewById(R.id.btn_recuperar_contrasena);

        registerButton.setOnClickListener(view12 -> Navigation.findNavController(view12).navigate(R.id.registerFragment));

        loginButton.setOnClickListener(view1 -> {
            autenticacionViewModel.iniciarSesion(nTelfEditText.getText().toString(), passEditText.getText().toString());

            autenticacionViewModel.estadoDeLaAutenticacion.observe(getViewLifecycleOwner(), estadoDeLaAutenticacion -> {
                switch (estadoDeLaAutenticacion){
                    case AUTENTICADO:
                        Navigation.findNavController(view1).navigate(R.id.homeFragment);
                        break;

                    case AUTENTICACION_INVALIDA:
                        Toast.makeText(getContext(), "CREDENCIALES NO VALIDAS", Toast.LENGTH_SHORT).show();
                        break;
                }
            });
        });


        forgotРass.setOnClickListener(view13 -> Navigation.findNavController(view13).navigate(R.id.forgotРasswordFragment));
    }
}
