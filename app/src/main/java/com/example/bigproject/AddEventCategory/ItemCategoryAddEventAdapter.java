package com.example.bigproject.AddEventCategory;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bigproject.ItemClickListener;
import com.example.bigproject.R;
import com.example.bigproject.Table_Category;

import java.io.InputStream;
import java.util.List;

public class ItemCategoryAddEventAdapter extends RecyclerView.Adapter<ItemCategoryAddEventAdapter.itemCatAddEventViewHolder>{

    private List<Table_Category> mListItem;
    private IClickItemCatEventListener iClickItemCatEventListener;
    public AssetManager assetManager;

    public ItemCategoryAddEventAdapter(IClickItemCatEventListener iClickItemCatEventListener) {
        this.iClickItemCatEventListener = iClickItemCatEventListener;
    }
    public void setData(List<Table_Category> list,AssetManager assetManager){
        this.mListItem = list;
        this.assetManager = assetManager;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public itemCatAddEventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_add_category_to_event,parent,false);
        return new itemCatAddEventViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull itemCatAddEventViewHolder holder, int position) {
        Table_Category cat = mListItem.get(position);
        if(cat == null) return;
        try{
            InputStream is = assetManager.open("img/"+cat.getImg_cat());
            Bitmap b = BitmapFactory.decodeStream(is);
            holder.imgCat.setImageBitmap(b);

        }catch (Exception e){
            Log.e("CatAdapter",e.getMessage());
        }
        holder.tvNameCat.setText(cat.getName_cat());
        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position) {
                iClickItemCatEventListener.ClickItem(cat);
            }
        });
    }

    @Override
    public int getItemCount() {
        if(mListItem != null )
            return mListItem.size();
        return 0;
    }

    public class itemCatAddEventViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private ImageView imgCat;
        private TextView tvNameCat;
        private ItemClickListener itemClickListener;

        public itemCatAddEventViewHolder(View itemView){
            super(itemView);

            imgCat = itemView.findViewById(R.id.img_cat_add_event);
            tvNameCat = itemView.findViewById(R.id.tv_name_cat_add_event);

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
