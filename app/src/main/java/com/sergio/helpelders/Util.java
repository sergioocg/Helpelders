package com.sergio.helpelders;

import android.content.Context;
import android.util.Patterns;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
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

    public ImageView volverButton, cerrarSesionButton;
    public TextView nombreApellidosTextView;
    public EditText nombreEditText, apellidosEditText, direccionEditText, cpEditText, localidadEditText, fechaNacEditText, emailEditText, passEditText;
    public RadioButton hombreRb, abueloRb;

    public String nombre, apellidos, fechaNac, sexo, rolUsuario, direccion,
            localidad, codPost;
    public String email, pass;

    public final Calendar calendar = Calendar.getInstance();

    public DocumentReference user;

    public TextView localidadTextView, fechaNacTextView,
            direccionTextView, codPostTextView, sexoTextView, tipoUsuarioTextView, emailTextView;

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
        boolean emailOk = false;

        if(emailEditText.getText().toString().length() > 0) {
            if(Patterns.EMAIL_ADDRESS.matcher(emailEditText.getText().toString()).matches()) {
                email = emailEditText.getText().toString();
                emailOk = true;
            }
            else {
                showErrorToast("Formato de EMAIL no válido");
            }
        }
        else {
            showErrorToast("El EMAIL no puede estar vacío");
        }

        return emailOk;
    }

    public boolean comprobarPass() {
        boolean passOK = false;

        if(passEditText.getText().toString().length() > 0) {
            if(passEditText.getText().toString().length() >= 6) {
                pass = passEditText.getText().toString();
                passOK = true;
            }
            else {
                showErrorToast("La CONTRASEÑA debe tener 6 carácteres");
            }
        }
        else {
            showErrorToast("La CONTRASEÑA no puede estar vacía");
        }

        return passOK;
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

    public void setRegisterScreen() {
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

    public void setForgotScreen() {
        int horaActual = calendar.get(Calendar.HOUR_OF_DAY);
        //Log.i("TIME", String.valueOf(horaActual));

        if(horaActual >= 7 && horaActual < 12) {
            // Mañana
            constraintLayout.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.good_morning_img));
        }
        else {
            if(horaActual >= 12 && horaActual < 20) {
                // Tarde
            }
            else {
                // Night
                constraintLayout.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.good_night_img));
            }
        }
    }





}
