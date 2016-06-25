package com.carwale.aepl.assignment3;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;


/**
 * Created by aepl on 24/6/16.
 */
public class RecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    ImageView image;
    Context mContext;
    ArrayList<Bitmap> bitmapList;

    public RecyclerViewHolder(View itemView, Context context, ArrayList<Bitmap> list) {
        super(itemView);
        image = (ImageView) itemView.findViewById(R.id.imageView);
        mContext = context;
        bitmapList = list;

        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        //Toast.makeText(mContext, "Image " + getAdapterPosition(), Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(mContext, ViewPagerActivity.class);
        intent.putParcelableArrayListExtra("bitmapList", bitmapList);
        intent.putExtra("position", getAdapterPosition());
        mContext.startActivity(intent);
    }
}
