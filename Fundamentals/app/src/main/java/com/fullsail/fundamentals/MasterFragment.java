//Brett Gear
//Java2 1408

package com.fullsail.fundamentals;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ListFragment;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Brett Gear on 8/7/14.
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
    public void onListItemClick(ListView _l, View _v, int _position, long _id) {


        MainActivity.YoutubeItem currentObject = (MainActivity.YoutubeItem) adapterList.get(_position);
        String passableString = currentObject.complete;

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction trans = fragmentManager.beginTransaction();
        DetailFragment detailFragment = DetailFragment.newInstance(passableString);
        trans.replace(R.id.detail_fragment, detailFragment, DetailFragment.TAG);
        trans.commit();

        //DetailFragment detailFragment = DetailFragment.newInstance();
       // trans.replace(R.id.detail_fragment, detailFragment, MasterFragment.TAG);
        //trans.commit();


        Log.i(TAG, "Breakpoint");

    }

}
