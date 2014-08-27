package com.fullsail.casestudythree;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by administrator on 8/27/14.
 */
public class FeatureFragment extends Fragment {

    public static FeatureFragment newInstance() {

        FeatureFragment frag = new FeatureFragment();
        return frag;

    }

    @Override
    public View onCreateView(LayoutInflater _inflater, ViewGroup _container,
                             Bundle _savedInstanceState) {
        // Create and return view for this fragment.
        View view = _inflater.inflate(R.layout.fragment_master, _container, false);
        return view;
    }
}
