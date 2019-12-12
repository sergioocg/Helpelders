package com.sergio.helpelders.login_register_screen;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;

import com.sergio.helpelders.R;
import com.sergio.helpelders.viewmodel.AutenticacionViewModel;

public class LoginFragment extends Fragment {
    private AutenticacionViewModel autenticacionViewModel;

    private EditText nTelfEditText, passEditText;
    private Button loginButton, registerButton;
    private TextView forgotРass;

    public LoginFragment() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        autenticacionViewModel = ViewModelProviders.of(requireActivity()).get(AutenticacionViewModel.class);

        nTelfEditText = view.findViewById(R.id.edittext_nTelf);
        passEditText = view.findViewById(R.id.edittext_pass);
        loginButton = view.findViewById(R.id.btnGoHome);
        registerButton = view.findViewById(R.id.btnGoRegister);
        forgotРass = view.findViewById(R.id.forgotРass);

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
