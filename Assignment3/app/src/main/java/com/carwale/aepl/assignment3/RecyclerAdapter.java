package com.carwale.aepl.assignment3;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.zip.Inflater;

/**
 * Created by aepl on 24/6/16.
 */
public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerViewHolder> {

    ArrayList<Bitmap> bitmapList;
    Context mContext;

    public RecyclerAdapter(Context context){
        bitmapList = new ArrayList<>();
        mContext = context;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.grid_item,
                parent, false);
        RecyclerViewHolder viewHolder = new RecyclerViewHolder(layoutView, mContext, bitmapList);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        holder.image.setImageBitmap(bitmapList.get(position));
    }

    @Override
    public int getItemCount() {
        return bitmapList.size();
    }

    // getter
    public ArrayList<Bitmap> getBitmapList(){
        return bitmapList;
    }

    // setter
    public void setBitmapList(ArrayList<Bitmap> list){
        bitmapList = list;
    }

    public void addBitmap(Bitmap b){
        bitmapList.add(b);
    }
}
