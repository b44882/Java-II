package com.fullsail.fundamentals;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;

import java.io.Serializable;

/**
 * Created by administrator on 8/13/14.
 */
public class DetailActivity extends Activity implements Serializable {


    /*protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        getActionBar().setDisplayHomeAsUpEnabled(true);



        if (savedInstanceState == null) {
            // Create the detail fragment and add it to the activity
            // using a fragment transaction.
            Bundle arguments = new Bundle();
            arguments.putString(DetailFragment.ARG_TEXT,
                    getIntent().getStringExtra(DetailFragment.ARG_TEXT));
            DetailFragment fragment = new DetailFragment();
            fragment.setArguments(arguments);
            getFragmentManager().beginTransaction()
                    .add(R.id.detail_fragment, fragment)
                    .commit();
        }


    }*/
}
