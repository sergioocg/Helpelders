package com.sergio.helpelders.welcome_screen;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.sergio.helpelders.R;

// https://www.youtube.com/watch?v=byLKoPgB7yA

public class SliderAdapter extends PagerAdapter {
    Context context;
    LayoutInflater layoutInflater;

    public SliderAdapter(Context context) {
        this.context = context;
    }

    // Store the values for our slider

    public int[] slide_images = {
            R.drawable.eat_icon,
            R.drawable.sleep_icon,
            R.drawable.code_icon
    };

    public String[] slide_headings = {
            "EAT",
            "SLEEP",
            "CODE"
    };

    public String[] slide_desc = {
            "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor" +
                    "incididunt ut labore et dolore manga aliqua",
            "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor" +
                    "incididunt ut labore et dolore manga aliqua",
            "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor" +
                    "incididunt ut labore et dolore manga aliqua"
    };

    @Override
    public int getCount() {
        return slide_headings.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == (RelativeLayout)object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.slide_layout, container, false);

        ImageView slideImageView = (ImageView) view.findViewById(R.id.slide_image);
        TextView slideHeading = (TextView) view.findViewById(R.id.slide_heading);
        TextView slideDescription = (TextView) view.findViewById(R.id.slide_description);

        slideImageView.setImageResource(slide_images[position]);
        slideHeading.setText(slide_headings[position]);
        slideDescription.setText(slide_desc[position]);

        container.addView(view);

        return view;
    }

    // El método al llegar a la última página, will stop, así no se crean múltiples slide
    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((RelativeLayout)object);
    }
}
