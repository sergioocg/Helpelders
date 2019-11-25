package com.sergio.helpelders.welcome_screen;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sergio.helpelders.R;

public class ViewpagerFragment extends Fragment {
    public ViewpagerFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_viewpager, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ViewPager viewPager = view.findViewById(R.id.viewPager);

        viewPager.setAdapter(new DemoPagerAdapter(getChildFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT));

    }

    class DemoPagerAdapter extends FragmentPagerAdapter {

        public DemoPagerAdapter(@NonNull FragmentManager fm, int behavior) {
            super(fm, behavior);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            if(position == 0) {
                return new ElderFragment();
            }
            return new VolunteerFragment();
        }

        @Override
        public int getCount() {
            return 2;
        }
    }
}
