package com.carwale.aepl.assignment3;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.widget.ImageView;

import java.util.ArrayList;

/**
 * Created by aepl on 25/6/16.
 */
public class ViewPagerActivity extends AppCompatActivity {

    ViewPager viewPager;
    MyPagerAdapter adapter;
    int currentPage;
    ArrayList<Bitmap> bitmapList;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pager_view);

        int position;
        adapter = new MyPagerAdapter(this);

        if(savedInstanceState != null){
            bitmapList = savedInstanceState.getParcelableArrayList("imageList");
            position = savedInstanceState.getInt("position");
        }
        else {
            Intent i = getIntent();
            bitmapList = i.getExtras().getParcelableArrayList("bitmapList");
            position = i.getExtras().getInt("position");
        }

        adapter.setList(bitmapList);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(position);
        viewPager.setOffscreenPageLimit(bitmapList.size());
        //viewPager.setOnTouchListener(new PinchZoom());
        viewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener(){

            @Override
            public void onPageSelected(int position){
                currentPage = position;
            }
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outBundle){
        super.onSaveInstanceState(outBundle);
        outBundle.putParcelableArrayList("imageList", bitmapList);
        outBundle.putInt("position", currentPage);
    }

}
