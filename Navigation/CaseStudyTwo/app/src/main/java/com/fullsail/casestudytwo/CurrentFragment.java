package com.fullsail.casestudytwo;

import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.apache.commons.io.IOUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;
import java.net.URLConnection;

/**
 * Created by administrator on 8/27/14.
 */
public class CurrentFragment extends Fragment {

    public static final String TAG = "CurrentFragment.TAG";
    private TextView tempEditText;
    private TextView weatherEditText;

    public static CurrentFragment newInstance() {

        CurrentFragment frag = new CurrentFragment();
        return frag;

    }

    @Override
    public View onCreateView(LayoutInflater _inflater, ViewGroup _container,
                             Bundle _savedInstanceState) {
        // Create and return view for this fragment.
        View view = _inflater.inflate(R.layout.fragment_current, _container, false);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle _savedInstanceState) {
        super.onActivityCreated(_savedInstanceState);
        tempEditText = (TextView) getView().findViewById(R.id.tempEditText);
        weatherEditText = (TextView)getView().findViewById(R.id.weatherEditText);
        try{
            String urlString = "http://api.wunderground.com/api/78a366bca828eaa0/conditions/q/FL/Orlando.json";
            URL queryURL = new URL(urlString);
            new GetCurrentCast().execute(queryURL);

        } catch (Exception e) {

        }
    }

    private class GetCurrentCast extends AsyncTask<URL, Integer, JSONObject> {
        protected JSONObject doInBackground(URL... urls) {
            String jsonString = "";
            for(URL queryURL : urls){
                try{
                    URLConnection conn = queryURL.openConnection();
                    jsonString = IOUtils.toString(conn.getInputStream());
                } catch (Exception e){
                    return null;
                }
            }
            JSONObject apiData;
            try{
                apiData = new JSONObject(jsonString);
            } catch (Exception e){
                apiData = null;
            }
            return apiData;
        }
        protected void onPostExecute(JSONObject apiData){

            JSONObject currentObject = null;
            try {
                currentObject = (apiData != null) ? apiData.getJSONObject("current_observation") : null;
            } catch (JSONException e) {
                e.printStackTrace();
            }
            if (currentObject != null){

                try {
                    String temp = String.valueOf((currentObject != null) ? currentObject.getInt("temp_f") : null);
                    String weather = (currentObject != null) ? currentObject.getString("weather") : null;
                    tempEditText.setText(temp);
                    weatherEditText.setText(weather);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }
    }

}
