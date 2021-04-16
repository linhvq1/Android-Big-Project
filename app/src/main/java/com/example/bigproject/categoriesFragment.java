package com.example.bigproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.bigproject.FCategory.category_viewPager_Datadapter;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.tabs.TabLayout;

public class categoriesFragment extends Fragment {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private View mview;
    ExtendedFloatingActionButton fabt;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        mview = inflater.inflate(R.layout.fragment_categories,container,false);

        tabLayout = mview.findViewById(R.id.tab_layout_main_categories);
        viewPager = mview.findViewById(R.id.vp_main_categories);
        category_viewPager_Datadapter adapter = new category_viewPager_Datadapter(getChildFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        viewPager.setAdapter(adapter);

        tabLayout.setupWithViewPager(viewPager);
        fabt =(ExtendedFloatingActionButton) mview.findViewById(R.id.fab_main_categories);
        fabt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(categoriesFragment.this.getActivity(),Add_Category_Activity.class);
                startActivity(i);
            }
        });
        return mview;
    }
}
