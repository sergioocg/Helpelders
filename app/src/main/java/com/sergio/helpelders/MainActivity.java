package com.sergio.helpelders;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.luseen.spacenavigation.SpaceItem;
import com.luseen.spacenavigation.SpaceNavigationView;
import com.luseen.spacenavigation.SpaceOnClickListener;

import es.dmoral.toasty.Toasty;

/**
 * Fragment y NavHost
 * https://github.com/gerardfp/P9/tree/master/app/src/main/java/com/company/p9
 */
public class MainActivity extends AppCompatActivity {
    /**
     * Atributos
     * @NavController navController, permite movernos por los diferentes Fragment
     * @Toolbar toolbar, barra superior. DE MOMENTO NO LA VOY A UTILIZAR
     * @SpaceNavigationView bottomBar, muestra la barra inferior (librería externa https://github.com/armcha/Space-Navigation-View)
     */
    private NavController navController;
    //private Toolbar toolbar;
    SpaceNavigationView bottomBar;

    /**
     * Añade a la bottomBar los diferentes items con nombre e imagen.
     * Método onCentrButtonClick habilita el botón central y redirige al Fragment.
     * onItemClick redirige a cada Fragment cuando se pulsa.
     * onItemReselected en este caso muestra un Toast cuando se vuelve a pulsar un elemento.
     */
    private void setBottomBar(Bundle savedInstanceState) {
        bottomBar = findViewById(R.id.space);

        bottomBar.initWithSaveInstanceState(savedInstanceState);
        bottomBar.addSpaceItem(new SpaceItem("Inicio", R.drawable.ic_home_black_24dp));
        bottomBar.addSpaceItem(new SpaceItem("Publicar", R.drawable.ic_add_black_24dp));
        bottomBar.addSpaceItem(new SpaceItem("Mensajes", R.drawable.ic_message_black_24dp));
        bottomBar.addSpaceItem(new SpaceItem("Perfil", R.drawable.ic_person_black_24dp));

        bottomBar.setSpaceOnClickListener(new SpaceOnClickListener() {
            @Override
            public void onCentreButtonClick() {
                Toasty.info(MainActivity.this,"Mapa", Toast.LENGTH_SHORT).show();
                bottomBar.setCentreButtonSelectable(true);

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
    }

    /**
     * Con el NavController podemos movernos por los diferentes Fragment
     */
    private void setNavController() {
        navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        navController.navigate(R.id.welcomeFragment);

        navController.addOnDestinationChangedListener((controller, destination, arguments) -> {
            switch (destination.getId()) {
                case R.id.welcomeFragment:
                case R.id.viewpagerFragment:
                case R.id.loginFragment:
                case R.id.registerFragment:
                case R.id.forgotРasswordFragment:
                case R.id.mapFragment:
                case R.id.fullProfileFragment:
                    bottomBar.setVisibility(View.GONE);
                    break;
                default:
                    bottomBar.setVisibility(View.VISIBLE);
            }
        });
    }

    /**
     * https://gerardfp.github.io/mp08/p13/#1
     * Desactiva la cache
     * El SDK de Firestore tiene activada por defecto la persistencia de datos. Esto signicica
     * que cuando hacemos una consulta al servidor, se guarda el resultado en el móvil (caché).
     * Va bien para ahorrar datos, pero puede ser un quebradero durante el desarrollo de la app.
     */
    private void setFirestore() {
        FirebaseFirestore.getInstance().setFirestoreSettings(new FirebaseFirestoreSettings.Builder()
                .setPersistenceEnabled(false)
                .build());
    }

    /**
     * Evita que puedas dar al botón atrás que incorpora Android, tanto barra inferior como gestos
     * Hay que programarlo para que vuelva a los Fragment correspondientes
     */
    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        return;
    }

    /**
     * Métodos de AppCompatActivity
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setBottomBar(savedInstanceState);

        setNavController();
        setFirestore();
    }
}
