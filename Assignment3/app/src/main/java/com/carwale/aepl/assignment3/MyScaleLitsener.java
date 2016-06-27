package com.carwale.aepl.assignment3;

import android.graphics.Matrix;
import android.view.ScaleGestureDetector;
import android.widget.ImageView;

/**
 * Created by aepl on 27/6/16.
 */
public class MyScaleLitsener extends ScaleGestureDetector.SimpleOnScaleGestureListener{

    private float scale = 1f;
    private ImageView imageView;
    @Override
    public boolean onScale(ScaleGestureDetector detector){
        scale *= detector.getScaleFactor();
        scale = Math.max(0.1f, Math.min(5.0f, scale));
        Matrix matrix = new Matrix();
        matrix.setScale(scale, scale);
        imageView.setImageMatrix(matrix);
        return true;
    }

    public void setImageView(ImageView image){
        imageView = image;
    }
}
