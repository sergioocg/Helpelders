package com.sergio.helpelders.login_register_forgot;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.ontbee.legacyforks.cn.pedant.SweetAlert.SweetAlertDialog;
import com.ornach.nobobutton.NoboButton;
import com.sergio.helpelders.R;
import com.sergio.helpelders.Util;

public class ForgotРasswordFragment extends Util {
    private NoboButton btnRecordar;

    public ForgotРasswordFragment() { }

    private void initWidgets(@NonNull View view) {
        constraintLayout = view.findViewById(R.id.container);
        volverButton = view.findViewById(R.id.btn_volver);
        emailEditText = view.findViewById(R.id.text_email);
        btnRecordar = view.findViewById(R.id.btn_recordar_contrasena);
    }

    private void setListeners() {
        volverButton.setOnClickListener(view1 -> getFragmentManager().popBackStack());

        btnRecordar.setOnClickListener(new View.OnClickListener() { // Faltaría comprobar cosas, etc.
            @Override
            public void onClick(View v) {
                if(comprobarEmail()) {
                    SweetAlertDialog pDialog = new SweetAlertDialog(requireContext(), SweetAlertDialog.SUCCESS_TYPE);

                    pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
                    pDialog.setTitleText("Reiniciando... ");
                    pDialog.setCancelable(false);
                    pDialog.show();

                    showSuccessToast("¡ Te acabamos de enviar un email para cambiar la contraseña !");

                    getFragmentManager().popBackStack();
                }
            }
        });
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_forgot_password, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initWidgets(view);
        setForgotScreen();
        setListeners();
    }
}
