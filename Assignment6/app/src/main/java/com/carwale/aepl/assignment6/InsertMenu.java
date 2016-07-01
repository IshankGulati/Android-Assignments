package com.carwale.aepl.assignment6;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by aepl on 30/6/16.
 */
public class InsertMenu extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainmenu);
        final DBhelper dBhelper;
        dBhelper = DBhelper.getInstance(this);

        final EditText tv1 = (EditText) findViewById(R.id.mainmenuid);
        final EditText tv2 = (EditText) findViewById(R.id.mainmenuname);
        final EditText tv3 = (EditText) findViewById(R.id.mainmenudob);
        final EditText tv4 = (EditText) findViewById(R.id.mainmenudoj);
        final EditText tv5 = (EditText) findViewById(R.id.mainmenudes);
        final EditText tv6 = (EditText) findViewById(R.id.mainmenusal);

        Button button = (Button) findViewById(R.id.mainmenubutton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int a = Integer.parseInt(tv1.getText().toString());
                String b = tv2.getText().toString();
                String c = tv3.getText().toString();
                String d = tv4.getText().toString();
                String e = tv5.getText().toString();
                int f = Integer.parseInt(tv6.getText().toString());
                dBhelper.insertEmployeeData(a, b, c, d, e, f);
                finish();
            }
        });
    }
}
