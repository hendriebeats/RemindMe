package com.example.hendriebeats.remindme;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class UpdateTaskActivity extends AppCompatActivity {

    EditText title, description, dateTxt, timeTxt;
    TextView locationTxt;
    Button changeLocationBtn, updateBtn;
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
        dateTxt = (EditText) findViewById(R.id.dateTxt);
        timeTxt = (EditText) findViewById(R.id.timeTxt);
        changeLocationBtn = (Button) findViewById(R.id.changeLocationBtn);
        updateBtn = (Button) findViewById(R.id.updateBtn);
        locationTxt = (TextView) findViewById(R.id.locationTxt);

        //Set all the displayed fields equal to the current Task's values
        title.setText(db.getTaskById(Integer.parseInt(currentTaskId)).getTitle());
        description.setText(db.getTaskById(Integer.parseInt(currentTaskId)).getDescription());
        dateTxt.setText(db.getTaskById(Integer.parseInt(currentTaskId)).getDate());
        timeTxt.setText(db.getTaskById(Integer.parseInt(currentTaskId)).getTime());
        locationTxt.setText(db.getTaskById(Integer.parseInt(currentTaskId)).getLocation());

        //On Click Listener
        changeLocationBtn.setOnClickListener(
                new View.OnClickListener(){public void onClick(View view) {
                    Intent i = new Intent(UpdateTaskActivity.this, PlacePickerUpdateTaskActivity.class);
                    i.putExtra("currentTaskId", currentTaskId);
                    startActivity(i);
                }});

        //On Click Listener
        updateBtn.setOnClickListener(
                new View.OnClickListener(){public void onClick(View view) {
                    //create task object
                    Task updatedTask = new Task();
                    updatedTask.setId(Integer.parseInt(currentTaskId));
                    updatedTask.setOwnerId(currentUserId);
                    updatedTask.setLocation(locationTxt.getText().toString());
                    updatedTask.setDescription(description.getText().toString());
                    updatedTask.setDate(dateTxt.getText().toString());
                    updatedTask.setTime(timeTxt.getText().toString());
                    updatedTask.setTitle(title.getText().toString());

                    db.updateTask(updatedTask);

                    Intent i = new Intent(UpdateTaskActivity.this, FullTaskActivity.class);
                    i.putExtra("currentTaskId", currentTaskId);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
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
