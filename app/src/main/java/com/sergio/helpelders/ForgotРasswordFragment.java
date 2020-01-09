package com.sergio.helpelders;

import android.os.Build;
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

import java.util.Calendar;

public class ForgotРasswordFragment extends Fragment {
    private ConstraintLayout constraintLayout;

    private TextInputEditText nTelfEditText;

    private TextView btnGoBack;

    public ForgotРasswordFragment() { }

    public void setLoginScreen(@NonNull View view) {
        constraintLayout = view.findViewById(R.id.container);

        Calendar c = Calendar.getInstance();
        int timeOfDay = c.get(Calendar.HOUR_OF_DAY);

        if(timeOfDay >= 0 && timeOfDay < 12) {
            // Morning
            constraintLayout.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.good_morning_img));
        }
        else {
            if(timeOfDay >= 12 && timeOfDay < 20) {
                // Afternoon

            }
            else {
                if(timeOfDay >= 20 && timeOfDay <= 24) {
                    // Night
                    constraintLayout.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.good_night_img));
                }
            }
        }

        if(Build.VERSION.SDK_INT >= 21) {
            view.setSystemUiVisibility(View.GONE);

        }

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_forgot_password, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        btnGoBack = view.findViewById(R.id.btn_volver);
        btnGoBack.setOnClickListener(view1 -> Navigation.findNavController(view1).navigate(R.id.loginFragment));
    }
}
