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

    Button ShareBtn;
    Button UpdateBtn;
    Button DeleteBtn;
    Button checkBtn;

    TextView TitleLbl;
    TextView TitleTxt;
    TextView DescriptionLbl;
    TextView DescriptionTxt;
    TextView DateLbl;
    TextView DateTxt;
    TextView TimeLbl;
    TextView TimeTxt;
    TextView LocationLbl;
    TextView PlaceTitleTxt;
    TextView PlaceAddressTxt;

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

        ShareBtn = (Button) findViewById(R.id.ShareBtn);
        UpdateBtn = (Button) findViewById(R.id.UpdateBtn);
        DeleteBtn = (Button) findViewById(R.id.DeleteBtn);
        checkBtn = (Button) findViewById(R.id.checkBtn);

        TitleLbl = (TextView) findViewById(R.id.TitleLbl);
        TitleTxt = (TextView) findViewById(R.id.TitleTxt);
        DescriptionLbl = (TextView) findViewById(R.id.DescriptionLbl);
        DescriptionTxt = (TextView) findViewById(R.id.DescriptionTxt);
        DateLbl = (TextView) findViewById(R.id.DateLbl);
        DateTxt = (TextView) findViewById(R.id.DateTxt);
        TimeLbl = (TextView) findViewById(R.id.TimeLbl);
        TimeTxt = (TextView) findViewById(R.id.TimeTxt);
        LocationLbl = (TextView) findViewById(R.id.LocationLbl);
        PlaceTitleTxt = (TextView) findViewById(R.id.PlaceTitleTxt);
        PlaceAddressTxt = (TextView) findViewById(R.id.PlaceAddressTxt);

        //Set all the displayed fields equal to the current Task's values
        TitleTxt.setText(title);
        DescriptionTxt.setText(description);
        DateTxt.setText(date);
        TimeTxt.setText(time);
        PlaceTitleTxt.setText(placeTitle);
        PlaceAddressTxt.setText(placeAddress);


        //Set checkBtn text
        if(db.getTaskById(Integer.parseInt(currentTaskId)).isComplete()){
            checkBtn.setText("Not Complete...");
        }


        //On Click Listener
        UpdateBtn.setOnClickListener(
                new View.OnClickListener(){public void onClick(View view) {
                    Intent i = new Intent(FullTaskActivity.this, UpdateTaskActivity.class);
                    i.putExtra("currentTaskId", currentTaskId);
                    i.putExtra("currentUserId", currentUserId);

                    //send textView values to locationPicker
                    i.putExtra("title", TitleTxt.getText().toString());
                    i.putExtra("description", DescriptionTxt.getText().toString());
                    i.putExtra("date", DateTxt.getText().toString());
                    i.putExtra("time", TimeTxt.getText().toString());
                    startActivity(i);
                }});

        //On Click Listener
        DeleteBtn.setOnClickListener(
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
        ShareBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("text/plain");
                i.putExtra(Intent.EXTRA_TEXT, getTaskReport(title, placeTitle));
                i.putExtra(Intent.EXTRA_SUBJECT,
                        getString(R.string.task_report_subject));
                startActivity(i);
            }
        });

        checkBtn.setOnClickListener(new View.OnClickListener() {
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
