package com.example.bigproject.FCategory;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bigproject.R;
import com.example.bigproject.Table_Category;

import java.io.InputStream;
import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {

    private List<Table_Category> mlistCategory;
    public AssetManager assetManager;
    private IClickItemCat iClickItemCat;

    public interface IClickItemCat{
        void updateCat(Table_Category cat);

        void deleteCat(Table_Category cat);
    }
    public CategoryAdapter (IClickItemCat iClickItemCat){
        this.iClickItemCat = iClickItemCat;
    }
    public void setData(List<Table_Category> list,AssetManager assetManager){
        this.mlistCategory = list;
        this.assetManager = assetManager;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_category,parent,false);
        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        Table_Category cat = mlistCategory.get(position);
        if(cat == null) return;
        try{
            InputStream is = assetManager.open("img/"+cat.getImg_cat());
            Bitmap b = BitmapFactory.decodeStream(is);
            holder.imgCat.setImageBitmap(b);

        }catch (Exception e){
            Log.e("CatAdapter",e.getMessage());
        }
        holder.nameCat.setText(cat.getName_cat());
        holder.btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iClickItemCat.updateCat(cat);
            }
        });
        holder.btnDeleteItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iClickItemCat.deleteCat(cat);
            }
        });
    }

    @Override
    public int getItemCount() {
        if(mlistCategory != null){
            return mlistCategory.size();
        }
        return 0;
    }

    public class CategoryViewHolder extends RecyclerView.ViewHolder{
        private ImageView imgCat;
        private TextView nameCat;
        private ImageButton btnUpdate,btnDeleteItem;

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            imgCat = itemView.findViewById(R.id.img_cat_view);
            nameCat = itemView.findViewById(R.id.name_cat_view);
            btnUpdate = itemView.findViewById(R.id.btn_update_item);
            btnDeleteItem = itemView.findViewById(R.id.bt_delete);
        }
    }
}
