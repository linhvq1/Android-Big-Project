package com.example.bigproject;

import android.content.res.AssetManager;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bigproject.DatabaseBigProject.DatabasePro;
import com.example.bigproject.FCategory.epensesTabFragment;
import com.example.bigproject.chartPage.chartAdapter;
import com.example.bigproject.sharePreferenceLogin.dataLocalLoginManager;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.List;

public class chartFragment extends Fragment {

    private MaterialButton bt_date,bt_ex,bt_in;
    private PieChart pieChart;
    List<Table_Bank> bank;
    List<chartExpensesClass> ch;
    private AssetManager assetManager;
    private RecyclerView recyclerViewChart;
    private chartAdapter chaAdapter;
    //List<chartExpensesClass> listCha;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_chart,container,false);

        ch = new ArrayList<>();
        assetManager = getActivity().getAssets();
        pieChart = view.findViewById(R.id.activity_main_piechart);
        bt_date = (MaterialButton) view.findViewById(R.id.bt_date_chart);
        setTextForBtDate();
        bt_ex = (MaterialButton) view.findViewById(R.id.id_btExpenses);
        bt_in = (MaterialButton) view.findViewById(R.id.id_btIncome);
        setupPieChart();
        loadListData();
        loadPieChartData();
        recyclerViewChart = (RecyclerView) view.findViewById(R.id.rcv_chart);
        //listCha = new ArrayList<>();
        chaAdapter = new chartAdapter();
        chaAdapter.setData(ch,assetManager);
        LinearLayoutManager layoutManager = new LinearLayoutManager(chartFragment.this.getContext());
        recyclerViewChart.setLayoutManager(layoutManager);
        recyclerViewChart.setAdapter(chaAdapter);
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL);
        recyclerViewChart.addItemDecoration(itemDecoration);

        setBackcolorButton(bt_ex);

        bt_ex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updatedataEX();
                setBackcolorButton(bt_ex);
                setdefBackcolorButton(bt_in);
            }
        });
        bt_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updatedataIn();
                setBackcolorButton(bt_in);
                setdefBackcolorButton(bt_ex);

            }
        });
        return view;
    }
    public void setdefBackcolorButton(MaterialButton mb){
        mb.setIconResource(0);
        mb.setBackgroundColor(Color.LTGRAY);
        mb.setTextColor(Color.BLACK);
    }
    public void setBackcolorButton(MaterialButton mb){
        mb.setIconResource(R.drawable.round_done_outline_white_36);
        mb.setBackgroundColor(Color.BLUE);
        mb.setTextColor(Color.WHITE);
    }
    private void updatedataIn() {
        bank = DatabasePro.getInstance(getContext()).bankDAO().getValuesfromIDBank(dataLocalLoginManager.getIdBank());
        if(bank == null || bank.isEmpty()) return;
        ch = DatabasePro.getInstance(getContext()).tranDAO().InChart(Integer.parseInt(dataLocalLoginManager.getIdLogin()),bank.get(0).getDate_start(),bank.get(0).getDate_end());
        if(ch == null || ch.isEmpty()) return;
        chaAdapter.setData(ch,assetManager);
        loadPieChartData();
    }

    private void updatedataEX() {
        loadListData();
        chaAdapter.setData(ch,assetManager);
        loadPieChartData();
    }

    public void setTextForBtDate(){
        List<Table_Bank> b = DatabasePro.getInstance(getContext()).bankDAO().getValuesfromIDBank(dataLocalLoginManager.getIdBank());
        if(b != null && !b.isEmpty()){
            bt_date.setText(b.get(0).getDate_start() + " - " + b.get(0).getDate_end());
        }else{
            bt_date.setText("Empty");
        }
    }
    private void setupPieChart() {
        pieChart.setDrawHoleEnabled(true);
        pieChart.setUsePercentValues(true);
        pieChart.setEntryLabelTextSize(12);
        pieChart.setEntryLabelColor(Color.BLACK);
        pieChart.setCenterText("Spending by Category");
        pieChart.setCenterTextSize(20);
        pieChart.getDescription().setEnabled(false);

        Legend l = pieChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.CENTER);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setDrawInside(false);
        l.setEnabled(true);
    }
    private void loadListData(){
        bank = DatabasePro.getInstance(getContext()).bankDAO().getValuesfromIDBank(dataLocalLoginManager.getIdBank());
        if(bank == null || bank.isEmpty()) return;
        ch = DatabasePro.getInstance(getContext()).tranDAO().ExChart(Integer.parseInt(dataLocalLoginManager.getIdLogin()),bank.get(0).getDate_start(),bank.get(0).getDate_end());
        if(ch == null || ch.isEmpty()) return;
    }
    private void loadPieChartData() {
        ArrayList<PieEntry> entries = new ArrayList<>();

        //loadListData();
        if(ch != null && !ch.isEmpty()){
        for (chartExpensesClass cha : ch) {
            entries.add(new PieEntry((float) cha.getPercentCat(), cha.getNameCatChart()));
        }
        }
        ArrayList<Integer> colors = new ArrayList<>();
        for (int color: ColorTemplate.MATERIAL_COLORS) {
            colors.add(color);
        }

        for (int color: ColorTemplate.VORDIPLOM_COLORS) {
            colors.add(color);
        }

        PieDataSet dataSet = new PieDataSet(entries, "Expense Category");
        dataSet.setColors(colors);

        PieData data = new PieData(dataSet);
        data.setDrawValues(true);
        data.setValueFormatter(new PercentFormatter(pieChart));
        data.setValueTextSize(12f);
        data.setValueTextColor(Color.BLACK);

        pieChart.setData(data);
        pieChart.invalidate();

        pieChart.animateY(1400, Easing.EaseInOutQuad);
    }
}
