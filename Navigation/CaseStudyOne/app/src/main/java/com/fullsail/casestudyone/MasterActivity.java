package com.fullsail.casestudyone;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;


public class MasterActivity extends Activity implements ActionBar.OnNavigationListener {

    /**
     * The serialization (saved instance state) Bundle key representing the
     * current dropdown position.
     */
    private static final String STATE_SELECTED_NAVIGATION_ITEM = "selected_navigation_item";
    private ArrayList <String> blue = new ArrayList<String>();
    private ArrayList <String> green = new ArrayList<String>();
    private ArrayList <String> silver = new ArrayList<String>();
    private ArrayList <String> purple = new ArrayList<String>();
    private ArrayList <String> red = new ArrayList<String>();
    private ArrayList <String> orange = new ArrayList<String>();
    private ArrayList <String> passList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_master);

        blue.add("Brian Hofrichter");
        green.add("Will Smith");
        silver.add("Maddie Coker Heinen");
        silver.add("John Torres");
        silver.add("Virginia Bland");
        purple.add("Ariana Siha");
        purple.add("Janet Chiarolanza");
        purple.add("Laura Ashley");
        purple.add("Nola Bennett");
        purple.add("Ron Shockley");
        purple.add("Phoebe Abdelmessih");
        red.add("Audrey Gibson");
        red.add("Shelley Long");
        red.add("Alanna Cervenak");
        red.add("Jacqueline Storm");
        orange.add("Noah Daniel Potter");
        orange.add("Jeremy Marks");


        // Set up the action bar to show a dropdown list.
        final ActionBar actionBar = getActionBar();
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);

        // Set up the dropdown list navigation in the action bar.
        actionBar.setListNavigationCallbacks(
                // Specify a SpinnerAdapter to populate the dropdown list.
                new ArrayAdapter<String>(
                        actionBar.getThemedContext(),
                        android.R.layout.simple_list_item_1,
                        android.R.id.text1,
                        new String[] {
                                getString(R.string.title_section1),
                                getString(R.string.title_section2),
                                getString(R.string.title_section3),
                                getString(R.string.title_section4),
                                getString(R.string.title_section5),
                                getString(R.string.title_section6),

                        }),
                this);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        // Restore the previously serialized current dropdown position.
        if (savedInstanceState.containsKey(STATE_SELECTED_NAVIGATION_ITEM)) {
            getActionBar().setSelectedNavigationItem(
                    savedInstanceState.getInt(STATE_SELECTED_NAVIGATION_ITEM));
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        // Serialize the current dropdown position.
        outState.putInt(STATE_SELECTED_NAVIGATION_ITEM,
                getActionBar().getSelectedNavigationIndex());
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.master, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(int position, long id) {

        View view = findViewById(R.id.master_fragment_container);
        View root = view.getRootView();

        if (id == 0){
            passList = blue;
            getActionBar().setIcon(R.drawable.ic_blue);
        } else if (id == 1) {
            passList = green;
            getActionBar().setIcon(R.drawable.ic_green);
        } else if (id == 2) {
            passList = silver;
            getActionBar().setIcon(R.drawable.ic_silver);
        } else if (id == 3) {
            passList = purple;
            getActionBar().setIcon(R.drawable.ic_purple);
        } else if (id == 4) {
            passList = red;
            getActionBar().setIcon(R.drawable.ic_red);
        } else if (id == 5) {
            passList = orange;
            getActionBar().setIcon(R.drawable.ic_orange);
        }

        FragmentManager fragmentManager =  getFragmentManager();
        FragmentTransaction trans = fragmentManager.beginTransaction();
        MasterFragment masterFragment = MasterFragment.newInstance(passList);
        trans.replace(R.id.master_fragment_container, masterFragment, MasterFragment.TAG);
        trans.commit();
        return true;
    }



}
