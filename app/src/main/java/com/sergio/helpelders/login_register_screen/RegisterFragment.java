package com.sergio.helpelders.login_register_screen;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;

import com.sergio.helpelders.R;
import com.sergio.helpelders.viewmodel.AutenticacionViewModel;

public class RegisterFragment extends Fragment {
    private AutenticacionViewModel autenticacionViewModel;

    private ImageView btnGoBack;
    private EditText nombreEditText, apellidosEditText, fechaNacEditText, nTelfEditText, passEditText;
    private Button registerButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_register, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        btnGoBack = view.findViewById(R.id.btnGoBack);
        btnGoBack.setOnClickListener(view12 -> Navigation.findNavController(view12).navigate(R.id.loginFragment));

        autenticacionViewModel = ViewModelProviders.of(requireActivity()).get(AutenticacionViewModel.class);

        nombreEditText = view.findViewById(R.id.edittext_nombre);
        apellidosEditText = view.findViewById(R.id.edittext_apellidos);
        fechaNacEditText = view.findViewById(R.id.edittext_fechaNac);
        nTelfEditText = view.findViewById(R.id.edittext_nTelf);
        passEditText = view.findViewById(R.id.edittext_pass);

        // Рoner el estado del registro al estado INICIAR, (por si se había quedado en COMРLETADO O NOMBRE_NO_DISРONIBLE)
        autenticacionViewModel.iniciarRegistro();

        registerButton = view.findViewById(R.id.btnRegistrar);
        registerButton.setOnClickListener(view1 -> {
           // Log.e("ABCD", "registrando.....");
            autenticacionViewModel.crearCuentaEIniciarSesion(nombreEditText.getText().toString(),
                    apellidosEditText.getText().toString(), fechaNacEditText.getText().toString(),
                    nTelfEditText.getText().toString(), passEditText.getText().toString());

            Navigation.findNavController(view1).navigate(R.id.homeFragment);
        });

        autenticacionViewModel.estadoDelRegistro.observe(getViewLifecycleOwner(), estadoDelRegistro -> {
      //     Log.e("ABCD", "estado del registro.....");
            switch (estadoDelRegistro){
                case NOMBRE_NO_DISPONIBLE:
                    Toast.makeText(getContext(), "NOMBRE DE USUARIO NO DISPONIBLE", Toast.LENGTH_SHORT).show();
                    break;
            }
        });

        autenticacionViewModel.estadoDeLaAutenticacion.observe(getViewLifecycleOwner(), estadoDeLaAutenticacion -> {
                //Log.e("ABCD", "estado del login.....");
                switch (estadoDeLaAutenticacion){
                    case AUTENTICADO:
                       // Log.e("ABCD", "estado del login OKKKKK.....");
                        Navigation.findNavController(view).popBackStack();
                        break;
            }
        });


    }
}
