package com.carwale.aepl.assignment6;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

/**
 * Created by aepl on 30/6/16.
 */
public class DetailsActivity extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        Intent intent = getIntent();
        int index = intent.getExtras().getInt("index");

        final DBhelper dBhelper;
        dBhelper = DBhelper.getInstance(this);
        Cursor cursor = dBhelper.getData();
        Log.d("index", index + "");
        cursor.moveToFirst();
        if(index > 0) {
            cursor.move(index);
        }
        TextView tv1 = (TextView) findViewById(R.id.textid);
        TextView tv2 = (TextView) findViewById(R.id.textname);
        TextView tv3 = (TextView) findViewById(R.id.textdob);
        TextView tv4 = (TextView) findViewById(R.id.textdoj);
        TextView tv5 = (TextView) findViewById(R.id.textdes);
        TextView tv6 = (TextView) findViewById(R.id.textsal);

        tv1.setText(cursor.getString(cursor.getColumnIndexOrThrow("id")));
        tv2.setText(cursor.getString(cursor.getColumnIndexOrThrow("name")));
        tv3.setText(cursor.getString(cursor.getColumnIndexOrThrow("dob")));
        tv4.setText(cursor.getString(cursor.getColumnIndexOrThrow("doj")));
        tv5.setText(cursor.getString(cursor.getColumnIndexOrThrow("designation")));
        tv6.setText(cursor.getString(cursor.getColumnIndexOrThrow("salary")));

    }
}
