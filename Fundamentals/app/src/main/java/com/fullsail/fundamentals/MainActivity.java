//Brett Gear
//Java2 1408

package com.fullsail.fundamentals;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;



public class MainActivity extends Activity {

    private TextView errorTextView;
    private TextView searchEditText;

    ConnectivityManager connectivityManager;

    Boolean connection;
    Boolean success;
    String resultMessage;


    SharedPreferences defaultPrefs;

    public final String TAG = "MainActivity.TAG";

    ArrayAdapter adapter;
    FragmentManager fragmentManager;
    FragmentTransaction trans;

    @Override
    protected void onCreate(Bundle _savedInstanceState) {
        super.onCreate(_savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        setContentView(R.layout.activity_main);
        errorTextView = (TextView) findViewById(R.id.errorTextView);
        checkConnectivity(connectivityManager);
        Button searchButton = (Button) findViewById(R.id.searchButton);
        Button settingsButton = (Button) findViewById(R.id.settingsButton);

        defaultPrefs = this.getSharedPreferences("PREFS", Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = defaultPrefs.edit();
        edit.putBoolean("network", true);
        edit.apply();

        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragmentManager = getFragmentManager();
                trans = fragmentManager.beginTransaction();
                SettingsFragment settingsFragment = new SettingsFragment();
                trans.replace(R.id.master_fragment, settingsFragment, SettingsFragment.TAG);
                trans.commit();

            }
        });

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {  //Button selected
                Boolean permission = checkNetworkPermission();
                if (permission) {
                    checkConnectivity(connectivityManager);
                    if (connection) {
                        searchEditText = (TextView) findViewById(R.id.searchTextField);
                        String symbol = searchEditText.getText().toString();
                        symbol = symbol.replace(" ", "+");
                        try {
                            String urlString = "https://www.googleapis.com/youtube/v3/search?part=snippet&maxResults=5&q=" + symbol + "&type=video&videoCaption=closedCaption&key=AIzaSyD0m9TrFUsKqIwYCCyoX3ERVlSYTWm-FZk";
                            URL queryURL = new URL(urlString);
                            new GetSearchTask().execute(queryURL);
                            fragmentManager = getFragmentManager();
                        } catch (Exception ignored) {

                        }
                    } else {

                    }
                } else {
                    String storageString = openObjectSerialize();
                    if (storageString != null) {
                        showToast("Cancelling query.  Pulling local data.  Please enable network.");
                        JSONObject storageData = convertStringToJsonObject(storageString);
                        buildMasterFragment(storageData);
                        success = false;
                        resultMessage = "Last saved data pulled: Network pull disabled.";
                        updateResult();
                    } else {
                        showToast("No local data.  Please enable network.");
                    }
                }
            }
        });

    }

    public boolean checkNetworkPermission () {
        defaultPrefs = this.getSharedPreferences("PREFS", Context.MODE_PRIVATE);
        return defaultPrefs.getBoolean("network", true);
    }


    public void checkConnectivity (ConnectivityManager manager){
        NetworkInfo netInfo = manager.getActiveNetworkInfo();
        if(netInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
            success = true;
            resultMessage= "Mobile connected.";
            connection = true;
        } else if(netInfo.getType() == ConnectivityManager.TYPE_WIFI) {
            success = true;
            resultMessage= "Wifi connected.";
            connection = true;
        }

        if (netInfo.isConnected()){
            success = true;
            resultMessage= "Connection Established";
            connection = true;
        } else {
            success = false;
            resultMessage = "Error: No Connection!  Please check network connection.";
            connection = false;
        }
        updateResult();
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
                    success = false;
                    resultMessage = "Could not establish URLConnection";
                    return null;
                }
            }
            objectSerialize(jsonString);
            jsonString = openObjectSerialize();
            return convertStringToJsonObject(jsonString);
        }

        protected void onPostExecute(JSONObject apiData){
            buildMasterFragment(apiData);
        }
    }

    public YoutubeItem buildYoutubeItem (JSONObject object){

        YoutubeItem returnedItem = null;

        try {
            String publishedAt = (object != null) ? object.getString("publishedAt") : null;
            String channelTitle = (object != null) ? object.getString("channelTitle") : null;
            String videoTitle = (object != null) ? object.getString("title") : null;
            String description = (object != null) ? object.getString("description") : null;
            String complete = (object != null) ? object.toString() : null;
            returnedItem = new YoutubeItem(publishedAt, channelTitle, videoTitle, description, complete);
        } catch (JSONException e){
            Log.e(TAG, "Error setting up strings");
        }

        return returnedItem;
    }

    public class YoutubeItem{
        public String publishedAt;
        public String channelTitle;
        public String videoTitle;
        public String description;
        public String complete;

        public YoutubeItem(String publishedAt, String channelTitle, String videoTitle, String description, String complete){
            this.publishedAt = publishedAt;
            this.channelTitle = channelTitle;
            this.videoTitle = videoTitle;
            this.description = description;
            this.complete = complete;
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
    }

    public String openObjectSerialize() {
        String loadedString;
        try {
            FileInputStream fin = openFileInput("recent_api.dat");
            ObjectInputStream oin = new ObjectInputStream(fin);
            loadedString = (String) oin.readObject();
            oin.close();
        } catch(Exception e) {
            e.printStackTrace();
            loadedString = null;
        }
        return loadedString;
    }

    public void updateResult (){
        if (success){
            errorTextView.setTextColor(Color.GREEN);
        } else {
            errorTextView.setTextColor(Color.RED);
        }
        errorTextView.setText(resultMessage);
    }
    public void showToast(String action)
    {
        Context context = this.getApplicationContext();
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, action, duration);
        toast.show();

    }

    public JSONObject convertStringToJsonObject(String jsonString) {
        JSONObject apiData;

        try {
            apiData = new JSONObject(jsonString);
        } catch (Exception e) {
            success = false;
            resultMessage = "Cannot convert API response to JSON";
            apiData = null;
        }
        return apiData;
    }

    public void buildMasterFragment (JSONObject apiData) {
        JSONObject currentObject;
        JSONArray apiDataArray;
        ArrayList myList = null;

        if (apiData != null) {
            try {
                apiDataArray = apiData.getJSONArray("items");
                for (int i = 0; i < apiDataArray.length(); i++) {
                    currentObject = apiDataArray.getJSONObject(i).getJSONObject("snippet");
                    if (myList == null) {
                        myList = new ArrayList();
                    }
                    myList.add(buildYoutubeItem(currentObject));
                    Log.e(TAG, "Item " + i + " added");
                }
                success = true;
                resultMessage = "API Display Complete.";
                trans = fragmentManager.beginTransaction();

                MasterFragment masterFragment = MasterFragment.newInstance(myList);
                trans.replace(R.id.master_fragment, masterFragment, MasterFragment.TAG);
                trans.commit();
            } catch (JSONException e) {
                success = false;
                resultMessage = "Error Setting Up Display";
            }
        }
        updateResult();
    }
}
