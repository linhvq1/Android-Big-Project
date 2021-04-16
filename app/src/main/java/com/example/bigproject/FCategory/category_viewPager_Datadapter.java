package com.example.bigproject.FCategory;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.bigproject.categoriesFragment;
import com.example.bigproject.chartFragment;
import com.example.bigproject.homeFragment;

public class category_viewPager_Datadapter extends FragmentStatePagerAdapter {


    public category_viewPager_Datadapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }
    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new epensesTabFragment();
            case 1:
                return new incomeTabFragment();
            default:
                return new epensesTabFragment();
        }
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0:
                return "Expenses";
            case 1:
                return "Income";
            default:
                return "Expenses";
        }
    }
}
