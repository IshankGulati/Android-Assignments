package com.carwale.aepl.assignment6;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by aepl on 30/6/16.
 */
public class UpdateActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        Intent intent = getIntent();
        int index = intent.getExtras().getInt("index");

        final DBhelper dBhelper;
        dBhelper = DBhelper.getInstance(this);

        final EditText tv1 = (EditText) findViewById(R.id.updateid);
        final EditText tv2 = (EditText) findViewById(R.id.updatename);
        final EditText tv3 = (EditText) findViewById(R.id.updatedob);
        final EditText tv4 = (EditText) findViewById(R.id.updatedoj);
        final EditText tv5 = (EditText) findViewById(R.id.updatedes);
        final EditText tv6 = (EditText) findViewById(R.id.updatesal);

        Cursor cursor = dBhelper.getData();
        cursor.moveToFirst();
        cursor.move(index);

        tv1.setText(cursor.getString(cursor.getColumnIndexOrThrow("id")));
        tv2.setText(cursor.getString(cursor.getColumnIndexOrThrow("name")));
        tv3.setText(cursor.getString(cursor.getColumnIndexOrThrow("dob")));
        tv4.setText(cursor.getString(cursor.getColumnIndexOrThrow("doj")));
        tv5.setText(cursor.getString(cursor.getColumnIndexOrThrow("designation")));
        tv6.setText(cursor.getString(cursor.getColumnIndexOrThrow("salary")));

        Button button = (Button) findViewById(R.id.updatebutton);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int a = Integer.parseInt(tv1.getText().toString());
                String b = tv2.getText().toString();
                String c = tv3.getText().toString();
                String d = tv4.getText().toString();
                String e = tv5.getText().toString();
                int f = Integer.parseInt(tv6.getText().toString());
                dBhelper.updateEmployeeData(a, b, c, d, e, f);
                finish();
            }
        });
    }
}
