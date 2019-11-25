package com.sergio.helpelders.welcome_screen;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sergio.helpelders.R;

public class WelcomeFragment extends Fragment {
    public WelcomeFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_welcome, container, false);
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Añade delay
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                // Después de X tiempo seguir a otra pantalla
                Navigation.findNavController(view).navigate(R.id.viewpagerFragment);
            }
        }, 3000 );//time in milisecond
    }
}
