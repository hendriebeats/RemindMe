package com.example.hendriebeats.remindme;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class FullTaskActivity extends AppCompatActivity {

    public DatabaseHandler db;
    String currentTaskId;
    Button updateTaskBtn, deleteTaskBtn, shareTaskBtn;
    TextView placeTitleTxt, placeAddressTxt, titleTxt, descriptionTxt, dateTxt, timeTxt, ownerNameTxt;

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
        shareTaskBtn = (Button) findViewById(R.id.shareTaskBtn);
        deleteTaskBtn = (Button) findViewById(R.id.deleteTaskBtn);

        //Link all the textviews that will be replaced
        titleTxt = (TextView) findViewById(R.id.titleTxt);
        descriptionTxt = (TextView) findViewById(R.id.descriptionTxt);
        placeTitleTxt = (TextView) findViewById(R.id.placeTitleTxt);
        placeAddressTxt = (TextView) findViewById(R.id.placeAddressTxt);
        dateTxt = (TextView) findViewById(R.id.dateTxt);
        timeTxt = (TextView) findViewById(R.id.timeTxt);
        ownerNameTxt = (TextView) findViewById(R.id.ownerTxt);

        //pulling out values
        final String title = db.getTaskById(Integer.parseInt(currentTaskId)).getTitle();
        final String description = db.getTaskById(Integer.parseInt(currentTaskId)).getDescription();
        final String date = db.getTaskById(Integer.parseInt(currentTaskId)).getDate();
        final String time = db.getTaskById(Integer.parseInt(currentTaskId)).getTime();
        final String ownerName = db.getTaskById(Integer.parseInt(currentTaskId)).getOwnerName(this);
        final String placeTitle = db.getPlaceById(db.getTaskById(Integer.parseInt(currentTaskId)).getPlaceId()).getTitle();
        final String placeAddress = db.getPlaceById(db.getTaskById(Integer.parseInt(currentTaskId)).getPlaceId()).getAddress();

        //Set all the displayed fields equal to the current Task's values
        titleTxt.setText(title);
        descriptionTxt.setText(description);
        placeTitleTxt.setText(placeTitle);
        placeAddressTxt.setText(placeAddress);
        dateTxt.setText(date);
        timeTxt.setText(time);
        ownerNameTxt.setText(ownerName);

        //On Click Listener
        updateTaskBtn.setOnClickListener(
                new View.OnClickListener(){public void onClick(View view) {
                    Intent i = new Intent(FullTaskActivity.this, UpdateTaskActivity.class);
                    i.putExtra("currentTaskId", currentTaskId);
                    startActivity(i);
                }});

        //On Click Listener
        deleteTaskBtn.setOnClickListener(
                new View.OnClickListener(){public void onClick(View view) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(FullTaskActivity.this);

                    builder.setTitle("Confirm");
                    builder.setMessage("Are you sure?");
                    builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            try{
                                db.deleteTask(db.getTaskById(Integer.parseInt(currentTaskId)));
                            }
                            catch(Exception e){
                                Toast.makeText(getApplicationContext(), "Failed to delete Task: "+e+".", Toast.LENGTH_SHORT).show();
                            }
                            Intent i = new Intent(FullTaskActivity.this, TaskListActivity.class);
                            i.putExtra("currentUserId", String.valueOf(db.getTaskById(Integer.parseInt(currentTaskId)).getOwnerId()));
                            startActivity(i);
                        }
                    });

                    builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });

                    AlertDialog alert = builder.create();
                    alert.show();



                }});

        // Will ask to open an existing service - covers requirement g for final project
        shareTaskBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("text/plain");
                i.putExtra(Intent.EXTRA_TEXT, getTaskReport(ownerName, title, placeTitle));
                i.putExtra(Intent.EXTRA_SUBJECT,
                        getString(R.string.task_report_subject));
                startActivity(i);
            }
        });
    }

    // Used to generate a sendable task report via chosen existing service.
    // The exact message that is generatated should be changed, this is just showing a basic placeholder
    private String getTaskReport(String ownerName, String title, String location){
        String report = getString(R.string.task_report,
                ownerName, title, location);
        return report;
    }

}
