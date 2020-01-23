package com.sergio.helpelders.chat;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.sergio.helpelders.R;

import es.dmoral.toasty.Toasty;

public class messageFragment extends Fragment {
    private ImageView btnGoBack;

    ListView list;
    ListAdapter adapter;
    String[] name,message;

    int[] image;

    public messageFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_message, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        btnGoBack = view.findViewById(R.id.btn_volver);
        btnGoBack.setOnClickListener(view1 -> Navigation.findNavController(view1).navigate(R.id.homeFragment));

        name = new String[]{ "Fabián", "Miguel", "Anthea", "Maeve"};

        message = new String[]{"Puto, ¿cómo vas?", "Heyyy", "Estoy escuchando música", "Ella Fitzgerald..."};


        image = new int[] {R.drawable.profile_image,
                R.drawable.profile_image,
                R.drawable.profile_image,
                R.drawable.profile_image};


        // Locate the ListView in listview_main.xml
        list = (ListView)view.findViewById(R.id.mylist);

        // Pass results to ListViewAdapter Class
        adapter = new ListAdapter(getContext(), name,message,image);
        // Binds the Adapter to the ListView
        list.setAdapter(adapter);
        // Capture ListView item click
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                Toasty.info(getContext(),"Has seleccionado: " + name[position], Toast.LENGTH_SHORT).show();
            }

        });

    }
}
