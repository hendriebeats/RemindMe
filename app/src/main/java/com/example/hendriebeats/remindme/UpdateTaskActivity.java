package com.example.hendriebeats.remindme;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;

public class UpdateTaskActivity extends AppCompatActivity {

    EditText title, description, dateTime, ownerTxt;
    Button changeLocationBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_task);

        title = (EditText) findViewById(R.id.titleTxt);
        description = (EditText) findViewById(R.id.descriptionTxt);
        dateTime = (EditText) findViewById(R.id.dateTimeTxt);
        ownerTxt = (EditText) findViewById(R.id.ownerTxt);
        changeLocationBtn = (Button) findViewById(R.id.changeLocationBtn);

        //On Click Listener
        changeLocationBtn.setOnClickListener(
                new View.OnClickListener(){public void onClick(View view) {
                    Intent i = new Intent(UpdateTaskActivity.this, PlacePickerActivity.class);
                    i.putExtra("activityFrom", "UpdateTaskActivity");
                    startActivity(i);
                }});
    }
}
