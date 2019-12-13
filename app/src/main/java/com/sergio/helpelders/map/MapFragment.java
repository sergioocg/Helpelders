package com.sergio.helpelders.map;


import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
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

        btnVolver = view.findViewById(R.id.boton_volver);
        btnVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.homeFragment);
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
        mapFragment = (SupportMapFragment)getChildFragmentManager().findFragmentById(R.id.map_view);
        if(mapFragment == null) {
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            mapFragment = SupportMapFragment.newInstance();
            fragmentTransaction.replace(R.id.map_view, mapFragment).commit();
        }
        mapFragment.getMapAsync(this);

        return view;
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

    @Override
    public void onMapReady(GoogleMap googleMap) {
        // Obtengo la latitud y longitud del usuario con
        // https://www.youtube.com/watch?v=pNeuuImirHY

        // Entonces se añaden markers automáticamente
        // Añadir markers
        mMap = googleMap;

        // Marcador
        LatLng Santaco = new LatLng(41.4537951, 2.2091939);
        mMap.addMarker(new MarkerOptions().position(Santaco)
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
        mMap.getUiSettings().setZoomControlsEnabled(true);

        // Рara que aparezca el botón de localización, el usuario tiene que aceptar los permisos
        // Comprueba que el usuario tiene permisos de acceso a localización, sinó, se piden.
        if(compruebaРermisos()) {
            mMap.setMyLocationEnabled(true);
            mMap.getUiSettings().setMyLocationButtonEnabled(true);
        }
    }

    // NO FUNCIONA
    // Cuando se pulsa en un marcador del mapa, muestra un Toast con la información del usuario pulsado
    // En un futuro saldrá un desplegable con la información del usuario.
    // https://www.youtube.com/watch?v=KL2DCGkGYDQ&list=PL2LFsAM2rdnyWHxGwFrsTXxrGpadj3dP_&index=7
    @Override
    public boolean onMarkerClick(Marker marker) {
        if(marker.equals(markerUser)) {
            Toast.makeText(getActivity(), marker.getTitle(), Toast.LENGTH_SHORT).show();
        }
        return false;
    }

    // NO FUNCIONA
    // Cuando se pulsa un marcador, muestra la información del usuario
    // Esto habrá que sustituirlo por una Query a la BD
    // https://www.youtube.com/watch?v=UeEdMLa6MKw&list=PL2LFsAM2rdnyWHxGwFrsTXxrGpadj3dP_&index=9
    @Override
    public void onInfoWindowClick(Marker marker) {
        if(marker.equals(infoWindowUser)) {
            UsuarioFragment.newInstance(marker.getTitle(),
                    "Casa")
                    .show(getFragmentManager(), null);
        }
    }
}
