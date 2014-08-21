package com.fullsail.multiactivity;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ListFragment;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by administrator on 8/19/14.
 */
public class MasterFragment extends ListFragment {

    ArrayList adapterList;
    public static final String TAG = "MasterFragment.TAG";
    private static final String ARG_TEXT = "MasterFragment.ARG_ARRAY";

    private Callbacks mCallbacks;

    public interface Callbacks {

        public void onItemSelected(MasterActivity.PassableObject object, int position);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mCallbacks = (Callbacks) activity;
        } catch (ClassCastException ex) {
            Log.e(TAG, "Casting the activity as a Callbacks listener failed"
                    + ex);
            mCallbacks = null;
        }
    }

    public static MasterFragment newInstance(ArrayList listArray) {
        MasterFragment frag = new MasterFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(ARG_TEXT, listArray);
        frag.setArguments(args);

        return frag;
    }

    private void setListArray(ArrayList listArray){
        Bundle args = getArguments();
        if(args != null && args.containsKey(ARG_TEXT)) {
            args.putParcelableArrayList("master", listArray);
        }
        ArrayAdapter adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, listArray);
        setListAdapter(adapter);
    }

    @Override
    public void onActivityCreated(Bundle _savedInstanceState) {
        super.onActivityCreated(_savedInstanceState);
        Bundle args = getArguments();
        if(args != null && args.containsKey(ARG_TEXT)) {
            adapterList = args.getParcelableArrayList(ARG_TEXT);
            setListArray(adapterList);
        }
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        MasterActivity.PassableObject object = (MasterActivity.PassableObject) this.getListAdapter().getItem(position);

        if (mCallbacks != null) {
            mCallbacks.onItemSelected(object, position);
        }


        Log.i(TAG, "Breakpoint");

    }

}

