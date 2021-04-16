package com.example.bigproject;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AbsListView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bigproject.DatabaseBigProject.DatabasePro;
import com.example.bigproject.sharePreferenceLogin.dataLocalLoginManager;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersDecoration;

import java.util.ArrayList;
import java.util.List;


public class homeFragment extends Fragment {

    private AssetManager assetManager;
    TextView date_home,monney_home,expenseCurrentMonth,incomeCurrentMonth;
    View homeview;
    user_Item_Data_Adapter uiAdapter;
    RecyclerView rv_ui;
    public static final int REQUEST_CODE = 11;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        homeview = inflater.inflate(R.layout.fragment_home,container,false);
        date_home = (TextView)homeview.findViewById(R.id.date_home_current);
        monney_home = (TextView)homeview.findViewById(R.id.Monney_home_current);
        expenseCurrentMonth = (TextView)homeview.findViewById(R.id.monney_sumEx_month);
        incomeCurrentMonth = (TextView) homeview.findViewById(R.id.monney_sumIn_month);

        assetManager = getActivity().getAssets();
        handleHeaderHomeForm(homeview);

        rv_ui = homeview.findViewById(R.id.rv_home);
        uiAdapter = new user_Item_Data_Adapter(new OnClickItemhome() {
            @Override
            public void OnClickItem(user_Item_data u) {
                clickUpdate(u);
            }
        });
        LinearLayoutManager layoutManager = new LinearLayoutManager(homeview.getContext());
        rv_ui.setLayoutManager(layoutManager);
        uiAdapter.setList_item_data(getList_Item(),assetManager,this.getContext());
        rv_ui.setAdapter(uiAdapter);

        //rv_ui.addItemDecoration(new StickyRecyclerHeadersDecoration(uiAdapter));

        ExtendedFloatingActionButton fab = (ExtendedFloatingActionButton) homeview.findViewById(R.id.fab);
        //fab.bringToFront();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Animation ani = AnimationUtils.loadAnimation(view.getContext(),R.anim.clockwise);
                fab.startAnimation(ani);
                Intent i = new Intent(homeFragment.this.getActivity(),add_event.class);
                startActivity(i);

            }
        });
        // hide floatbutton

        rv_ui.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {

                if (dy > 0 )
                { fab.hide(); }
                else{ fab.show();}
            }
        });
        loadData();
        return homeview;
    }

    private void clickUpdate(user_Item_data u) {
        Intent intent = new Intent(homeFragment.this.getActivity(), Detail_MonneyUsed.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable("object item",u);
        intent.putExtras(bundle);
        startActivityForResult(intent,REQUEST_CODE);
    }
    @Override
    public void onStart() {
        super.onStart();
        loadheaderData(homeview);
        loadData();
        rv_ui.setAdapter(uiAdapter);
        //rv_ui.removeItemDecoration(new StickyRecyclerHeadersDecoration(uiAdapter));
        while (rv_ui.getItemDecorationCount() > 0) {
            rv_ui.removeItemDecorationAt(0);
        }
        rv_ui.addItemDecoration(new StickyRecyclerHeadersDecoration(uiAdapter));
        loadExInHeader();
    }

    public void loadData(){
        uiAdapter.setList_item_data(getList_Item(),assetManager,this.getContext());
    }

    public void loadheaderData(View v){
        List<Table_Bank> listBank =  DatabasePro.getInstance(v.getContext()).bankDAO().getValuesfromIDBank(dataLocalLoginManager.getIdBank());
        if(listBank != null && !listBank.isEmpty()){
            monney_home.setText(String.valueOf(listBank.get(0).getCurrent_monney()));
            String s = listBank.get(0).getDate_start();
            date_home.setText(s.subSequence(0,3).toString() + " -" + s.subSequence(s.length()-4,s.length()));
        }else{
            monney_home.setText("0");
        }
    }

    private void handleHeaderHomeForm(View v){
        loadheaderData(v);
        RelativeLayout rl = v.findViewById(R.id.rl_edit_money_home);
        rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(homeFragment.this.getActivity(),Setting.class));
            }
        });
        loadExInHeader();
    }
    private void loadExInHeader(){
        if(getDateFromBank()){
            List<Table_Bank> list = DatabasePro.getInstance(getContext()).tranDAO().getDateTransaction(dataLocalLoginManager.getIdBank(),Integer.parseInt(dataLocalLoginManager.getIdLogin()));
            List<Double> sumExPerMonth = new ArrayList<>();
            sumExPerMonth = (List<Double>) DatabasePro.getInstance(homeview.getContext()).tranDAO().getSumExPerMonth(list.get(0).getDate_start(),list.get(0).getDate_end(),Integer.parseInt(dataLocalLoginManager.getIdLogin()));

            expenseCurrentMonth.setText(String.valueOf(sumExPerMonth.get(0)));
            if(sumExPerMonth.get(0) != null)
                expenseCurrentMonth.setText(String.valueOf(sumExPerMonth.get(0)));
            else expenseCurrentMonth.setText("0");
            //
            List<Double> sumInPerMonth = new ArrayList<>();
            sumInPerMonth = (List<Double>) DatabasePro.getInstance(homeview.getContext()).tranDAO().getSumInPerMonth(list.get(0).getDate_start(),list.get(0).getDate_end(),Integer.parseInt(dataLocalLoginManager.getIdLogin()));

            if(sumInPerMonth.get(0) != null)
                incomeCurrentMonth.setText(String.valueOf(sumInPerMonth.get(0)));
            else incomeCurrentMonth.setText("0");
        }
    }
    private boolean getDateFromBank(){
        if(dataLocalLoginManager.getIdBank() == 0) return false;
        List<Table_Bank> list = DatabasePro.getInstance(getContext()).tranDAO().getDateTransaction(dataLocalLoginManager.getIdBank(),Integer.parseInt(dataLocalLoginManager.getIdLogin()));
        return list != null && !list.isEmpty();
    }

    @NonNull
    private List<user_Item_data> getList_Item(){
        List<user_Item_data> list = new ArrayList<>();
        list = DatabasePro.getInstance(this.getContext()).tranDAO().getListData(Integer.parseInt(dataLocalLoginManager.getIdLogin()));
        if(list != null && !list.isEmpty()){
            //Toast.makeText(this.getContext(),"get dc roi nay: "+list.get(0).getDiscribe(),Toast.LENGTH_LONG).show();
        }
        return list;
    }
}
