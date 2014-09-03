//Brett Gear
//Java 1408

package com.fullsail.casestudythree;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by administrator on 8/27/14.
 */
public class SettingsFragment extends PreferenceFragment{

    public static final String TAG = "PreferenceFragment.TAG";
    private static final String PREFS = "package_name.PREFS";
    SharedPreferences defaultPrefs;

    @Override
    public void onCreate(Bundle _savedInstanceState) {
        super.onCreate(_savedInstanceState);
        addPreferencesFromResource(R.xml.settings);


        defaultPrefs = this.getActivity().getSharedPreferences("PREFS", Context.MODE_PRIVATE);
        Log.i(TAG, "Holder");



    }

    @Override
    public void onActivityCreated(Bundle _savedInstanceState) {
        super.onActivityCreated(_savedInstanceState);

        Preference allPref = findPreference("NETWORK_CLICK");
        allPref.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference _pref) {
                SharedPreferences.Editor edit = defaultPrefs.edit();
                edit.putBoolean("network", true);
                edit.apply();
                showToast("Network data get enabled.");
                return true;
            }
        });

        Preference wifiPref = findPreference("LOCAL_CLICK");
        wifiPref.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference _pref) {
                SharedPreferences.Editor edit = defaultPrefs.edit();
                edit.putBoolean("network", false);
                edit.apply();
                showToast("Network data get disabled: will get from local storage.");
                return true;
            }
        });

    }

    public void showToast(String action)
    {
        Context context = this.getActivity().getApplicationContext();
        CharSequence text = action;
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();

    }
}
