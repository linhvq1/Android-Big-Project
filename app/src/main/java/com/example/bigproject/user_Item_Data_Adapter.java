package com.example.bigproject;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bigproject.DatabaseBigProject.DatabasePro;
import com.example.bigproject.sharePreferenceLogin.dataLocalLoginManager;
import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersAdapter;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


///////////////////////
public class user_Item_Data_Adapter extends RecyclerView.Adapter<user_Item_Data_Adapter.itemViewHolder> implements StickyRecyclerHeadersAdapter<user_Item_Data_Adapter.headerViewHolder> {
    public AssetManager assetManager;
    private List<user_Item_data> list_item_data;
    private Context context;
    public void setList_item_data(List<user_Item_data> list,AssetManager assetManager,Context context){
        this.list_item_data = list;
        this.assetManager = assetManager;
        this.context = context;
        notifyDataSetChanged();
    }

    OnClickItemhome onClickItemhome;

    public user_Item_Data_Adapter(OnClickItemhome onClickItemhome) {
        this.onClickItemhome = onClickItemhome;
    }

    @NonNull
    @Override
    public itemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user_detail,parent,false);

        return new itemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull itemViewHolder holder, int position) {
        user_Item_data i_data= list_item_data.get(position);
        if(i_data == null) return;
        try{
            InputStream is = assetManager.open("img/"+i_data.getResource_img());
            Bitmap b = BitmapFactory.decodeStream(is);
            holder.imageView.setImageBitmap(b);

        }catch (Exception e){
            Log.e("CatAdapter",e.getMessage());
        }
        //holder.imageView.setImageResource(i_data.getResource_img_id());
        holder.tv_name_categories_items.setText(i_data.getName_categories());
        holder.tv_name_categories_describe.setText(i_data.getDiscribe());
        if(i_data.getTypeCat().equals("Expenses")){
            holder.tv_used_money_item.setText("-" + i_data.getMoney_used());
        }
        if(i_data.getTypeCat().equals("Income")){
            holder.tv_used_money_item.setText("+" + i_data.getMoney_used());
        }

        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position) {
                    onClickItemhome.OnClickItem(i_data);
            }
        });
    }
    public long convertToId(@NonNull String s) {
        long l = 0;
        if(s.length() == 10) {
            for(int i = 0;i<s.length();i++) {
                long temp =0;
                temp = s.subSequence(0,10).charAt(i);
                l += temp;
            }
        }else {
            for(int i = 0;i<s.length();i++) {
                long temp =0;
                temp = s.subSequence(0,11).charAt(i);
                l += temp;
            }
        }
        return l;
    }
    @Override
    public long getHeaderId(int position) {
        return convertToId(list_item_data.get(position).getDate_time());
    }

    @Override
    public headerViewHolder onCreateHeaderViewHolder(@NonNull ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user,parent,false);

        return new headerViewHolder(view);
    }

    @Override
    public void onBindHeaderViewHolder(@NonNull headerViewHolder headerVHolder, int i) {
        user_Item_data i_data= list_item_data.get(i);
        headerVHolder.item1.setText(i_data.getDate_time());
        List<Double> tranE = new ArrayList<>();
        tranE = DatabasePro.getInstance(context).tranDAO().getSumExpenses(Integer.parseInt(dataLocalLoginManager.getIdLogin()),i_data.getDate_time());
        if(tranE.get(0) != null){
            i_data.setExpense(tranE.get(0));
            headerVHolder.item2.setText(String.valueOf(i_data.getExpense()));}
        else headerVHolder.item2.setText("0");

        List<Double> tranI = new ArrayList<>();
        tranI = DatabasePro.getInstance(context).tranDAO().getSumIncome(Integer.parseInt(dataLocalLoginManager.getIdLogin()),i_data.getDate_time());
        if(tranI.get(0) != null){
            i_data.setIncome(tranI.get(0));
            headerVHolder.item3.setText(String.valueOf(i_data.getIncome()));}
        else headerVHolder.item3.setText("0");
    }

    @Override
    public int getItemCount() {
        if(list_item_data != null)
        {
            return list_item_data.size();
        }
        return 0;
    }

    public static class headerViewHolder extends RecyclerView.ViewHolder{
        private final TextView item1;
        private final TextView item2;
        private final TextView item3;

        public headerViewHolder(@NonNull View itemView) {
            super(itemView);
            item1 = itemView.findViewById(R.id.tv_item_date);
            item2 = itemView.findViewById(R.id.tv_item_expense);
            item3 = itemView.findViewById(R.id.tv_item_income);
        }
    }
    public static class itemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private final ImageView imageView;
        private final TextView tv_name_categories_items;
        private final TextView tv_name_categories_describe;
        private final TextView tv_used_money_item;
        private ItemClickListener itemClickListener;
        public itemViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.icon_categories);
            tv_name_categories_items = itemView.findViewById(R.id.name_categories_items);
            tv_name_categories_describe = itemView.findViewById(R.id.name_categories_describe);
            tv_used_money_item = itemView.findViewById(R.id.used_money_item);

            itemView.setOnClickListener(this);
        }
        public void setItemClickListener(ItemClickListener itemClickListener){
            this.itemClickListener = itemClickListener;
        }
        @Override
        public void onClick(View v) {
            itemClickListener.onClick(v,getAdapterPosition());
        }

    }
}


