package com.fullsail.fundamentals;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Brett Gear on 8/7/14.
 */

public class DetailFragment extends Fragment{

    public static final String TAG = "DetailFragment.TAG";
    public static final String ARG_TEXT = "DetailFragment.ARG_ARRAY";

    public static DetailFragment newInstance() {
        DetailFragment frag = new DetailFragment();
        return frag;

    }

    @Override
    public View onCreateView(LayoutInflater _inflater, ViewGroup _container,
                             Bundle _savedInstanceState) {
        // Create and return view for this fragment.
        View view = _inflater.inflate(R.layout.detail_fragment, _container, false);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle _savedInstanceState) {
        super.onActivityCreated(_savedInstanceState);

        // Get our TextView and set some text to it.
        TextView titleTextView = (TextView)getView().findViewById(R.id.video_title);
        titleTextView.setText("Hello Title!");
    }

}
