package com.example.hendriebeats.remindme;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class UpdateTaskActivity extends AppCompatActivity {

    EditText title, description, dateTxt, timeTxt;
    TextView PlaceTitleTxt, PlaceAddressTxt;
    Button changeLocationBtn, updateBtn;
    String currentTaskId, placeTitle, placeAddress, placeLatitude, placeLongitude, placeLocale, isUpdated, currentUserId;
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
            currentUserId = extras.getString("currentUserId");

            //Get Location Elements
            placeTitle = extras.getString("placeTitle");
            placeAddress = extras.getString("placeAddress");
            placeLatitude = extras.getString("placeLatitude");
            placeLongitude = extras.getString("placeLongitude");
            placeLocale = extras.getString("placeLocale");
            isUpdated = extras.getString("isUpdated");
        }

        title = (EditText) findViewById(R.id.titleTxt);
        description = (EditText) findViewById(R.id.descriptionTxt);
        dateTxt = (EditText) findViewById(R.id.dateTxt);
        timeTxt = (EditText) findViewById(R.id.timeTxt);
        changeLocationBtn = (Button) findViewById(R.id.changeLocationBtn);
        updateBtn = (Button) findViewById(R.id.updateBtn);
        PlaceTitleTxt = (TextView) findViewById(R.id.placeTitleTxt);
        PlaceAddressTxt = (TextView) findViewById(R.id.placeAddressTxt);


        //Set all the displayed fields equal to the current Task's values
        title.setText(db.getTaskById(Integer.parseInt(currentTaskId)).getTitle());
        description.setText(db.getTaskById(Integer.parseInt(currentTaskId)).getDescription());
        dateTxt.setText(db.getTaskById(Integer.parseInt(currentTaskId)).getDate());
        timeTxt.setText(db.getTaskById(Integer.parseInt(currentTaskId)).getTime());

        PlaceTitleTxt.setText(db.getPlaceById(db.getTaskById(Integer.parseInt(currentTaskId)).getPlaceId()).getTitle());
        PlaceAddressTxt.setText(db.getPlaceById(db.getTaskById(Integer.parseInt(currentTaskId)).getPlaceId()).getAddress());

        //Update Location field when updated via Place Picker
        try{
            if(isUpdated.equals("yes")){
                PlaceTitleTxt.setText(placeTitle);
                PlaceAddressTxt.setText(placeAddress);
            }
        } catch(Exception e) {}



        //On Click Listener
        changeLocationBtn.setOnClickListener(
                new View.OnClickListener(){public void onClick(View view) {
                    Intent i = new Intent(UpdateTaskActivity.this, PlacePickerUpdateTaskActivity.class);
                    i.putExtra("currentTaskId", currentTaskId);
                    i.putExtra("currentUserId", currentUserId);
                    startActivity(i);
                }});

        //On Click Listener
        updateBtn.setOnClickListener(
                new View.OnClickListener(){public void onClick(View view) {
                    //create task object
                    Task updatedTask = new Task();
                    Place updatedPlace = db.getPlaceById(db.getTaskById(Integer.parseInt(currentTaskId)).getId());
                    updatedTask.setId(Integer.parseInt(currentTaskId));
                    updatedTask.setOwnerId(Integer.parseInt(currentUserId));
                    updatedTask.setDescription(description.getText().toString());
                    updatedTask.setDate(dateTxt.getText().toString());
                    updatedTask.setTime(timeTxt.getText().toString());
                    updatedTask.setTitle(title.getText().toString());

                    if(placeLatitude != ""){
                        updatedPlace.setTitle(placeTitle);
                        updatedPlace.setLatitude(placeLatitude);
                        updatedPlace.setLongitude(placeLongitude);
                        updatedPlace.setAddress(placeAddress);
                        updatedPlace.setLocale(placeLocale);
                    }

                    db.updateTask(updatedTask);
                    db.updatePlace(updatedPlace);

                    Intent i = new Intent(UpdateTaskActivity.this, FullTaskActivity.class);
                    i.putExtra("currentTaskId", currentTaskId);
                    i.putExtra("currentUserId", currentUserId);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);
                }});
    }
}
