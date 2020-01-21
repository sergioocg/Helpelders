package com.sergio.helpelders;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.sergio.helpelders.viewmodel.AutenticacionViewModel;

import me.ibrahimsn.lib.SmoothBottomBar;

// https://github.com/gerardfp/P9/tree/master/app/src/main/java/com/company/p9
public class MainActivity extends AppCompatActivity {
    private NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AutenticacionViewModel autenticacionViewModel;
        autenticacionViewModel = ViewModelProviders.of(this).get(AutenticacionViewModel.class);
        autenticacionViewModel.mostrarUsuarios();


        /**
         * Explica que hace
         * @navController Contiene
         */
        // NavController
        navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        navController.navigate(R.id.welcomeFragment);

        // BottomNavigation
        final BottomNavigationView bottomNavView = findViewById(R.id.bottom_nav_view);
        NavigationUI.setupWithNavController(bottomNavView, navController);

        // Toolbar superior
        Toolbar myToolbar = findViewById(R.id.superiorBar);
        setSupportActionBar(myToolbar);

        navController.addOnDestinationChangedListener((controller, destination, arguments) -> {
            switch (destination.getId()) {
                case R.id.welcomeFragment:
                case R.id.viewpagerFragment:
                case R.id.loginFragment:
                case R.id.registerFragment:
                case R.id.forgotРasswordFragment:
                case R.id.mapFragment:
                    bottomNavView.setVisibility(View.GONE);
                    myToolbar.setVisibility(View.GONE);
                break;
                default:
                    bottomNavView.setVisibility(View.VISIBLE);
                    myToolbar.setVisibility(View.VISIBLE);
                    myToolbar.setTitle(navController.getCurrentDestination().getLabel());
            }
        });
    }
}
