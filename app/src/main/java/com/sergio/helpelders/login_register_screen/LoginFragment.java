package com.sergio.helpelders.login_register_screen;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.sergio.helpelders.R;

public class LoginFragment extends Fragment {
    private Button btnGoRegister, btnGoHome;

    public LoginFragment() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        btnGoRegister = view.findViewById(R.id.btnGoRegister);
        btnGoRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.registerFragment);
            }
        });

        btnGoHome = view.findViewById(R.id.btnGoHome);
        btnGoHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.homeFragment);
            }
        });
    }
}
