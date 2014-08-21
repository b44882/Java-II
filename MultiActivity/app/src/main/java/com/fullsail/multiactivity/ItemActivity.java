package com.fullsail.multiactivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by administrator on 8/19/14.
 */
public class ItemActivity extends Activity {

    TextView nameEditText;
    TextView classEditText;
    TextView descriptionEditText;
    Button createItemButton;

    int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_activity);

        nameEditText =(TextView) getFragmentManager().findFragmentById(R.id.item_fragment).getView().findViewById(R.id.nameEditText);
        classEditText =(TextView) getFragmentManager().findFragmentById(R.id.item_fragment).getView().findViewById(R.id.classEditText);
        descriptionEditText =(TextView) getFragmentManager().findFragmentById(R.id.item_fragment).getView().findViewById(R.id.descriptionEditText);
        createItemButton = (Button) getFragmentManager().findFragmentById(R.id.create_fragment).getView().findViewById(R.id.createItemButton);
        createItemButton.setText("Delete");

        createItemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finishActivity();
            }
        });

        nameEditText.setFocusable(false);
        classEditText.setFocusable(false);
        descriptionEditText.setFocusable(false);


        Bundle result = getIntent().getExtras();
        String nameString = result.getString("name");
        String classString = result.getString("class");
        String descString = result.getString("desc");
        position = result.getInt("position");

        nameEditText.setText(nameString);
        classEditText.setText(classString);
        descriptionEditText.setText(descString);

    }

    private void finishActivity() {
        String nameString = String.valueOf(nameEditText.getText());
        String classString = String.valueOf(classEditText.getText());
        String descString = String.valueOf(descriptionEditText.getText());

        Intent returnIntent = new Intent();
        returnIntent.putExtra("name", nameString);
        returnIntent.putExtra("class", classString);
        returnIntent.putExtra("desc", descString);
        returnIntent.putExtra("reason", "delete");
        returnIntent.putExtra("position", position);
        setResult(RESULT_OK, returnIntent);

        finish();
    }

}
