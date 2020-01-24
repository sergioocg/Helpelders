package com.sergio.helpelders;

import android.content.Context;
import android.util.Patterns;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.ontbee.legacyforks.cn.pedant.SweetAlert.SweetAlertDialog;

import java.util.Calendar;

import es.dmoral.toasty.Toasty;


public abstract class Util extends Fragment {
    public FirebaseAuth mAuth = FirebaseAuth.getInstance();
    public FirebaseFirestore fStore;

    public LinearLayout linearLayout;
    public ConstraintLayout constraintLayout;

    public ImageView volverButton;
    public TextView nombreApellidosTextView;
    public EditText emailEditText, passEditText;

    public SweetAlertDialog alertDialog;

    public String email, pass;

    public final Calendar calendar = Calendar.getInstance();

    public DocumentReference user;
    public DocumentSnapshot doc;

    public void showAlertDialog(int tipoAlerta, String titulo, String texto) {
        new SweetAlertDialog(requireContext(), tipoAlerta)
                .setTitleText(titulo)
                .setContentText(texto)
                .show();
    }

    public void showErrorToast(String texto) {
        Toasty.error(requireContext(), texto, Toasty.LENGTH_SHORT, true).show();
    }

    public void showSuccessToast(String texto) {
        Toasty.success(requireContext(), texto, Toasty.LENGTH_SHORT, true).show();
    }



    public boolean comprobarEmail() {
        boolean userOk = false;

        try {
            email = emailEditText.getText().toString();
            //Log.e("LOGIN", "Email: " + email.length());

            if(email.length() == 0) { // Email vacío
                showErrorToast("El email no puede estar vacío");
            }
            else {
                if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    showErrorToast("Formato de email no válido");
                }
                else {
                    userOk = true;
                }
            }
        }catch(Exception e) {
            e.printStackTrace();
        }

        return userOk;
    }

    public boolean comprobarPass() {
        boolean passOk = false;

        try {
            pass = passEditText.getText().toString();

            if(pass.length() == 0) { // Email vacío
                showErrorToast("La contraseña no puede estar vacía");
            }
            else {
                if(pass.length() < 6) {
                    showErrorToast("La contraseña debe tener 6 carácteres");
                }
                else {
                    passOk = true;
                }
            }

        }catch(Exception e) {
            e.printStackTrace();
        }

        return passOk;
    }

    public void setLoginScreen(TextView titulo) {
        int horaActual = calendar.get(Calendar.HOUR_OF_DAY);
        //Log.i("TIME", String.valueOf(horaActual));

        if(horaActual >= 7 && horaActual < 12) {
            // Mañana
            constraintLayout.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.good_morning_img));
            titulo.setText("¡ Buenos días !");
        }
        else {
            if(horaActual >= 12 && horaActual < 20) {
                // Tarde
            }
            else {
                // Night
                constraintLayout.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.good_night_img));
                titulo.setText("¡ Buenas noches !");
            }
        }
    }

    public void setScreenByTime() {
        int horaActual = calendar.get(Calendar.HOUR_OF_DAY);
        //Log.i("TIME", String.valueOf(horaActual));

        if(horaActual >= 7 && horaActual < 12) {
            // Mañana
            linearLayout.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.good_morning_img));
        }
        else {
            if(horaActual >= 12 && horaActual < 20) {
                // Tarde
            }
            else {
                // Night
                linearLayout.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.good_night_img));
            }
        }
    }




}
