package com.example.bigproject;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.bigproject.AddEventCategory.Fragment_expenses;
import com.example.bigproject.AddEventCategory.Fragment_income;

public class viewPager_Adapter_add_event_categories extends FragmentStateAdapter {

    public viewPager_Adapter_add_event_categories(@NonNull FragmentActivity fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                return new Fragment_expenses();
            case 1:
                return new Fragment_income();
            default:
                return new Fragment_expenses();
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
