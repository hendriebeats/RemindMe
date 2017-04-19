package com.example.hendriebeats.remindme;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import static com.example.hendriebeats.remindme.R.id.dateTimeTxt;
import static com.example.hendriebeats.remindme.R.id.descriptionTxt;
import static com.example.hendriebeats.remindme.R.id.locationTxt;
import static com.example.hendriebeats.remindme.R.id.titleTxt;

public class FullTaskActivity extends AppCompatActivity {

    public DatabaseHandler db;
    String currentTaskId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_task);

        db = new DatabaseHandler(this);

        //Get Current Task ID from Previous Activity
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            currentTaskId = extras.getString("currentTaskId");
        }

        //Link all the textViews that will be replaced
        TextView title = (TextView) findViewById(R.id.titleTxt);
        TextView description = (TextView) findViewById(R.id.descriptionTxt);
        TextView location = (TextView) findViewById(R.id.locationTxt);
        TextView dateTime = (TextView) findViewById(R.id.dateTimeTxt);
        TextView ownerName = (TextView) findViewById(R.id.ownerTxt);

        //Set all the displayed fields equal to the current Task's values
        title.setText(db.getTaskById(Integer.parseInt(currentTaskId)).getTitle());
        description.setText(db.getTaskById(Integer.parseInt(currentTaskId)).getDescription());
        location.setText(db.getTaskById(Integer.parseInt(currentTaskId)).getLocation());
        dateTime.setText(db.getTaskById(Integer.parseInt(currentTaskId)).getDateTime());
        ownerName.setText(db.getTaskById(Integer.parseInt(currentTaskId)).getOwnerName(this));


    }
}
