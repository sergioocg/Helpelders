package com.sergio.helpelders;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.luseen.spacenavigation.SpaceItem;
import com.luseen.spacenavigation.SpaceNavigationView;
import com.luseen.spacenavigation.SpaceOnClickListener;

import es.dmoral.toasty.Toasty;

// https://github.com/gerardfp/P9/tree/master/app/src/main/java/com/company/p9
public class MainActivity extends AppCompatActivity {
    private NavController navController;

    @Override
    public void onBackPressed() { // Evita que puedas dar atrás por gestos o barra de abajo de Android
        //super.onBackPressed();
        return;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /**
         * Desactiva la cache
         * El SDK de Firestore tiene activada por defecto la persistencia de datos. Esto signicica
         * que cuando hacemos una consulta al servidor, se guarda el resultado en el móvil (caché).
         * Va bien para ahorrar datos, pero puede ser un quebradero durante el desarrollo de la app.
         */
        FirebaseFirestore.getInstance().setFirestoreSettings(new FirebaseFirestoreSettings.Builder()
                .setPersistenceEnabled(false)
                .build());


        /**
         * Explica que hace
         * @navController Contiene
         */
        // NavController
        navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        navController.navigate(R.id.welcomeFragment);

        // Toast
        Toasty.Config.getInstance().apply();

        // Bottom bar
        SpaceNavigationView navigationView = findViewById(R.id.space);

        navigationView.initWithSaveInstanceState(savedInstanceState);
        navigationView.addSpaceItem(new SpaceItem("Inicio", R.drawable.ic_home_black_24dp));
        navigationView.addSpaceItem(new SpaceItem("Publicar", R.drawable.ic_add_black_24dp));
        navigationView.addSpaceItem(new SpaceItem("Mensajes", R.drawable.ic_message_black_24dp));
        navigationView.addSpaceItem(new SpaceItem("Perfil", R.drawable.ic_person_black_24dp));

        navigationView.setSpaceOnClickListener(new SpaceOnClickListener() {
            @Override
            public void onCentreButtonClick() {
                Toasty.info(MainActivity.this,"Mapa", Toast.LENGTH_SHORT).show();
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
                        navController.navigate(R.id.publishFragment);
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
                case R.id.homeFragment:
                    navigationView.setVisibility(View.VISIBLE);
                    break;
                case R.id.welcomeFragment:
                case R.id.viewpagerFragment:
                case R.id.loginFragment:
                case R.id.registerFragment:
                case R.id.forgotРasswordFragment:
                case R.id.mapFragment:
                case R.id.messageFragment:
                case R.id.profileFragment:
                    navigationView.setVisibility(View.GONE);
                    myToolbar.setVisibility(View.GONE);
                break;
                default:
                    //myToolbar.setVisibility(View.VISIBLE);
                    //myToolbar.setTitle(navController.getCurrentDestination().getLabel());
            }
        });
    }
}
