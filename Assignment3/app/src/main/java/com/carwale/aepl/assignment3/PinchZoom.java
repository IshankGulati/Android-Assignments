package com.carwale.aepl.assignment3;

import android.content.Context;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

/**
 * Created by aepl on 25/6/16.
 */
public class PinchZoom implements View.OnTouchListener {

    public static enum State {Zoom, None}
    private State curentState = State.None;
    private float initialDistance = 0;
    private PointF firstTouchLocation = new PointF();
    private PointF midPoint = new PointF();
    private Matrix matrix = new Matrix();
    private Matrix savedMatrix = new Matrix();
    Context mContext;

    PinchZoom(Context context){
        mContext = context;
    }
    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        int maskedAction = motionEvent.getActionMasked();
        PointF location = new PointF(motionEvent.getX(), motionEvent.getY());
        ImageView imageView= (ImageView) view;

        switch (maskedAction){
            case MotionEvent.ACTION_DOWN:{
                firstTouchLocation.set(location);
                //matrix = imageView.getImageMatrix();
                savedMatrix.set(matrix);
                break;
            }

            case MotionEvent.ACTION_POINTER_DOWN:{
                initialDistance = setDistance(motionEvent);
                if(initialDistance > 20f){
                    setMidPoint(motionEvent);
                    curentState = State.Zoom;
                    savedMatrix.set(matrix);
                }
                break;
            }

            case MotionEvent.ACTION_MOVE:{
                if(curentState == State.Zoom){
                    float currentDistance = setDistance(motionEvent);
                    float scaleFactor = currentDistance / initialDistance;
                    matrix.set(savedMatrix);
                    matrix.postScale(scaleFactor, scaleFactor, midPoint.x, midPoint.y);
                }
                break;
            }

            case MotionEvent.ACTION_UP:{
                curentState = State.None;
                break;
            }

            case MotionEvent.ACTION_POINTER_UP:{
                curentState = State.None;
                break;
            }

        }
        imageView.setImageMatrix(matrix);
        imageView.invalidate();
        return true;
    }

    public float setDistance(MotionEvent event){
        float distX = event.getX() - firstTouchLocation.x;
        float distY = event.getY() - firstTouchLocation.y;
        float distance = (float) Math.sqrt(distX * distX + distY * distY);
        return distance;
    }

    public void setMidPoint(MotionEvent event){
        float x = (event.getX() + firstTouchLocation.x ) / 2;
        float y = (event.getY() + firstTouchLocation.y) / 2;
        midPoint.set(x, y);
    }

}
