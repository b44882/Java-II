package com.fullsail.multiactivity;

import android.app.Activity;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_activity);

        nameEditText =(TextView) getFragmentManager().findFragmentById(R.id.item_fragment).getView().findViewById(R.id.nameEditText);
        classEditText =(TextView) getFragmentManager().findFragmentById(R.id.item_fragment).getView().findViewById(R.id.classEditText);
        descriptionEditText =(TextView) getFragmentManager().findFragmentById(R.id.item_fragment).getView().findViewById(R.id.descriptionEditText);

        nameEditText.setFocusable(false);
        classEditText.setFocusable(false);
        descriptionEditText.setFocusable(false);


        Bundle result = getIntent().getExtras();
        String nameString = result.getString("name");
        String classString = result.getString("class");
        String descString = result.getString("desc");

        nameEditText.setText(nameString);
        classEditText.setText(classString);
        descriptionEditText.setText(descString);

    }

}
