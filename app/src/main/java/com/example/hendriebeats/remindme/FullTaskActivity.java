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

import org.w3c.dom.Text;

public class FullTaskActivity extends AppCompatActivity {

    public DatabaseHandler db;
    String currentTaskId, currentUserId;

    Button fullShareBtn;
    Button fullUpdateBtn;
    Button fullDeleteBtn;
    Button fullDoneBtn;

    TextView fullTitleLbl;
    TextView fullTitleTxt;
    TextView fullDescriptionLbl;
    TextView fullDescriptionTxt;
    TextView fullDateLbl;
    TextView fullDateTxt;
    TextView fullTimeLbl;
    TextView fullTimeTxt;
    TextView fullLocationLbl;
    TextView fullPlaceTitleTxt;
    TextView fullPlaceAddressTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_task);

        db = new DatabaseHandler(this);

        //Get Current Task ID from Previous Activity
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            currentTaskId = extras.getString("currentTaskId");
            currentUserId = extras.getString("currentUserId");
        }

        //pulling out values
        final String title = db.getTaskById(Integer.parseInt(currentTaskId)).getTitle();
        final String description = db.getTaskById(Integer.parseInt(currentTaskId)).getDescription();
        final String date = db.getTaskById(Integer.parseInt(currentTaskId)).getDate();
        final String time = db.getTaskById(Integer.parseInt(currentTaskId)).getTime();
        final String placeTitle = db.getPlaceById(db.getTaskById(Integer.parseInt(currentTaskId)).getPlaceId()).getTitle();
        final String placeAddress = db.getPlaceById(db.getTaskById(Integer.parseInt(currentTaskId)).getPlaceId()).getAddress();

        fullShareBtn = (Button) findViewById(R.id.fullShareBtn);
        fullUpdateBtn = (Button) findViewById(R.id.fullUpdateBtn);
        fullDeleteBtn = (Button) findViewById(R.id.fullDeleteBtn);
        fullDoneBtn = (Button) findViewById(R.id.fullDoneBtn);

        fullTitleLbl = (TextView) findViewById(R.id.fullTitleLbl);
        fullTitleTxt = (TextView) findViewById(R.id.fullTitleTxt);
        fullDescriptionLbl = (TextView) findViewById(R.id.fullDescriptionLbl);
        fullDescriptionTxt = (TextView) findViewById(R.id.fullDescriptionTxt);
        fullDateLbl = (TextView) findViewById(R.id.fullDateLbl);
        fullDateTxt = (TextView) findViewById(R.id.fullDateTxt);
        fullTimeLbl = (TextView) findViewById(R.id.fullTimeLbl);
        fullTimeTxt = (TextView) findViewById(R.id.fullTimeTxt);
        fullLocationLbl = (TextView) findViewById(R.id.fullLocationLbl);
        fullPlaceTitleTxt = (TextView) findViewById(R.id.fullPlaceTitleTxt);
        fullPlaceAddressTxt = (TextView) findViewById(R.id.fullPlaceAddressTxt);

        //Set all the displayed fields equal to the current Task's values
        fullTitleTxt.setText(title);
        fullDescriptionTxt.setText(description);
        fullDateTxt.setText(date);
        fullTimeTxt.setText(time);
        fullPlaceTitleTxt.setText(placeTitle);
        fullPlaceAddressTxt.setText(placeAddress);


        //On Click Listener
        fullUpdateBtn.setOnClickListener(
                new View.OnClickListener(){public void onClick(View view) {
                    Intent i = new Intent(FullTaskActivity.this, UpdateTaskActivity.class);
                    i.putExtra("currentTaskId", currentTaskId);
                    i.putExtra("currentUserId", currentUserId);

                    //send textView values to locationPicker
                    i.putExtra("title", fullTitleTxt.getText().toString());
                    i.putExtra("description", fullDescriptionTxt.getText().toString());
                    i.putExtra("date", fullDateTxt.getText().toString());
                    i.putExtra("time", fullTimeTxt.getText().toString());
                    startActivity(i);
                }});

        //On Click Listener
        fullDeleteBtn.setOnClickListener(
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
                            i.putExtra("currentUserId", String.valueOf(currentUserId));
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
        fullShareBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("text/plain");
                i.putExtra(Intent.EXTRA_TEXT, getTaskReport(title, placeTitle));
                i.putExtra(Intent.EXTRA_SUBJECT,
                        getString(R.string.task_report_subject));
                startActivity(i);
            }
        });

        fullDoneBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                Task updatedTask = db.getTaskById(Integer.parseInt(currentTaskId));
                boolean completeVar = updatedTask.isComplete();
                completeVar = !completeVar; //switch between true and false
                updatedTask.setComplete(completeVar);
                db.updateTask(updatedTask);

                Intent i=new Intent(FullTaskActivity.this, TaskListActivity.class);
                i.putExtra("currentUserId", currentUserId);
                startActivity(i);

            }
        });
    }

    // Used to generate a sendable task report via chosen existing service.
    // The exact message that is generatated should be changed, this is just showing a basic placeholder
    private String getTaskReport(String title, String location){
        return "I am doing " + title + " at " + location + ". Want to join me?";
    }

}
