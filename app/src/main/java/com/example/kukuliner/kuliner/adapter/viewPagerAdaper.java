package com.example.kukuliner.kuliner.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class viewPagerAdaper extends FragmentPagerAdapter{
    private final List<Fragment> fragments1 = new ArrayList<>();
    private final List<String> title1 = new ArrayList<>();

    public viewPagerAdaper(FragmentManager fm){
        super(fm);
    }
    @Override
    public Fragment getItem(int position) {
        return null;
    }

    @Override
    public int getCount() {
        return title1.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return title1.get(position);
    }

    public void addFragment(Fragment fragment, String title){
        fragments1.add(fragment);
        title1.add(title);
    }
}
