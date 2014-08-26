package com.fullsail.contextapp;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;


public class MasterActivity extends Activity implements Serializable, MasterFragment.Callbacks{

    public static final int NEXT_REQUESTCODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.master_activity);


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
            if (serList != null) {
                passList = convertSerToParse(serList);
            } else {
                passList = new ArrayList<PassableObject>();
                serList = new ArrayList<SerializableObject>();
            }

            String nameString = result.getString("name");
            String classString = result.getString("class");
            String descString = result.getString("desc");

            //Serialize
            SerializableObject serObject = new SerializableObject(nameString, classString, descString);
            serList.add(serObject);
            objectSerialize(serList);

            //Parse
            PassableObject passObject = new PassableObject(nameString, classString, descString);
            passList.add(passObject);

            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction trans = fragmentManager.beginTransaction();
            MasterFragment masterFragment = MasterFragment.newInstance(passList);
            trans.replace(R.id.master_fragment_container, masterFragment, MasterFragment.TAG);
            trans.commit();
        }
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
    public void passPosition(int position) {
        ArrayList<PassableObject> passList;
        ArrayList<SerializableObject> serList;
        serList = openObjectSerialize();
        serList.remove(position);
        objectSerialize(serList);
        passList = convertSerToParse(serList);
        FragmentManager fragmentManager =  getFragmentManager();
        FragmentTransaction trans = fragmentManager.beginTransaction();
        MasterFragment masterFragment = MasterFragment.newInstance(passList);
        trans.replace(R.id.master_fragment_container, masterFragment, MasterFragment.TAG);
        trans.commit();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.mastermenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.action_add) {
            Intent nextActivity = new Intent(MasterActivity.this, CreateActivity.class);
            MasterActivity.this.startActivityForResult(nextActivity, NEXT_REQUESTCODE);
            return true;
        }
        if (id == R.id.action_reset) {
            ArrayList<PassableObject> passList;
            ArrayList<SerializableObject> serList = new ArrayList<SerializableObject>();
            objectSerialize(serList);
            passList = convertSerToParse(serList);
            FragmentManager fragmentManager =  getFragmentManager();
            FragmentTransaction trans = fragmentManager.beginTransaction();
            MasterFragment masterFragment = MasterFragment.newInstance(passList);
            trans.replace(R.id.master_fragment_container, masterFragment, MasterFragment.TAG);
            trans.commit();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
