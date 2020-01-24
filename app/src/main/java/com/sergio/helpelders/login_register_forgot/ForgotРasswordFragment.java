package com.sergio.helpelders.login_register_forgot;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputEditText;
import com.ontbee.legacyforks.cn.pedant.SweetAlert.SweetAlertDialog;
import com.ornach.nobobutton.NoboButton;
import com.sergio.helpelders.R;

import java.util.Calendar;

public class ForgotРasswordFragment extends Fragment {
    private ConstraintLayout constraintLayout;
    private TextInputEditText nTelfEditText;
    private TextView btnGoBack;

    SweetAlertDialog pDialog;

    private NoboButton btnRecordar;

    public ForgotРasswordFragment() { }

    private void initWidgets(@NonNull View view) {
        constraintLayout = view.findViewById(R.id.container);
        btnGoBack = view.findViewById(R.id.btn_volver);
        nTelfEditText = view.findViewById(R.id.text_email);
        btnRecordar = view.findViewById(R.id.btn_recordar_contrasena);
    }

    private void setForgetPasswordScreen() {
        Calendar c = Calendar.getInstance();
        int timeOfDay = c.get(Calendar.HOUR_OF_DAY);

        if(timeOfDay >= 7 && timeOfDay < 12) {
            // Morning
            constraintLayout.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.good_morning_img));
        }
        else {
            if(timeOfDay >= 12 && timeOfDay < 20) {
                // Afternoon

            }
            else {
                // Night
                constraintLayout.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.good_night_img));
            }
        }
    }

    private void setListeners() {
        btnGoBack.setOnClickListener(view1 -> getFragmentManager().popBackStack());
        btnRecordar.setOnClickListener(new View.OnClickListener() { // Faltaría comprobar cosas, etc.
            @Override
            public void onClick(View v) {
                if(!nTelfEditText.getText().toString().isEmpty()) {
                    pDialog = new SweetAlertDialog(requireContext(), SweetAlertDialog.SUCCESS_TYPE);

                    pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
                    pDialog.setTitleText("Reiniciando... ");
                    pDialog.setCancelable(false);
                    pDialog.show();

                    pDialog.setTitleText("¡Buen trabajo!")
                            .setContentText("¡Te acabamos de enviar un sms con la contraseña!")
                            .show();

                    getFragmentManager().popBackStack();
                }
                else {
                    pDialog = new SweetAlertDialog(requireContext(), SweetAlertDialog.ERROR_TYPE);
                    pDialog.setTitleText("Oops...")
                            .setContentText("¡Introduce tu número de teléfono!")
                            .show();

                    // POP hacia atrás
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
        setForgetPasswordScreen();
        setListeners();
    }
}
