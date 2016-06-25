package com.carwale.ishankgulati.assignment2;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.view.LayoutInflater;
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
    private ArrayList<Bitmap> bitmapList = new ArrayList<>();

    public ImageAdapter(Context context){
        this.context = context;
    }

    public int getCount(){
        return bitmapList.size();
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
        ViewHolder viewHolder;
        if(view == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(
                    context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.image_layout, null);
            viewHolder = new ViewHolder();
            viewHolder.imageView = (ImageView) view.findViewById(R.id.imageField);
            view.setTag(viewHolder);
        }
        else{
            viewHolder = (ViewHolder) view.getTag();
        }

        Bitmap bitmap = bitmapList.get(i);
        viewHolder.imageView.setImageBitmap(bitmap);
        return view;
    }

    public ArrayList<Bitmap> getBitmapList(){
        return bitmapList;
    }

    public void setBitmapList(ArrayList<Bitmap> list){
        bitmapList = list;
    }

    public void addBitmap(Bitmap b){
        bitmapList.add(b);
    }
    // used to add all the images from gallery
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

    private static class ViewHolder{
        ImageView imageView;
    }
}
