package com.example.hendriebeats.remindme;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class FullTaskActivity extends AppCompatActivity {

    public DatabaseHandler db;
    String currentTaskId;
    Button updateTaskBtn;

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

        //Link updateButton
        updateTaskBtn = (Button) findViewById(R.id.updateTaskBtn);

        //Link all the textviews that will be replaced
        TextView titleTxt = (TextView) findViewById(R.id.titleTxt);
        TextView descriptionTxt = (TextView) findViewById(R.id.descriptionTxt);
        TextView locationTxt = (TextView) findViewById(R.id.locationTxt);
        TextView dateTxt = (TextView) findViewById(R.id.dateTxt);
        TextView timeTxt = (TextView) findViewById(R.id.timeTxt);
        TextView ownerNameTxt = (TextView) findViewById(R.id.ownerTxt);

        //Set all the displayed fields equal to the current Task's values
        titleTxt.setText(db.getTaskById(Integer.parseInt(currentTaskId)).getTitle());
        descriptionTxt.setText(db.getTaskById(Integer.parseInt(currentTaskId)).getDescription());
        locationTxt.setText(db.getTaskById(Integer.parseInt(currentTaskId)).getLocation());
        dateTxt.setText(db.getTaskById(Integer.parseInt(currentTaskId)).getDate());
        timeTxt.setText(db.getTaskById(Integer.parseInt(currentTaskId)).getTime());
        ownerNameTxt.setText(db.getTaskById(Integer.parseInt(currentTaskId)).getOwnerName(this));

        //On Click Listener
        updateTaskBtn.setOnClickListener(
                new View.OnClickListener(){public void onClick(View view) {
                    Intent i = new Intent(FullTaskActivity.this, UpdateTaskActivity.class);
                    i.putExtra("currentTaskId", currentTaskId);
                    startActivity(i);
                }});

    }
}
