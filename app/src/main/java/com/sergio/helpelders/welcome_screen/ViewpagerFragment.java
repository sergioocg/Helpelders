package com.sergio.helpelders.welcome_screen;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.viewpager.widget.ViewPager;

import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;
import com.sergio.helpelders.R;

// https://www.youtube.com/watch?v=byLKoPgB7yA

public class ViewpagerFragment extends Fragment {
    private ViewPager slideViewPager;
    private LinearLayout dotsLayout;

    private SliderAdapter sliderAdapter;

    private TextView[] dots;

    private Button backBtn, nextBtn, skipBtn, iniciarButton;
    private int currentPage;


    public ViewpagerFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_viewpager, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        slideViewPager = view.findViewById(R.id.slideViewPager);
        dotsLayout = view.findViewById(R.id.dotsLayout);

        sliderAdapter = new SliderAdapter(view.getContext());

        slideViewPager.setAdapter(sliderAdapter);

        addDotsIndicator(0);

        slideViewPager.addOnPageChangeListener(viewListener);

        backBtn = view.findViewById(R.id.prevButton);
        nextBtn = view.findViewById(R.id.nextButton);
        skipBtn = view.findViewById(R.id.skipButton);
        iniciarButton = view.findViewById(R.id.iniciarButton);

        // OnClickListeners
        skipBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.loginFragment);
            }
        });


        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                slideViewPager.setCurrentItem(currentPage + 1);
            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                slideViewPager.setCurrentItem(currentPage - 1);
            }
        });

        iniciarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.loginFragment);
            }
        });
    }

    public void addDotsIndicator(int position) {
        dots = new TextView[sliderAdapter.getCount()];
        dotsLayout.removeAllViews(); // Si no empieza a crear puntos

        for(int i = 0; i < dots.length; i++) {
            dots[i] = new TextView(getContext());
            dots[i].setText(Html.fromHtml("&#8226;"));
            dots[i].setTextSize(35);
            dots[i].setTextColor(getResources().getColor(R.color.colorTransparentWhite));

            dotsLayout.addView(dots[i]);
        }

        if(dots.length > 0) {
            dots[position].setTextColor(getResources().getColor(R.color.colorWhite));
        }
    }

    ViewPager.OnPageChangeListener viewListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            addDotsIndicator(position);
            currentPage = position;

            if(position == 0) {
                nextBtn.setEnabled(true);
                backBtn.setEnabled(false);
                backBtn.setVisibility(View.INVISIBLE);

                nextBtn.setText("Siguiente");
                backBtn.setText("");
            }
            else {
                if(position == (dots.length - 1)) {
                    nextBtn.setEnabled(false);
                    backBtn.setEnabled(true);
                    backBtn.setVisibility(View.VISIBLE);

                    nextBtn.setText("");
                    backBtn.setText("Volver");

                    iniciarButton.setEnabled(true);
                    iniciarButton.setVisibility(View.VISIBLE);
                }
                else {
                    nextBtn.setEnabled(true);
                    backBtn.setEnabled(true);
                    backBtn.setVisibility(View.VISIBLE);

                    nextBtn.setText("Siguiente");
                    backBtn.setText("Volver");
                }
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };
}
