package com.carwale.ishankgulati.assignment2;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private Uri imageUri;
    private String imagePath;
    private static final int CAPTURE_IMAGE_REQUEST_CODE = 10;
    private GridView grid;
    private static Bitmap b = null;
    private ImageAdapter imageAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ImageButton button = (ImageButton) findViewById(R.id.cameraButton);
        grid = (GridView)findViewById(R.id.gridview);
        imageAdapter = new ImageAdapter(this);
        grid.setAdapter(imageAdapter);

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

//                if (i.resolveActivity(getPackageManager()) != null) {
//                    File photoFile = null;
//                    try{
//                        photoFile = getOutputImage();
//                    }
//                    catch (IOException ex){
//
//                    }
//                    if(photoFile != null){
//                        imageUri = Uri.fromFile(photoFile);
//                        i.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
//                        startActivityForResult(i, CAPTURE_IMAGE_REQUEST_CODE);
//                    }
//                }
            }
        });
    }

    private void handleReceivedImage(Intent intent) {
//        Bundle extras = intent.getExtras();
//        Bitmap b = (Bitmap) extras.get("data");
//
//        imageAdapter.addBitmap(b);
//        imageAdapter.notifyDataSetChanged();
        Uri imageUri = (Uri) intent.getParcelableExtra(Intent.EXTRA_STREAM);
        if (imageUri != null) {
            try {
                Bitmap b = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
                imageAdapter.addBitmap(b);
                imageAdapter.notifyDataSetChanged();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    private File getOutputImage() throws IOException{
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File mediaStorageDirectory = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), "MyCameraApp");
        if (! mediaStorageDirectory.exists()){
            if (! mediaStorageDirectory.mkdirs()){
                Log.d("MyCameraApp", "failed to create directory");
                return null;
            }
        }
        File mediaFile = new File(mediaStorageDirectory + File.separator + "IMG"
                + timeStamp + ".jpg");

        return mediaFile;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == CAPTURE_IMAGE_REQUEST_CODE){
            if(resultCode == RESULT_OK){

                Bundle extras = data.getExtras();
                Bitmap b = (Bitmap) extras.get("data");

                imageAdapter.addBitmap(b);
                imageAdapter.notifyDataSetChanged();
            }
        }
    }

//    private void saveBitmap(Bitmap b){
//        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
//        File imageDirectory = new File(IMAGE_PATH);
//        if(!imageDirectory.exists()) {
//            imageDirectory.mkdirs();
//        }
//        String name = "IMG" + timeStamp + ".jpg";
//        try {
//            FileOutputStream out = new FileOutputStream(name);
//            b.compress(Bitmap.CompressFormat.JPEG, 90, out);
//            out.close();
//        } catch (FileNotFoundException e) {
//            e.getMessage();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

//    private ArrayList<String> getStoredImages(){
//        ArrayList<String> temp = new ArrayList<>();
//
//        File file = new File(IMAGE_PATH);
//        if(file.exists()) {
//            File[] files = file.listFiles();
//            for (File tempFile : files) {
//                if (tempFile.isDirectory()) continue;
//                temp.add(tempFile.getPath());
//            }
//        }
//        return temp;
//        //Log.d("Error", "Cannot add to array");
//    }


}
