package com.fullsail.casestudythree;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by administrator on 8/27/14.
 */
public class ImageFragment extends Fragment {
    public static ImageFragment newInstance() {

        ImageFragment frag = new ImageFragment();
        return frag;

    }

    @Override
    public View onCreateView(LayoutInflater _inflater, ViewGroup _container,
                             Bundle _savedInstanceState) {
        // Create and return view for this fragment.
        View view = _inflater.inflate(R.layout.fragment_image, _container, false);
        return view;
    }
}
