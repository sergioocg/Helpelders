package com.sergio.helpelders.login_register_forgot;

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
import androidx.navigation.Navigation;

import com.google.android.material.textfield.TextInputEditText;
import com.sergio.helpelders.R;

import java.util.Calendar;

public class ForgotРasswordFragment extends Fragment {
    private ConstraintLayout constraintLayout;
    private TextInputEditText nTelfEditText;
    private TextView btnGoBack;

    public ForgotРasswordFragment() { }

    private void initWidgets(@NonNull View view) {
        constraintLayout = view.findViewById(R.id.container);
        btnGoBack = view.findViewById(R.id.btn_volver);
    }

    private void setForgetPasswordScreen() {
        Calendar c = Calendar.getInstance();
        int timeOfDay = c.get(Calendar.HOUR_OF_DAY);

        if(timeOfDay >= 7 && timeOfDay < 12) {
            // Morning
            constraintLayout.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.good_morning_img));
        }
        else {
            if(timeOfDay >= 12 && timeOfDay < 20) {
                // Afternoon

            }
            else {
                // Night
                constraintLayout.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.good_night_img));
            }
        }
    }

    private void setListeners() {
        btnGoBack.setOnClickListener(view1 -> Navigation.findNavController(view1).navigate(R.id.loginFragment));
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
