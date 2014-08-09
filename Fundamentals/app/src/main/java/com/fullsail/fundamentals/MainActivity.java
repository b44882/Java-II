package com.fullsail.fundamentals;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends Activity {

    public final String TAG = "MainActivity.TAG";
    ArrayAdapter adapter;
    FragmentManager fragmentManager;
    FragmentTransaction trans;

    @Override
    protected void onCreate(Bundle _savedInstanceState) {
        super.onCreate(_savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        final ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        setContentView(R.layout.activity_main);
        checkConnectivity(connectivityManager);
        //API Pull
        String symbol = "test";
        symbol = symbol.replace(" ", "+");
        try{
            String urlString = "https://www.googleapis.com/youtube/v3/search?part=snippet&maxResults=5&q=" + symbol + "&type=video&videoCaption=closedCaption&key=AIzaSyD0m9TrFUsKqIwYCCyoX3ERVlSYTWm-FZk";
            URL queryURL = new URL(urlString);
            new GetSearchTask().execute(queryURL);
        } catch (Exception e) {

        }

        //Fragment Placement
        fragmentManager = getFragmentManager();
        trans = fragmentManager.beginTransaction();

        DetailFragment detailFragment = DetailFragment.newInstance();
        trans.replace(R.id.detail_fragment, detailFragment, DetailFragment.TAG);
        trans.commit();

    }

    public void checkConnectivity (ConnectivityManager manager){
        NetworkInfo netInfo = manager.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnected()){

        } else {

        }
    }

    private class GetSearchTask extends AsyncTask<URL, Integer, JSONObject> {

        @Override
        protected void onPreExecute(){

        }
        protected JSONObject doInBackground(URL... urls) {
            String jsonString = "";
            for(URL queryURL : urls){
                try{
                    URLConnection conn = queryURL.openConnection();
                    jsonString = IOUtils.toString(conn.getInputStream());
                } catch (Exception e){
                    Log.e(TAG,"Could not establish URLConnection");
                    return null;
                }
            }

            objectSerialize(jsonString);
            openObjectSerialize();

            JSONObject apiData;

            try{
                apiData = new JSONObject(jsonString);
            } catch (Exception e){
                Log.e(TAG,"Cannot convert API response to JSON");
                apiData = null;
            }

            try{
                Log.e(TAG,"API JSON data received");
            } catch (Exception e){
                Log.e(TAG,"Could not parse data record.");
                apiData = null;
            }
            return apiData;
        }


        protected void onPostExecute(JSONObject apiData){



            JSONObject currentObject = null;
            JSONArray apiDataArray;
            ArrayList myList = null;

            if (apiData != null)
            {
                try {
                    apiDataArray = (apiData!= null) ? apiData.getJSONArray("items") : null;
                    for (int i = 0; i < apiDataArray.length(); i++)
                    {
                        currentObject = (apiDataArray != null) ? apiDataArray.getJSONObject(i).getJSONObject("snippet") : null;
                        if (myList == null){
                            myList = new ArrayList();
                        }
                        myList.add(buildYoutubeItem(currentObject));
                        Log.e(TAG, "Item " + i + " added");
                    }
                    Log.e(TAG, "API List compiled");
                    trans = fragmentManager.beginTransaction();

                    MasterFragment masterFragment = MasterFragment.newInstance(myList);
                    trans.replace(R.id.master_fragment, masterFragment, MasterFragment.TAG);
                    trans.commit();




                } catch (JSONException e) {
                    Log.e(TAG, "Error Setting Up Display");
                }

            }

        }
    }

    public YoutubeItem buildYoutubeItem (JSONObject object){

        YoutubeItem returnedItem = null;

        try {
            String publishedAt = (object != null) ? object.getString("publishedAt") : null;
            String channelTitle = (object != null) ? object.getString("channelTitle") : null;
            String videoTitle = (object != null) ? object.getString("title") : null;
            String description = (object != null) ? object.getString("description") : null;
            returnedItem = new YoutubeItem(publishedAt, channelTitle, videoTitle, description);
        } catch (JSONException e){
            Log.e(TAG, "Error setting up strings");
        }

        return returnedItem;
    };

    public class YoutubeItem{
        public String publishedAt;
        public String channelTitle;
        public String videoTitle;
        public String description;

        public YoutubeItem(String publishedAt, String channelTitle, String videoTitle, String description){
            this.publishedAt = publishedAt;
            this.channelTitle = channelTitle;
            this.videoTitle = videoTitle;
            this.description = description;
        }

        @Override
        public String toString() {
            return this.videoTitle;
        }
    }

    public void objectSerialize (String apiString){

        try {
            FileOutputStream fos = openFileOutput("recent_api.dat", Context.MODE_PRIVATE);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(apiString);
            oos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    };

    public void openObjectSerialize() {
        try {
            FileInputStream fin = openFileInput("recent_api.dat");
            ObjectInputStream oin = new ObjectInputStream(fin);
            String loadedString = (String) oin.readObject();
            oin.close();
        } catch(Exception e) {
            e.printStackTrace();
        }
    };


}
