package com.sergio.helpelders.home.bottom_bar_navigation;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sergio.helpelders.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class BottomРrofileFragment extends Fragment {


    public BottomРrofileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_bottom_profile, container, false);
    }

}
