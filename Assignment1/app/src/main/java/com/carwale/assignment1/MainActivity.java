package com.carwale.assignment1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class MainActivity extends AppCompatActivity {
    EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = (EditText) findViewById(R.id.EditText1);

        final Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                final String input = editText.getText().toString();
                if(input.length() != 0) {
                    Intent i1 = new Intent(getApplicationContext(), SecondActivity.class);
                    i1.putExtra("message", input);
                    startActivityForResult(i1, 10);
                    //finish();
                }
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent i){
        super.onActivityResult(requestCode, resultCode, i);
        if(requestCode == 10){
            String msg = i.getStringExtra("returnMessage");
            if(msg.length() != 0) {
                editText.setText(msg);
            }
        }
    }
}
