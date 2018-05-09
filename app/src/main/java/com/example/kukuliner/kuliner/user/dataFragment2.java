package com.example.kukuliner.kuliner.user;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;


import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v7.widget.Toolbar;

import com.example.kukuliner.kuliner.R;
import com.example.kukuliner.kuliner.penjual.contentFragmentPenjual;
import com.example.kukuliner.kuliner.penjual.contentFragmentPenjual2;
import com.example.kukuliner.kuliner.penjual.contentFragmentpenjual3;

public class dataFragment2 extends Fragment {
    View view;
    Toolbar toolbar;
    ViewPager viewPager;
    TabLayout tabLayout;
    AppBarLayout appBarLayout;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.sample, container, false);
        toolbar =  view.findViewById(R.id.toolbar);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);

        viewPager = (ViewPager) view.findViewById(R.id.viewpager);
        viewPager.setAdapter(new sliderAdapter(getChildFragmentManager()));
        tabLayout = (TabLayout) view.findViewById(R.id.sliding_tabs);
        tabLayout.post(new Runnable() {
            @Override
            public void run() {
                tabLayout.setupWithViewPager(viewPager);
            }
        });
        appBarLayout  = view.findViewById(R.id.appbar);

        return view;
    }
    private class sliderAdapter extends FragmentPagerAdapter {

        final  String tabs[]={"new", "on proses","completed"};
        public sliderAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment = null;
            switch (position){
                case 0:

                    fragment = new contentFragmentPenjual();

                    break;
                case 1:
                    fragment = new contentFragmentPenjual2();
                    break;
                case 2:

                    fragment = new contentFragmentpenjual3();
            }
            return fragment;
        }

        @Override
        public int getCount() {
            return 3;
        }
        @Override
        public CharSequence getPageTitle(int position) {
            return tabs[position];
        }
    }
}