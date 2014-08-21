//Brett Gear
//Java 1408 Week 3

package com.fullsail.multiactivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.apache.http.protocol.HTTP;

/**
 * Created by administrator on 8/19/14.
 */
public class ItemActivity extends Activity {

    TextView nameEditText;
    TextView classEditText;
    TextView descriptionEditText;
    Button createItemButton;
    Button gmailButton;

    int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_activity);

        nameEditText =(TextView) getFragmentManager().findFragmentById(R.id.item_fragment).getView().findViewById(R.id.nameEditText);
        classEditText =(TextView) getFragmentManager().findFragmentById(R.id.item_fragment).getView().findViewById(R.id.classEditText);
        descriptionEditText =(TextView) getFragmentManager().findFragmentById(R.id.item_fragment).getView().findViewById(R.id.descriptionEditText);
        createItemButton = (Button) getFragmentManager().findFragmentById(R.id.item_fragment).getView().findViewById(R.id.createItemButton);
        createItemButton.setText("Delete");
        gmailButton = (Button) getFragmentManager().findFragmentById(R.id.item_fragment).getView().findViewById(R.id.gmail_button);


        createItemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finishActivity();
            }
        });

        gmailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nameString = String.valueOf(nameEditText.getText());
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, nameString);
                sendIntent.setType(HTTP.PLAIN_TEXT_TYPE); // "text/plain" MIME type

                // Verify that the intent will resolve to an activity
                if (sendIntent.resolveActivity(getPackageManager()) != null) {
                    startActivity(sendIntent);
                }

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
