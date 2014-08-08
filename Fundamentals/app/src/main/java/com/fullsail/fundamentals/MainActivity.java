package com.fullsail.fundamentals;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle _savedInstanceState) {
        super.onCreate(_savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.activity_main);

        FragmentManager mgr = getFragmentManager();
        FragmentTransaction trans = mgr.beginTransaction();

        DetailFragment frag = DetailFragment.newInstance();
        trans.replace(R.id.detail_container, frag, DetailFragment.TAG);

        trans.commit();
    }


}
