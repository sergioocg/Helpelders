package com.sergio.helpelders.messages;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.sergio.helpelders.R;

import es.dmoral.toasty.Toasty;

public class MessageFragment extends Fragment {
    private ListView listView;
    private ListAdapterChats adapterChats;
    private String[] arrayNombres, arrayMensajes;
    private int[] arrayImagenesPerfil;

    public MessageFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_message, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        arrayNombres = new String[]{ "Fabián", "Miguel", "Anthea", "Maeve"};

        arrayMensajes = new String[]{"Hey, ¿qué tal?", "Buenas", "Estoy escuchando música", "Ella Fitzgerald..."};


        arrayImagenesPerfil = new int[] {R.drawable.profile_image,
                R.drawable.profile_image,
                R.drawable.profile_image,
                R.drawable.profile_image};


        listView = view.findViewById(R.id.listChats);

        adapterChats = new ListAdapterChats(requireContext(), arrayNombres, arrayMensajes, arrayImagenesPerfil);

        listView.setAdapter(adapterChats);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                Toasty.info(requireContext(),"Has seleccionado: " + arrayNombres[position], Toast.LENGTH_SHORT).show();
            }

        });

    }
}
