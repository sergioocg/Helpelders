package com.sergio.helpelders;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.luseen.spacenavigation.SpaceItem;
import com.luseen.spacenavigation.SpaceNavigationView;
import com.luseen.spacenavigation.SpaceOnClickListener;
import com.sergio.helpelders.viewmodel.AutenticacionViewModel;

import es.dmoral.toasty.Toasty;
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

        // Toast
        Toasty.Config.getInstance() .apply();

        // Bottom bar
        SpaceNavigationView navigationView = findViewById(R.id.space);

        navigationView.initWithSaveInstanceState(savedInstanceState);
        navigationView.addSpaceItem(new SpaceItem("Inicio", R.drawable.ic_home_white_24dp));
        navigationView.addSpaceItem(new SpaceItem("Publicar", R.drawable.ic_add_white_24dp));
        //navigationView.addSpaceItem(new SpaceItem("Buscar", R.drawable.ic_search_white_24dp));
        navigationView.addSpaceItem(new SpaceItem("Mensajes", R.drawable.ic_message_white_24dp));
        navigationView.addSpaceItem(new SpaceItem("Perfil", R.drawable.ic_person_white_24dp));

        navigationView.setSpaceOnClickListener(new SpaceOnClickListener() {
            @Override
            public void onCentreButtonClick() {
                Toasty.info(MainActivity.this,"MAPA", Toast.LENGTH_SHORT).show();
                navigationView.setCentreButtonSelectable(true);

                navController.navigate(R.id.mapFragment);
            }

            @Override
            public void onItemClick(int itemIndex, String itemName) {
                Toasty.info(MainActivity.this, itemIndex + " " + itemName, Toast.LENGTH_SHORT).show();

                switch(itemIndex) {
                    case 0: // Inicio
                        navController.navigate(R.id.homeFragment);
                    break;

                    case 1: // Publicar
                        //navController.navigate(R.id.publishFragment);
                    break;

                    case 2: // Mensajes
                        navController.navigate(R.id.messageFragment);
                    break;

                    case 3: // Perfil
                        navController.navigate(R.id.profileFragment);
                    break;
                }
            }

            @Override
            public void onItemReselected(int itemIndex, String itemName) {
                Toasty.info(MainActivity.this, itemIndex + " " + itemName, Toast.LENGTH_SHORT).show();
            }
        });


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
                    navigationView.setVisibility(View.GONE);
                    myToolbar.setVisibility(View.GONE);
                break;
                default:
                    navigationView.setVisibility(View.VISIBLE);
                    myToolbar.setVisibility(View.VISIBLE);
                    myToolbar.setTitle(navController.getCurrentDestination().getLabel());
            }
        });
    }
}
