package com.example.bigproject.FCategory;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.bigproject.DatabaseBigProject.DatabasePro;
import com.example.bigproject.R;
import com.example.bigproject.Table_Category;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

import java.util.ArrayList;
import java.util.List;


public class epensesTabFragment extends Fragment {
    private AssetManager assetManager;
    private RecyclerView recyclerView;
    //private Button add,load;
    private CategoryAdapter adapter;
    private List<Table_Category> list;
    private View mViewExpenses;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mViewExpenses =  inflater.inflate(R.layout.fragment_epenses_tab, container, false);
        assetManager = getActivity().getAssets();
        recyclerView = mViewExpenses.findViewById(R.id.rcv_cat);

        adapter = new CategoryAdapter(new CategoryAdapter.IClickItemCat() {
            @Override
            public void updateCat(Table_Category cat) {
                CliclupdateCat(cat);
            }

            @Override
            public void deleteCat(Table_Category cat) {
                ClickdeleteCat(cat);
            }
        });
        list = new ArrayList<>();
        adapter.setData(list,assetManager);
        LinearLayoutManager layoutManager = new LinearLayoutManager(epensesTabFragment.this.getContext());
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setAdapter(adapter);
        // onStart -> onResume -> app running -> onPause -> onStop -> onStart
        loaddata();

        return mViewExpenses;

    }
    @Override
    public void onResume() {
        super.onResume();
        Log.i("On resume","ok");
            loaddata();
    }

    @Override
    public void onStart() {
        super.onStart();
        // onStart -> onResume -> app running -> onPause -> onStop -> onStart
        Log.i("On Start","okokokokok");
        loaddata();
    }


    public void loaddata(){
        list = DatabasePro.getInstance(this.getContext()).catDAO().getListExCategory();
        adapter.setData(list,assetManager);
    }

    private void ClickdeleteCat(Table_Category cat) {
       AlertDialog dia = new AlertDialog.Builder(this.getContext())
                .setTitle("Confirm delete category")
                .setMessage("Are you sure?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DatabasePro.getInstance(epensesTabFragment.this.getContext()).catDAO().deleteCat(cat);
                        Toast.makeText(epensesTabFragment.this.getContext(),"Delete Successfully",Toast.LENGTH_SHORT).show();
                        loaddata();
                    }
                })
                .setNegativeButton("No",null)
                .show();
       dia.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.GRAY);
        dia.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.GRAY);
    }

    private void CliclupdateCat(Table_Category cat) {
        Intent i = new Intent(epensesTabFragment.this.getActivity(),Edit_Category.class);
        Bundle bd = new Bundle();
        bd.putParcelable("data_item",cat);
        i.putExtras(bd);
        startActivityForResult(i,10);
        loaddata();
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 10 && requestCode == Activity.RESULT_OK){
            loaddata();
        }
    }
}