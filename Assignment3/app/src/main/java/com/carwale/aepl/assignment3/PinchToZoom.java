package com.carwale.aepl.assignment3;

import android.content.Context;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.ImageView;

/**
 * Created by aepl on 27/6/16.
 */
public class PinchToZoom implements View.OnTouchListener{

    private ScaleGestureDetector SGD;
    private MyScaleLitsener litsener = new MyScaleLitsener();
    PinchToZoom(Context  context){
        SGD = new ScaleGestureDetector(context, litsener);
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        litsener.setImageView((ImageView) view);
        SGD.onTouchEvent(motionEvent);
        return true;
    }

}
