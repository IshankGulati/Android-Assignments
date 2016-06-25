package com.carwale.aepl.assignment3;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;

import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    RecyclerAdapter adapter;
    final static int NUM_COLUMNS = 3;
    private static final int CAPTURE_IMAGE_REQUEST_CODE = 10;
    RecyclerView gridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        adapter = new RecyclerAdapter(this);
        if(savedInstanceState != null){
            ArrayList<Bitmap> list = savedInstanceState.getParcelableArrayList("bitmapList");
            adapter.setBitmapList(list);
        }

        final ImageButton button = (ImageButton) findViewById(R.id.cameraButton);
        gridView = (RecyclerView) findViewById(R.id.recyclerView);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, NUM_COLUMNS);

        gridView.setAdapter(adapter);
        gridView.setLayoutManager(gridLayoutManager);

        Intent galleryIntent = getIntent();
        String action = galleryIntent.getAction();
        String type = galleryIntent.getType();

        if(Intent.ACTION_SEND.equals(action) && type != null){
            if(type.startsWith("image/")){
                handleReceivedImage(galleryIntent);
            }
        }

        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(i, CAPTURE_IMAGE_REQUEST_CODE);
            }
        });
    }

    private void handleReceivedImage(Intent intent) {
        Uri imageUri = (Uri) intent.getParcelableExtra(Intent.EXTRA_STREAM);
        if (imageUri != null) {
            try {
                Bitmap b = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
                adapter.addBitmap(b);
                adapter.notifyDataSetChanged();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outBundle){
        super.onSaveInstanceState(outBundle);
        outBundle.putParcelableArrayList("bitmapList", adapter.getBitmapList());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == CAPTURE_IMAGE_REQUEST_CODE){
            if(resultCode == RESULT_OK){
                Bundle extras = data.getExtras();
                Bitmap b = (Bitmap) extras.get("data");

                adapter.addBitmap(b);
                adapter.notifyDataSetChanged();
            }
        }
    }
}
