package com.fullsail.casestudyone;

import android.app.ListFragment;
import android.os.Bundle;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

/**
 * Created by administrator on 8/26/14.
 */
public class MasterFragment extends ListFragment {
    ArrayList adapterList;
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
    public void onActivityCreated(Bundle _savedInstanceState) {
        super.onActivityCreated(_savedInstanceState);
        Bundle args = getArguments();
        if(args != null && args.containsKey(ARG_TEXT)) {
            adapterList = args.getParcelableArrayList(ARG_TEXT);
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
    }
}
