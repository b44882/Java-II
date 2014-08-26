package com.fullsail.contextapp;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.Serializable;

/**
 * Created by administrator on 8/25/14.
 */
public class CreateFragment extends Fragment implements Serializable {

    public static final String TAG = "CreateFragment.TAG";
    public static final String ARG_ITEM_ID = "item_id";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.create_fragment, container, false);

        return view;
    }

}
