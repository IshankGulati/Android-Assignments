package com.carwale.aepl.assignment4;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_layout);

        View detailsFrame = findViewById(R.id.details);
        boolean dualpane = detailsFrame != null && detailsFrame.getVisibility() == View.VISIBLE;
        int targetLayout = dualpane ? R.id.titles : R.id.main;

        android.app.FragmentManager fragmentManager = getFragmentManager();
        android.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        TitlesFragment fragment = new TitlesFragment();
        Bundle bundle = new Bundle();
        bundle.putBoolean("dual", dualpane);
        fragment.setArguments(bundle);
        fragmentTransaction.add(targetLayout, fragment);

        fragmentTransaction.commit();
    }
}
