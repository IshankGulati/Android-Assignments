package com.carwale.aepl.assignment5;

import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements MyBroadcastReceiver.publish,Serializable{
    public ArrayList<State> states;
    View detailsFrame;
    android.app.FragmentManager fragmentManager;
    android.app.FragmentTransaction fragmentTransaction;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_layout);
        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(getApplicationContext(), Downloader.class);
                intent.putExtra("URL", "http://www.carwale.com/webapi/newcardealers/cities/?makeid=10");
                intent.putExtra("context",MainActivity.this);
                startService(intent);

            }
        });
        detailsFrame = findViewById(R.id.details);
        fragmentManager = getFragmentManager();

//        receiver = new MyBroadcastReceiver();
//        receiver.setPublish(this);
    }


    public void startFragments(){
        boolean dualpane = detailsFrame != null && detailsFrame.getVisibility() == View.VISIBLE;
        int targetLayout = dualpane ? R.id.titles : R.id.main;
        fragmentTransaction = fragmentManager.beginTransaction();
        TitlesFragment fragment = new TitlesFragment();
        Bundle bundle = new Bundle();
        bundle.putBoolean("dual", dualpane);
        bundle.putSerializable("States", states);
        fragment.setArguments(bundle);
        fragmentTransaction.add(targetLayout, fragment);
        fragmentTransaction.commit();
    }

    @Override
    public void publishData(ArrayList<State> states){
        this.states = states;
        startFragments();
    }
}
