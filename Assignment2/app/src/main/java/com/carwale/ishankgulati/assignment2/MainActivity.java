package com.carwale.ishankgulati.assignment2;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        GridView gridView = (GridView) findViewById(R.id.gridview);
        ImageAdapter imageAdapter = new ImageAdapter(this);

        gridView.setAdapter(imageAdapter);

//        String externalDirectoryPath = Environment.getExternalStorageDirectory().getAbsolutePath();
//        Log.d("path", externalDirectoryPath);
//        String imageDirectoryPath = externalDirectoryPath + "/images/";
//        Log.d("image path", imageDirectoryPath);
//        Toast.makeText(getApplicationContext(), imageDirectoryPath, Toast.LENGTH_LONG).show();
//
//        File imageDirectory = new File(imageDirectoryPath);
//        File[] files = imageDirectory.listFiles();
//        Log.d("Files", "" + files.length);
//        for(File file : files){
//            imageAdapter.add(file.getAbsolutePath());
//        }
        imageAdapter.addAllImages(this);
    }

}
