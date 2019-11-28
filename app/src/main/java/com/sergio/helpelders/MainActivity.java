package com.sergio.helpelders;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    private NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // NavController
        navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        navController.navigate(R.id.welcomeFragment);

        // BottomNavigation
        final BottomNavigationView bottomNavView = findViewById(R.id.bottom_nav_view);
        NavigationUI.setupWithNavController(bottomNavView, navController);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);

        navController.addOnDestinationChangedListener(new NavController.OnDestinationChangedListener() {
            @Override
            public void onDestinationChanged(@NonNull NavController controller, @NonNull NavDestination destination, @Nullable Bundle arguments) {
                switch(destination.getId()) {
                    case R.id.welcomeFragment:
                    case R.id.viewpagerFragment:
                    case R.id.loginFragment:
                    case R.id.registerFragment:
                    case R.id.forgotРasswordFragment:
                        bottomNavView.setVisibility(View.GONE);
                        myToolbar.setVisibility(View.GONE);
                        break;
                    default:
                        bottomNavView.setVisibility(View.VISIBLE);
                        myToolbar.setVisibility(View.VISIBLE);
                }
            }
        });




    }
}
