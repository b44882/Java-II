package com.fullsail.contextapp;

import android.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by administrator on 8/25/14.
 */
public class MasterFragment extends ListFragment {

    public static final String TAG = "MasterFragment.TAG";
    private static final String ARG_TEXT = "MasterFragment.ARG_ARRAY";

    public static MasterFragment newInstance(ArrayList listArray) {
        MasterFragment frag = new MasterFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(ARG_TEXT, listArray);
        frag.setArguments(args);

        return frag;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onActivityCreated(savedInstanceState);
        Bundle args = getArguments();
        if(args != null && args.containsKey(ARG_TEXT)) {
            ArrayList adapterList = args.getParcelableArrayList(ARG_TEXT);
            setListArray(adapterList);
        }
    }

    private void setListArray(ArrayList listArray){
        Bundle args = getArguments();
        if(args != null && args.containsKey(ARG_TEXT)) {
            args.putParcelableArrayList("master", listArray);
        }
        ArrayAdapter adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, listArray);
        setListAdapter(adapter);

        OnItemLongClickListener listener = new OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int position, long id) {
                Toast.makeText( getActivity().getBaseContext()  , "Long Clicked " + position , Toast.LENGTH_SHORT).show();
                return false;
            }
        };

        getListView().setOnItemLongClickListener(listener);
    }
}
