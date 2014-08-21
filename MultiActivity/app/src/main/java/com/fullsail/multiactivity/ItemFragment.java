package com.fullsail.multiactivity;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by administrator on 8/19/14.
 */
public class ItemFragment extends Fragment {
    public static final String TAG = "ItemFragment.TAG";
    public static final String ARG_ITEM_ID = "item_id";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.item_fragment, container, false);

        return view;
    }
}
