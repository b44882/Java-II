package com.fullsail.multiactivity;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

/**
 * Created by administrator on 8/19/14.
 */
public class CreateActivity extends Activity {

    TextView nameEditText;
    TextView classEditText;
    TextView descriptionEditText;
    Button createItemButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_activity);

        nameEditText =(TextView) getFragmentManager().findFragmentById(R.id.create_fragment).getView().findViewById(R.id.nameEditText);
        classEditText =(TextView) getFragmentManager().findFragmentById(R.id.create_fragment).getView().findViewById(R.id.classEditText);
        descriptionEditText =(TextView) getFragmentManager().findFragmentById(R.id.create_fragment).getView().findViewById(R.id.descriptionEditText);
        createItemButton = (Button) getFragmentManager().findFragmentById(R.id.create_fragment).getView().findViewById(R.id.createItemButton);
        createItemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finishActivity();

            }
        });

    }

    private void finishActivity() {
        String nameString = String.valueOf(nameEditText.getText());
        String classString = String.valueOf(classEditText.getText());
        String descString = String.valueOf(descriptionEditText.getText());

        Intent returnIntent = new Intent();
        returnIntent.putExtra("name", nameString);
        returnIntent.putExtra("class", classString);
        returnIntent.putExtra("desc", descString);
        setResult(RESULT_OK, returnIntent);

        finish();
    }





}



