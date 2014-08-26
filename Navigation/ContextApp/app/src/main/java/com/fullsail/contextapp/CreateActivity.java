//Brett Gear
//Java 1408 Week 3

package com.fullsail.contextapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by administrator on 8/25/14.
 */
public class CreateActivity extends Activity {

    TextView nameEditText;
    TextView classEditText;
    TextView descriptionEditText;
    Button createItemButton;
    Button gmailButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_activity);

        nameEditText =(TextView) getFragmentManager().findFragmentById(R.id.create_fragment).getView().findViewById(R.id.nameEditText);
        classEditText =(TextView) getFragmentManager().findFragmentById(R.id.create_fragment).getView().findViewById(R.id.classEditText);
        descriptionEditText =(TextView) getFragmentManager().findFragmentById(R.id.create_fragment).getView().findViewById(R.id.descriptionEditText);
        createItemButton = (Button) getFragmentManager().findFragmentById(R.id.create_fragment).getView().findViewById(R.id.createItemButton);
        gmailButton = (Button) getFragmentManager().findFragmentById(R.id.create_fragment).getView().findViewById(R.id.gmail_button);
        gmailButton.setVisibility(View.GONE);

        createItemButton.setText("Create");
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
        returnIntent.putExtra("reason", "create");
        setResult(RESULT_OK, returnIntent);

        finish();
    }





}
