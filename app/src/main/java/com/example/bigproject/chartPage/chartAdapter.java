package com.example.bigproject.chartPage;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bigproject.R;
import com.example.bigproject.chartExpensesClass;

import java.io.InputStream;
import java.util.List;

public class chartAdapter extends RecyclerView.Adapter<chartAdapter.itemChartViewHolder>{

    List<chartExpensesClass> list;
    public AssetManager assetManager;
    public void setData(List<chartExpensesClass> list,AssetManager assetManager){
        this.list = list;
        this.assetManager = assetManager;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public itemChartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemchart,parent,false);
        return new itemChartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull itemChartViewHolder holder, int position) {
        chartExpensesClass chart = list.get(position);
        if(chart == null) return;
        try{
            InputStream is = assetManager.open("img/"+chart.getImgCatChart());
            Bitmap b = BitmapFactory.decodeStream(is);
            holder.imageView.setImageBitmap(b);
        }catch (Exception e){
            Log.e("CatAdapter",e.getMessage());
        }
        holder.nameCat.setText(chart.getNameCatChart());
        holder.percentCat.setText(String.valueOf(Math.round(chart.getPercentCat()*100))+" %");
        holder.countCat.setText(String.valueOf(chart.getCountCatChart())+" count");
        holder.monneyCat.setText(String.valueOf(chart.getMonneySumCat()));
        holder.proBar.setProgress((int) (chart.getPercentCat()*100));
    }

    @Override
    public int getItemCount() {
        if(list != null){
            return list.size();
        }
        return 0;
    }

    public class itemChartViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        TextView nameCat,percentCat,countCat,monneyCat;
        ProgressBar proBar;
        public itemChartViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = (ImageView) itemView.findViewById(R.id.imgItemChart);
            nameCat = (TextView) itemView.findViewById(R.id.tvItemNameCat);
            percentCat = (TextView) itemView.findViewById(R.id.tvItemPercent);
            countCat = (TextView) itemView.findViewById(R.id.tvItemCount);
            monneyCat = (TextView) itemView.findViewById(R.id.tvItemMonneyUse);

            proBar = (ProgressBar) itemView.findViewById(R.id.progressChart);
        }
    }
}
