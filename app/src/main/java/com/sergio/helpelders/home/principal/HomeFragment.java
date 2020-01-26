package com.sergio.helpelders.home.principal;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.ornach.nobobutton.NoboButton;
import com.sergio.helpelders.R;

public class HomeFragment extends Fragment {
    private NoboButton publicarBoton, buscaBoton;
    /**
     * Constructor
     */
    public HomeFragment() {}

    /**
     * Métodos
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    private void setListeners(View view) {
        publicarBoton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.publishFragment);
            }
        });

        buscaBoton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.mapFragment);
            }
        });
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        publicarBoton = view.findViewById(R.id.boton_ir_publicar);
        buscaBoton = view.findViewById(R.id.boton__ir_buscar);

        setListeners(view);
    }
}
