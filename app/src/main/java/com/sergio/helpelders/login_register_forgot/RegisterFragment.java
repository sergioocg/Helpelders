package com.sergio.helpelders.login_register_forgot;

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
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;

import com.sergio.helpelders.R;
import com.sergio.helpelders.viewmodel.AutenticacionViewModel;

import java.util.Calendar;

public class RegisterFragment extends Fragment {
    private ConstraintLayout constraintLayout;

    private AutenticacionViewModel autenticacionViewModel;

    private TextView volverButton;
    private EditText nombreEditText, apellidosEditText, direccionEditText, cpEditText, localidadEditText, fechaNacEditText, nTelfEditText, passEditText;
    private Button registerButton;

    private void setInitWidgets(@NonNull View view) {
        constraintLayout = view.findViewById(R.id.container);

        volverButton = view.findViewById(R.id.btn_volver);

        nombreEditText = view.findViewById(R.id.text_nombre);
        apellidosEditText = view.findViewById(R.id.text_apellidos);

        direccionEditText = view.findViewById(R.id.text_direccion);
        cpEditText = view.findViewById(R.id.text_direccion_cp);
        localidadEditText = view.findViewById(R.id.text_direccion_localidad);

        fechaNacEditText = view.findViewById(R.id.text_fechanac);

        nTelfEditText = view.findViewById(R.id.text_ntelf);
        passEditText = view.findViewById(R.id.text_contrasena);

        registerButton = view.findViewById(R.id.btn_registrar);
    }

    private void setLoginScreen() {
        Calendar c = Calendar.getInstance();
        int timeOfDay = c.get(Calendar.HOUR_OF_DAY);

        if(timeOfDay >= 7 && timeOfDay < 12) {
            // Morning
            constraintLayout.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.good_morning_img));
        }
        else {
            if(timeOfDay >= 12 && timeOfDay < 20) {
                // Afternoon - Algo de bienvenida

            }
            else {
                // Night
                constraintLayout.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.good_night_img));
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_register, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setInitWidgets(view);
        setLoginScreen();

        volverButton.setOnClickListener(view12 -> Navigation.findNavController(view12).navigate(R.id.loginFragment));

        autenticacionViewModel = ViewModelProviders.of(requireActivity()).get(AutenticacionViewModel.class);

        // Рoner el estado del registro al estado INICIAR, (por si se había quedado en COMРLETADO O NOMBRE_NO_DISРONIBLE)
        autenticacionViewModel.iniciarRegistro();

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
