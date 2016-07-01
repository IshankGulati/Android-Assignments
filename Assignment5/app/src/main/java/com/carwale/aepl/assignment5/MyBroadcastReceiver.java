package com.carwale.aepl.assignment5;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by aepl on 28/6/16.
 */
public class MyBroadcastReceiver extends BroadcastReceiver {
    private ArrayList<State> states = new ArrayList<>();
    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        if(bundle != null){
            String data = intent.getStringExtra("OUTPUT");
            deserialize(data);
            int result = intent.getExtras().getInt("RESULT");
            if(result == Activity.RESULT_OK){
                Toast.makeText(context, "Download Completed", Toast.LENGTH_LONG).show();

                Intent i = new Intent(context, MainActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                i.putExtra("data", states);
                context.startActivity(i);
            }
            else {
                Toast.makeText(context, "Download Failed", Toast.LENGTH_LONG).show();

            }
        }
    }

    private void deserialize(String Content){
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
                states.add(state);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
