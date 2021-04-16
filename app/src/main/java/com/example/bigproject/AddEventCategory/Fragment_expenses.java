package com.example.bigproject.AddEventCategory;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.bigproject.DatabaseBigProject.DatabasePro;
import com.example.bigproject.FCategory.CategoryAdapter;
import com.example.bigproject.R;
import com.example.bigproject.Table_Category;
import com.example.bigproject.add_event;
import com.example.bigproject.sharePreferenceLogin.dataLocalLoginManager;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.ArrayList;
import java.util.List;

public class Fragment_expenses extends Fragment {

    private AssetManager assetManager;
    private List<Table_Category> mListCat;
    private IClickItemCatEventListener iClickListener;
    ItemCategoryAddEventAdapter itemAdapter;
//    public interface DataBoy{
//        public void bigcityBoy(Table_Category cat);
//    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_expenses, container, false);
        assetManager = getActivity().getAssets();
        RecyclerView rcv_expenses = view.findViewById(R.id.rcv_add_expenses);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        rcv_expenses.setLayoutManager(layoutManager);
        itemAdapter = new ItemCategoryAddEventAdapter(new IClickItemCatEventListener() {
            @Override
            public void ClickItem(Table_Category cat) {
                //iClickListener.ClickItem(cat);
                Toast.makeText(getContext(),cat.getName_cat(),Toast.LENGTH_SHORT).show();
                dataLocalLoginManager.setCategory(cat);
                //getActivity().getFragmentManager().popBackStack();
                // BROADCAST
                String actionName = "CLOSE_BOTTOM_SHEET";
                Intent intent = new Intent(actionName);
                //Thiết lập tên để cho Receiver nhận được thì biết đó là loại Intent
                intent.setAction(actionName);
                //Dữ liệu gắn vào Intent thiết lập bằng putExtra với (tên, dữ liệu), dữ liệu là
                //các kiểu cơ bản Int, String ... hoặc các loại đối tượng lớp kế thừa từ Serializable
                intent.putExtra("dataname", cat.getName_cat());
                //Thực hiện lan truyền Intent trong hệ thống
                LocalBroadcastManager.getInstance(getContext()).sendBroadcast(intent);

            }
        });
        mListCat = new ArrayList<>();
        itemAdapter.setData(mListCat,assetManager);
        rcv_expenses.setAdapter(itemAdapter);
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL);
        rcv_expenses.addItemDecoration(itemDecoration);
        loaddata();
        return view;
    }
    @Override
    public void onStart() {
        super.onStart();
        // onStart -> onResume -> app running -> onPause -> onStop -> onStart
        Log.i("On Start","okokokokok");
        loaddata();
    }


    public void loaddata(){
        mListCat = DatabasePro.getInstance(this.getContext()).catDAO().getListExCategory();
        itemAdapter.setData(mListCat,assetManager);
    }
}

