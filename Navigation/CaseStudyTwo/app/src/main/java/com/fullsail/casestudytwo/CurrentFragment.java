package com.fullsail.casestudytwo;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by administrator on 8/27/14.
 */
public class CurrentFragment extends Fragment {

    public static final String TAG = "CurrentFragment.TAG";

    public static CurrentFragment newInstance(String temp, String weather) {

        CurrentFragment frag = new CurrentFragment();

        Bundle args = new Bundle();
        args.putString("temp", temp);
        args.putString("weather", weather);
        frag.setArguments(args);
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


        TextView tempEditText = (TextView) getView().findViewById(R.id.tempEditText);
        TextView weatherEditText = (TextView)getView().findViewById(R.id.weatherEditText);

        Bundle args = getArguments();
        if(args != null && args.containsKey("temp") && args.containsKey("weather")) {
            tempEditText.setText(args.getString("temp"));
            weatherEditText.setText(args.getString("weather"));
        }

    }

}
