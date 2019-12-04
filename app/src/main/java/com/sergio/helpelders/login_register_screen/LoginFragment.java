package com.sergio.helpelders.login_register_screen;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
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

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.registerFragment);
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                autenticacionViewModel.iniciarSesion(nTelfEditText.getText().toString(), passEditText.getText().toString());
                //Navigation.findNavController(view).navigate(R.id.bottomHomeFragment);
                Log.e("ABCDE", autenticacionViewModel.mostrarUsuarios());
            }
        });

        autenticacionViewModel.estadoDeLaAutenticacion.observe(getViewLifecycleOwner(), new Observer<AutenticacionViewModel.EstadoDeLaAutenticacion>() {
            @Override
            public void onChanged(AutenticacionViewModel.EstadoDeLaAutenticacion estadoDeLaAutenticacion) {
                switch (estadoDeLaAutenticacion){
                    case AUTENTICADO:
                        Navigation.findNavController(view).popBackStack();
                        break;

                    case AUTENTICACION_INVALIDA:
                        Toast.makeText(getContext(), "CREDENCIALES NO VALIDAS", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });

        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(),
                new OnBackPressedCallback(true) {
                    @Override
                    public void handleOnBackPressed() {
                        Navigation.findNavController(view).popBackStack(R.id.bottomHomeFragment, false);
                    }
                });

        forgotРass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.forgotРasswordFragment);
            }
        });
    }
}
