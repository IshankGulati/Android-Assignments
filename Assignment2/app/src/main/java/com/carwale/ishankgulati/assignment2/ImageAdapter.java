package com.carwale.ishankgulati.assignment2;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import java.util.ArrayList;

/**
 * Created by ishank.gulati on 21-06-2016.
 */
public class ImageAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<String> imageList = new ArrayList<>();

    public ImageAdapter(Context context){
        this.context = context;
    }

    public int getCount(){
        return imageList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ImageView imageView;
        if(view == null){
            imageView = new ImageView(context);

            // dimensions and appearance of the new view
            imageView.setLayoutParams(new GridView.LayoutParams(220, 220));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(8, 8, 8, 8);
        }
        else{
            imageView = (ImageView) view;
        }

        Bitmap bitmap = getBitmap(imageList.get(i), 220, 220);
        imageView.setImageBitmap(bitmap);
        return imageView;
    }

    private Bitmap getBitmap(String path, int width, int height) {
        Bitmap bitmap;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;

        options.inSampleSize = calculateImageSize(options, width, height);
        options.inJustDecodeBounds = false;
        bitmap = BitmapFactory.decodeFile(path, options);
        return bitmap;
    }

    private int calculateImageSize(BitmapFactory.Options options, int width, int height) {
        int currentHeight = options.outHeight;
        int currentWidth = options.outWidth;
        int sampleSize = 1;
        if(currentHeight > height || currentWidth > width){
            if(width > height){
                sampleSize = Math.round((float) currentHeight / (float) height);
            }
            else {
                sampleSize = Math.round((float) currentWidth / (float) width);
            }
        }

        return sampleSize;
    }

    public void add(String path){
        imageList.add(path);
    }

    public void addAllImages(Activity activity){
        Uri uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        String[] projection = {MediaStore.MediaColumns.DATA};
        Cursor cursor = activity.getContentResolver().query(uri, projection, null, null, null);

        int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
        while (cursor.moveToNext()){
            String absolutePathOfImage = cursor.getString(columnIndex);
            imageList.add(absolutePathOfImage);
        }
    }
}
