package com.example.bigproject;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import java.util.ArrayList;

public class GridAdapter extends BaseAdapter {
    Context context;
    ArrayList<Bitmap> logo;
    LayoutInflater inflater;

    public GridAdapter(Context context, ArrayList<Bitmap> logo) {
        this.context = context;
        this.logo = logo;
        inflater = (LayoutInflater.from(context));
    }

    @Override
    public int getCount() {
        return logo.size();
    }

    @Override
    public Object getItem(int position) {
        return logo.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @SuppressLint("ViewHolder")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = inflater.inflate(R.layout.layout_gridview,null);
        ImageView icon = (ImageView) convertView.findViewById(R.id.icon);
        icon.setImageBitmap(logo.get(position));
        return convertView;
    }
}
