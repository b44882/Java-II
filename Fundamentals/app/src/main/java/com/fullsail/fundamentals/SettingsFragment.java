package com.fullsail.fundamentals;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by administrator on 8/10/14.
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

        Preference allPref = findPreference("ALL_CLICK");
        allPref.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference _pref) {
                SharedPreferences.Editor edit = defaultPrefs.edit();
                edit.putBoolean("wifiData", true);
                edit.putBoolean("dataData", true);
                edit.apply();
                showToast("Wifi On, Data On");
                return true;
            }
        });

        Preference wifiPref = findPreference("WIFI_CLICK");
        wifiPref.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference _pref) {
                SharedPreferences.Editor edit = defaultPrefs.edit();
                edit.putBoolean("wifiData", true);
                edit.putBoolean("dataData", false);
                edit.apply();
                showToast("Wifi On, Data Off");
                return true;
            }
        });

        Preference nonePref = findPreference("NONE_CLICK");
        nonePref.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference _pref) {
                SharedPreferences.Editor edit = defaultPrefs.edit();
                edit.putBoolean("wifiData", false);
                edit.putBoolean("dataData", false);
                edit.apply();
                showToast("Wifi Off, Data Off");
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
