package com.fullsail.multiactivity;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;


public class MasterActivity extends Activity implements MasterFragment.Callbacks{


    public static final int NEXT_REQUESTCODE = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.master_activity);


        Button createButton = (Button) findViewById(R.id.createButton);

        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent nextActivity = new Intent(MasterActivity.this, CreateActivity.class);
                MasterActivity.this.startActivityForResult(nextActivity, NEXT_REQUESTCODE);
            }
        });


    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {   //When activity returns from Create
        if (resultCode == RESULT_OK && requestCode == NEXT_REQUESTCODE) {

            ArrayList myList;
            Bundle result = data.getExtras();

            String nameString = result.getString("name");
            String classString = result.getString("class");
            String descString = result.getString("desc");

            PassableObject object = new PassableObject(nameString,classString,descString);



            myList = openObjectSerialize();
            if (myList == null)
            {
                myList = new ArrayList();
            }
            myList.add(object);
            objectSerialize(myList);

            FragmentManager fragmentManager =  getFragmentManager();
            FragmentTransaction trans = fragmentManager.beginTransaction();
            MasterFragment masterFragment = MasterFragment.newInstance(myList);
            trans.replace(R.id.master_fragment_container, masterFragment, MasterFragment.TAG);
            trans.commit();

        }
    }

    @Override
    public void onItemSelected(PassableObject object) {

        String nameString = String.valueOf(object.nameChar);
        String classString = String.valueOf(object.classChar);
        String descString = String.valueOf(object.descChar);

        Intent nextActivity = new Intent(MasterActivity.this, ItemActivity.class);
        nextActivity.putExtra("name", nameString);
        nextActivity.putExtra("class", classString);
        nextActivity.putExtra("desc", descString);
        MasterActivity.this.startActivity(nextActivity);

    }



    public class PassableObject implements Parcelable {

        public String nameChar;
        public String classChar;
        public String descChar;

        public PassableObject(String nameChar, String classChar, String descChar){
            this.nameChar = nameChar;
            this.classChar = classChar;
            this.descChar = descChar;
        }

        @Override
        public String toString() {
            return this.nameChar;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel parcel, int i) {

        }
    }

    public void objectSerialize (ArrayList list){

        try {
            FileOutputStream fos = openFileOutput("list.dat", Context.MODE_PRIVATE);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(list);
            oos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ArrayList openObjectSerialize() {
        ArrayList list;
        try {
            FileInputStream fin = openFileInput("list.dat");
            ObjectInputStream oin = new ObjectInputStream(fin);
            list = (ArrayList) oin.readObject();
            oin.close();
        } catch(Exception e) {
            e.printStackTrace();
            list = null;
        }
        return list;
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_actvitiy, menu);
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
}
