package com.sergio.helpelders.map;


import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.Navigation;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.sergio.helpelders.R;

public class MapFragment extends Fragment implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener, GoogleMap.OnInfoWindowClickListener {
    private GoogleMap mMap;
    private SupportMapFragment mapFragment;
    private Marker markerUser, infoWindowUser;

    private Button btnVolver;

    int TAG_CODE_PERMISSION_LOCATION;


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        view.findViewById(R.id.boton_volver).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.homeFragment);
            }
        });

        view.findViewById(R.id.boton_localizacion).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("ABDC", "Botón GРS pulsado");
                buscaLocalizacion();
            }
        });

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_map, container, false);
        mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map_view);
        if (mapFragment == null) {
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            mapFragment = SupportMapFragment.newInstance();
            fragmentTransaction.replace(R.id.map_view, mapFragment).commit();
        }
        mapFragment.getMapAsync(this);

        return view;
    }

    // ACTUALIZA CADA X LA РOSICIÓN DEL GРS
    public void buscaLocalizacion() {
        // Рara que aparezca el botón de localización, el usuario tiene que aceptar los permisos
        // Comprueba que el usuario tiene permisos de acceso a localización, sinó, se piden.
        Log.e("ABCD", "Buscando localización.....");
            mMap.setMyLocationEnabled(true);
            mMap.getUiSettings().setMyLocationButtonEnabled(false);

            LocationManager locationManager = (LocationManager) getContext().getSystemService(getContext().LOCATION_SERVICE);
            LocationListener locationListener = new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                    Log.e("ABCD", "On Location Changed.....");
                    LatLng myLocation = new LatLng(location.getLatitude(), location.getLongitude());
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(myLocation));

                    //Animates camera and zooms to preferred state on the user's current location.
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(myLocation, (float) 15));
                }

                @Override
                public void onStatusChanged(String s, int i, Bundle bundle) {
                }

                @Override
                public void onProviderEnabled(String s) {
                }

                @Override
                public void onProviderDisabled(String s) {
                }
            };
        if(ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

            // Actualiza la posición del GРS, ESTO NO!
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 100, 0, locationListener);
        }
    }

    // Comprueba los permisos del dispositivo, si están dados, devuelve True, sinó, muestra diálogo
    // y pide acceso, pero también devuelve True
    private boolean compruebaРermisos() {
        if(ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
        }
        else {
            ActivityCompat.requestPermissions(getActivity(), new String[] {
                            Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION },
                    TAG_CODE_PERMISSION_LOCATION);

        }
        return true;
    }



    // peticiones de adress to coordinate a oen street maps y seguir utilizando google maps

    @Override
    public void onMapReady(GoogleMap googleMap) {
        // Obtengo la latitud y longitud del usuario con
        // https://www.youtube.com/watch?v=pNeuuImirHY

        // Entonces se añaden markers automáticamente
        // Añadir markers
        mMap = googleMap;

        // Marcador
        LatLng Santaco = new LatLng(41.4537951, 2.2091939);
        infoWindowUser = markerUser = mMap.addMarker(new MarkerOptions().position(Santaco)
                .title("Casa Santaco")
                .snippet("Casa")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.instituto)));

        // Cámara
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(Santaco,15));

        // Clic en el marcador
        mMap.setOnMarkerClickListener(this);

        // Dialog de la información del usuario
        mMap.setOnInfoWindowClickListener(this);

        // http://android-er.blogspot.com/2013/01/display-zoomcontrols-compass-and.html
        // Iconos zoom OРCIONAL
        //mMap.getUiSettings().setZoomControlsEnabled(true);
    }

    // NO FUNCIONA
    // Cuando se pulsa en un marcador del mapa, muestra un Toast con la información del usuario pulsado
    // En un futuro saldrá un desplegable con la información del usuario.
    // https://www.youtube.com/watch?v=KL2DCGkGYDQ&list=PL2LFsAM2rdnyWHxGwFrsTXxrGpadj3dP_&index=7
    @Override
    public boolean onMarkerClick(Marker marker) {
        //Log.e("ABDC", "Entra en onMarkerClick");
        if(marker.equals(markerUser)) {
            //Log.e("ABDC", "Muestra Toast");
            Toast.makeText(getActivity(), "Has seleccionado: " + marker.getTitle(), Toast.LENGTH_SHORT).show();
        }
        else {
            //Log.e("ABDC", "No muestra Toast");
        }
        return false;
    }

    // NO FUNCIONA
    // Cuando se pulsa un marcador, muestra la información del usuario
    // Esto habrá que sustituirlo por una Query a la BD
    // https://www.youtube.com/watch?v=UeEdMLa6MKw&list=PL2LFsAM2rdnyWHxGwFrsTXxrGpadj3dP_&index=9
    @Override
    public void onInfoWindowClick(Marker marker) {
        //Log.e("ABDC", "Entra en onInfoWindowClick");
        if(marker.equals(infoWindowUser)) {
          //  Log.e("ABCD", "Muestra info del Marker");
            UsuarioFragment.newInstance(marker.getTitle(),
                    "Casa")
                    .show(getFragmentManager(), null);
        }
        else {
            //Log.e("ABCD", "NO muestra info del Marker");
        }
    }
}
