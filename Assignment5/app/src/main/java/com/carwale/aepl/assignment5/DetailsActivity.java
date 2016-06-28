package com.carwale.aepl.assignment5;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class DetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
            finish();
            return;
        }

        if(savedInstanceState == null) {
            DetailsFragment detailsFragment = new DetailsFragment();
            detailsFragment.setArguments(getIntent().getExtras());
            getFragmentManager().beginTransaction().add(
                    android.R.id.content, detailsFragment).commit();
        }
    }
}
