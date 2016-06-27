package com.carwale.aepl.assignment3;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.Point;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.view.PagerAdapter;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by aepl on 24/6/16.
 */
public class MyPagerAdapter extends PagerAdapter {
    private ArrayList<ImageView> imageViewList;
    private ArrayList<Bitmap> bitmapArrayList;
    private Context mContext;

    public MyPagerAdapter(Context mContext){
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return imageViewList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup parent, int position){
        RelativeLayout.LayoutParams params =
                new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
                        RelativeLayout.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE);
        params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);
        params.addRule(RelativeLayout.ALIGN_PARENT_END, RelativeLayout.TRUE);
        params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
        params.addRule(RelativeLayout.ALIGN_PARENT_START, RelativeLayout.TRUE);

        ImageView imageView = imageViewList.get(position);
        imageView.setScaleType(ImageView.ScaleType.MATRIX);
        imageView.setLayoutParams(params);
        imageView.setOnTouchListener(new PinchToZoom(mContext));
        if(imageView.getParent() != null){
            ((ViewGroup) imageView.getParent()).removeView(imageView);
        }

        parent.addView(imageView);
        return imageView;
    }

    @Override
    public void destroyItem(ViewGroup parent, int position, Object view){
        imageViewList.remove(position);
    }

    public void setList(ArrayList<Bitmap> list){
        bitmapArrayList = list;
        imageViewList = new ArrayList<>();
        for (Bitmap b : bitmapArrayList) {
            ImageView image = new ImageView(mContext);
            image.setImageBitmap(b);
            imageViewList.add(image);
        }
    }

    public ArrayList<Bitmap> getList(){
        return bitmapArrayList;
    }
}
