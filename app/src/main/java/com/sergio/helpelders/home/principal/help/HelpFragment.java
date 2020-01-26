package com.sergio.helpelders.home.principal.help;


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
import com.sergio.helpelders.Util;
import com.sergio.helpelders.messages.ListAdapterChats;

import es.dmoral.toasty.Toasty;


/**
 * A simple {@link Fragment} subclass.
 */
public class HelpFragment extends Util {
    ListView list;
    ListAdapterChats adapter;
    String[] name,message;


    int[] image;

    public HelpFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_help, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        volverButton = view.findViewById(R.id.btn_volver);
        volverButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStack();
            }
        });

        name = new String[]{ "1. Lorem", "2. Ipsum", "3. Matador"};

        message = new String[]{"La solución es simple...", "Todo tiene solución....", "Envía un correo a..."};


        image = new int[] {R.drawable.profile_image,
                R.drawable.profile_image,
                R.drawable.profile_image};


        // Locate the ListView in listview_main.xml
        list = (ListView)view.findViewById(R.id.myListHelp);

        // Pass results to ListViewAdapter Class
        adapter = new ListAdapterChats(requireContext(), name, message, image);
        // Binds the Adapter to the ListView
        list.setAdapter(adapter);
        // Capture ListView item click
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                Toasty.info(requireContext(),"Has seleccionado: " + name[position], Toast.LENGTH_SHORT).show();
            }

        });
    }
}