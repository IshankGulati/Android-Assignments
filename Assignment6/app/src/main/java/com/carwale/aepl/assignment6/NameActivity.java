package com.carwale.aepl.assignment6;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by aepl on 30/6/16.
 */
public class NameActivity extends AppCompatActivity{
    ArrayList<String> names = new ArrayList<>();
    private int nextActivity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_name);

        Intent intent = getIntent();
        nextActivity = intent.getExtras().getInt("index");
        int layoutId = android.R.layout.simple_list_item_activated_1;

        ListView lv = (ListView) findViewById(R.id.namelist);
        final DBhelper dBhelper;
        dBhelper = DBhelper.getInstance(this);
        Cursor cursor = dBhelper.getData();
        cursor.moveToFirst();
        do{
            String d = cursor.getString(cursor.getColumnIndexOrThrow("name"));
            names.add(d);
        }
        while (cursor.moveToNext());
        if(names.size() > 0) {
            lv.setAdapter(new ArrayAdapter<String>(this, layoutId, names));
        }
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                selectItem(i);
            }
        });
    }

    void selectItem(int position) {
        if(nextActivity == 1) {
            Intent intent = new Intent(this, DetailsActivity.class);
            intent.putExtra("index", position);
            startActivity(intent);
        }
        else if(nextActivity == 2){
            Intent intent = new Intent(this, UpdateActivity.class);
            intent.putExtra("index", position);
            startActivity(intent);
        }
    }
}
