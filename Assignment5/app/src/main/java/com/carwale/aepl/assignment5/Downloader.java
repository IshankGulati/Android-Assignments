package com.carwale.aepl.assignment5;

import android.app.Activity;
import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.os.Parcelable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by aepl on 28/6/16.
 */
public class Downloader extends IntentService{

    private int result = Activity.RESULT_CANCELED;
    private String Content;
    public Downloader(String name) {
        super(name);
    }
    public Downloader(){
        super("Downloader");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        String urlString = intent.getExtras().getString("URL");
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
            result = Activity.RESULT_OK;
            Content = sb.toString();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Intent intent1 = new Intent("com.carwale.aepl.assignment5");
        intent1.putExtra("OUTPUT", Content);
        intent1.putExtra("RESULT", result);
        sendBroadcast(intent1);

    }
}
