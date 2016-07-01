package com.carwale.aepl.assignment6;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {
    private DBhelper dBhelper;
    private String[] mainMenu = {"Insert", "View", "Update", "Update Designation", "Delete"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ListView lv = (ListView) findViewById(R.id.list);
        int layoutId = android.R.layout.simple_list_item_activated_1;

        lv.setAdapter(new ArrayAdapter<String>(this, layoutId,mainMenu));
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                selectItem(i);
            }
        });
        dBhelper = DBhelper.getInstance(getApplicationContext());

//        dBhelper.insertEmployeeData(1, "ishank", "08-09-1993", "13-06-2016", "SDE1", 8);
//        dBhelper.insertEmployeeData(2, "harsh", "01-05-1993", "13-06-2016", "SDE1", 8);
//        dBhelper.insertEmployeeData(3, "sumit", "08-02-1993", "13-06-2016", "SDE1", 8);
//        dBhelper.insertEmployeeData(4, "shubham", "07-10-1993", "13-06-2016", "SDE1", 8);
//        dBhelper.insertEmployeeData(5, "db", "08-01-1993", "13-06-2012", "SDE1", 18);

//        int i = 0;
//        do{
//            String d = cursor.getString(cursor.getColumnIndexOrThrow("designation"));
//            Log.d("Designation " + i, d);
//            i++;
//        }
//        while (cursor.moveToNext());

        dBhelper.updateDesignation("SDE2");

    }

    void selectItem(int position){
        Intent intent = new Intent();
        if(position == 0){
            intent.setClass(this, InsertMenu.class);
            intent.putExtra("index", position);
            startActivity(intent);
        }
        else if(position == 1){
            intent.setClass(this, NameActivity.class);
            intent.putExtra("index", position);
            startActivity(intent);
        }
        else if(position == 2){
            intent.setClass(this, NameActivity.class);
            intent.putExtra("index", position);
            startActivity(intent);
        }
        else if(position == 3){
            dBhelper.updateDesignation("SDE2");
        }

    }
}
