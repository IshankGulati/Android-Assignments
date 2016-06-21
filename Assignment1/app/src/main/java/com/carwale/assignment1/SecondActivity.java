package com.carwale.assignment1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

public class SecondActivity extends AppCompatActivity {

    EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        editText = (EditText) findViewById(R.id.EditText2);
        Intent i1 = getIntent();
        String message = i1.getStringExtra("message");
        editText.setText(message);

    }


    @Override
    public void onBackPressed() {
        editText.getText();
        String ret = editText.getText().toString();
        Intent i2 = new Intent();
        i2.putExtra("returnMessage", ret);
        setResult(10, i2);
        //startActivity(i2);
        finish();
        super.onBackPressed();
    }
}
