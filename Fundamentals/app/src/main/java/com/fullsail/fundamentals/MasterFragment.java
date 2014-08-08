package com.fullsail.fundamentals;

import android.app.ListFragment;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/**
 * Created by Brett Gear on 8/7/14.
 */
public class MasterFragment extends ListFragment {

    public static final String TAG = "MasterFragment.TAG";

    public static MasterFragment newInstance() {
        MasterFragment frag = new MasterFragment();
        return frag;
    }

    @Override
    public void onActivityCreated(Bundle _savedInstanceState) {
        super.onActivityCreated(_savedInstanceState);

        //String[] presidents = getResources().getStringArray(R.array.presidents);

        //ArrayAdapter adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, presidents);
        //setListAdapter(adapter);
    }

    @Override
    public void onListItemClick(ListView _l, View _v, int _position, long _id) {
        String title = (String) _l.getItemAtPosition(_position);

    }
}
