package com.example.bigproject;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class viewPager_Datadapter extends FragmentStatePagerAdapter {


    public viewPager_Datadapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }
    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new homeFragment();
            case 1:
                return new categoriesFragment();
            case 2:
                return new chartFragment();
            default:
                return new homeFragment();
        }
    }

    @Override
    public int getCount() {
        return 3;
    }
}
