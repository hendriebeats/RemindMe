package com.example.hendriebeats.remindme;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;

public class UpdateTaskActivity extends AppCompatActivity {

    EditText title, description, dateTime;
    TextView locationTxt;
    Button changeLocationBtn;
    String currentTaskId, placeName;
    int currentUserId;
    public DatabaseHandler db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_task);

        db = new DatabaseHandler(this);

        //Get Current Task ID from Previous Activity
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            currentTaskId = extras.getString("currentTaskId");
            placeName = extras.getString("placeName");
        }

        currentUserId = db.getTaskById(Integer.parseInt(currentTaskId)).getOwnerId();

        title = (EditText) findViewById(R.id.titleTxt);
        description = (EditText) findViewById(R.id.descriptionTxt);
        dateTime = (EditText) findViewById(R.id.dateTimeTxt);
        changeLocationBtn = (Button) findViewById(R.id.changeLocationBtn);
        locationTxt = (TextView) findViewById(R.id.locationTxt);

        //Set all the displayed fields equal to the current Task's values
        title.setText(db.getTaskById(Integer.parseInt(currentTaskId)).getTitle());
        description.setText(db.getTaskById(Integer.parseInt(currentTaskId)).getDescription());
        dateTime.setText(db.getTaskById(Integer.parseInt(currentTaskId)).getDateTime());
        locationTxt.setText(db.getTaskById(Integer.parseInt(currentTaskId)).getLocation());

        //On Click Listener
        changeLocationBtn.setOnClickListener(
                new View.OnClickListener(){public void onClick(View view) {
                    Intent i = new Intent(UpdateTaskActivity.this, PlacePickerActivity.class);
                    i.putExtra("activityFrom", "UpdateTaskActivity");
                    startActivity(i);
                }});

        //Update Location field when updated via Place Picker
        try{
            if(placeName.length()>2);

            //Populate Location Text
            locationTxt.setText(placeName);
        } catch(Exception e) {}
    }
}
