package com.carwale.aepl.assignment5;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.app.Service;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Parcelable;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Layout;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

public class MainActivity extends AppCompatActivity{
    private static ArrayList<State> states;
    private View detailsFrame;
    private android.app.FragmentManager fragmentManager;
    private android.app.FragmentTransaction fragmentTransaction;
    private DrawerLayout drawerLayout;
    private ListView drawerListView;
    private ActionBarDrawerToggle drawerToggle;
    private CharSequence title;
    private CharSequence drawerTitle;
    private static String dURL = "http://www.carwale.com/webapi/newcardealers/cities/?makeid=10";
    private static String content = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_layout);
        title = drawerTitle = getTitle();
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        myToolbar.setTitleTextColor(Color.LTGRAY);
        setSupportActionBar(myToolbar);

        fragmentManager = getFragmentManager();
        if(savedInstanceState != null){
            states = (ArrayList<State>) savedInstanceState.getSerializable("data");
            startFragments();
        }
//        else {
////            Intent i = getIntent();
////            if (i.hasExtra("data")) {
////                states = (ArrayList<State>) i.getSerializableExtra("data");
////                startFragments();
////            }
//        }

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerListView = (ListView) findViewById(R.id.left_drawer);
        String[] drawerList = {"Download", "Dummy1", "Dummy2", "Dummy3"};
        drawerListView.setAdapter(new ArrayAdapter<String>(this,
                R.layout.drawer_textview, drawerList));

        drawerListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                selectItem(i);
            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            getSupportActionBar().setHomeButtonEnabled(true);
        }

        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout,
                myToolbar, R.string.drawer_open, R.string.drawer_close) {

            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                getSupportActionBar().setTitle(title);
                //invalidateOptionsMenu();
            }

            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getSupportActionBar().setTitle(drawerTitle);
                //invalidateOptionsMenu();
            }
        };

        drawerLayout.setDrawerListener(drawerToggle);
    }

    private void selectItem(int position){
        drawerListView.setItemChecked(position, true);
        if(position == 0){
            downloadUsingService();
            //downloadUsingAsyncTask();
        }
        drawerLayout.closeDrawer(drawerListView);
    }

    private void downloadUsingService(){
        Intent intent = new Intent(getApplicationContext(), Downloader.class);
        intent.putExtra("URL", dURL);
        startService(intent);
    }

    private void downloadUsingAsyncTask(){
        Downloader2 d = new Downloader2();
        d.execute(dURL);
        while (content == null);
        states = deserialize(content);
        startFragments();
    }

    @Override
    public void onSaveInstanceState(Bundle bundle){
        super.onSaveInstanceState(bundle);
        bundle.putSerializable("data", states);
    }
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if(intent.hasExtra("data")){
            states = (ArrayList<State>) intent.getSerializableExtra("data");
            startFragments();
        }
    }

    public void startFragments(){
        detailsFrame = findViewById(R.id.details);
        boolean dualpane = detailsFrame != null && detailsFrame.getVisibility() == View.VISIBLE;
        int targetLayout = dualpane ? R.id.titles : R.id.titlesFull;
        fragmentTransaction = fragmentManager.beginTransaction();
        TitlesFragment fragment = new TitlesFragment();
        Bundle bundle = new Bundle();
        bundle.putBoolean("dual", dualpane);
        bundle.putSerializable("States", states);
        fragment.setArguments(bundle);
        fragmentTransaction.replace(targetLayout, fragment);
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        fragmentTransaction.commit();
    }

    @Override
    public void setTitle(CharSequence title) {
        this.title = title;
        getSupportActionBar().setTitle(title);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggls
        drawerToggle.onConfigurationChanged(newConfig);
    }

    public static class Downloader2 extends AsyncTask<String, String, String>{
        @Override
        protected String doInBackground(String... objects) {
            String urlString = objects[0];
            String s = null;
            InputStream inputStream = null;
            try {
                URL url = new URL(urlString);
                inputStream = url.openConnection().getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                String line = null;
                StringBuilder sb = new StringBuilder();
                while ((line = reader.readLine()) != null){
                    sb.append(line);
                }
                s = sb.toString();

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return s;
        }

        @Override
        protected void onPostExecute(String result) {
            content = result;
        }
    }


    private ArrayList<State> deserialize(String Content){
        ArrayList<State> data = new ArrayList<>();
        JSONObject json;
        try {
            json = new JSONObject(Content);
            JSONArray jsonMainNode = json.optJSONArray("Item1");
            int length = jsonMainNode.length();
            for(int i = 0; i < length; i++){
                ArrayList<City> cities = new ArrayList<>();
                JSONObject jsonChildNode = jsonMainNode.getJSONObject(i);
                JSONArray cityArray = jsonChildNode.optJSONArray("cities");
                int childLength = cityArray.length();
                for(int j = 0; j < childLength; j++){
                    JSONObject jsonCityNode = cityArray.getJSONObject(j);
                    String cityName = jsonCityNode.optString("cityName");
                    int cityId = jsonCityNode.getInt("cityId");
                    int totalCount = jsonCityNode.getInt("totalCount");
                    City city = new City(cityName, cityId, totalCount);
                    cities.add(city);
                }
                String stateName = jsonChildNode.optString("stateName");
                int stateId = jsonChildNode.optInt("stateId");
                State state = new State(cities, stateName, stateId);
                data.add(state);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return data;
    }
}
