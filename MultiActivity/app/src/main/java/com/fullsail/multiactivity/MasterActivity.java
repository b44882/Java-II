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
import java.io.Serializable;
import java.util.ArrayList;


public class MasterActivity extends Activity implements MasterFragment.Callbacks, Serializable{


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

        ArrayList<PassableObject> passList;
        ArrayList<SerializableObject> serList;

        serList = openObjectSerialize();

        if (serList != null){
            passList = convertSerToParse(serList);

            FragmentManager fragmentManager =  getFragmentManager();
            FragmentTransaction trans = fragmentManager.beginTransaction();
            MasterFragment masterFragment = MasterFragment.newInstance(passList);
            trans.replace(R.id.master_fragment_container, masterFragment, MasterFragment.TAG);
            trans.commit();
        }

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {   //When activity returns from Create
        if (resultCode == RESULT_OK && requestCode == NEXT_REQUESTCODE) {

            ArrayList<PassableObject> passList;
            ArrayList<SerializableObject> serList;
            Bundle result = data.getExtras();
            serList = openObjectSerialize();
            if (serList != null){
                passList = convertSerToParse(serList);
            } else {
                passList = new ArrayList<PassableObject>();
                serList = new ArrayList<SerializableObject>();
            }

            if (result.getString("desc") == "delete"){

            } else {
                String nameString = result.getString("name");
                String classString = result.getString("class");
                String descString = result.getString("desc");

                //Serialize
                SerializableObject serObject = new SerializableObject(nameString,classString,descString);
                serList.add(serObject);
                objectSerialize(serList);

                //Parse
                PassableObject passObject = new PassableObject(nameString,classString,descString);
                passList.add(passObject);
            }

            FragmentManager fragmentManager =  getFragmentManager();
            FragmentTransaction trans = fragmentManager.beginTransaction();
            MasterFragment masterFragment = MasterFragment.newInstance(passList);
            trans.replace(R.id.master_fragment_container, masterFragment, MasterFragment.TAG);
            trans.commit();
        }
    }


    public ArrayList<PassableObject> convertSerToParse (ArrayList<SerializableObject> list){
        ArrayList parseList = new ArrayList();
        if (list != null){
            SerializableObject currentSerObject;
            PassableObject convertedParObject;

            for (int i = 0; i < list.size(); i++){

                currentSerObject = list.get(i);
                convertedParObject = new PassableObject(currentSerObject.getNameString(), currentSerObject.getClassString(), currentSerObject.getDescString());
                if (convertedParObject != null){
                    parseList.add(convertedParObject);
                }
            }
        } else {
            parseList = null;
        }
        return parseList;
    }

    @Override
    public void onItemSelected(PassableObject object, int position) {

        String nameString = String.valueOf(object.nameChar);
        String classString = String.valueOf(object.classChar);
        String descString = String.valueOf(object.descChar);

        Intent nextActivity = new Intent(MasterActivity.this, ItemActivity.class);
        nextActivity.putExtra("name", nameString);
        nextActivity.putExtra("class", classString);
        nextActivity.putExtra("desc", descString);
        nextActivity.putExtra("position", position);
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

    public class SerializableObject implements Serializable {

        public String nameString;
        public String classString;
        public String descString;
        //private static final long serialVersionUID = 528195923;

        public SerializableObject(String nameString, String classString, String descString){
            this.nameString = nameString;
            this.classString = classString;
            this.descString = descString;
        }
        public String getNameString(){
            return this.nameString;
        }
        public String getClassString(){
            return this.classString;
        }
        public String getDescString(){
            return this.descString;
        }

    }

    public void objectSerialize (ArrayList<SerializableObject> list){

        try {
            FileOutputStream fos = openFileOutput("save.bin", Context.MODE_PRIVATE);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(list);
            oos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    public ArrayList openObjectSerialize() {
        ArrayList<SerializableObject> list;
        try {
            FileInputStream fin = openFileInput("save.bin");
            ObjectInputStream oin = new ObjectInputStream(fin);
            list = (ArrayList<SerializableObject>) oin.readObject();
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
