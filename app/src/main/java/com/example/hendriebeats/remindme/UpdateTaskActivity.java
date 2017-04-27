package com.example.hendriebeats.remindme;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class UpdateTaskActivity extends AppCompatActivity {

    EditText title, description, dateTxt, timeTxt;
    TextView PlaceTitleTxt, PlaceAddressTxt;
    Button changeLocationBtn, updateBtn,chooseDateBtn, chooseTimeBtn;
    String currentTaskId, placeTitle, placeAddress, placeLatitude, placeLongitude, placeLocale, isUpdated, currentUserId;
    Calendar myCalendar;
    DatePickerDialog.OnDateSetListener dateDialog;
    public DatabaseHandler db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_task);

        db = new DatabaseHandler(this);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            /**
             * Get Current Task ID from Previous Activity
             */
            currentTaskId = extras.getString("currentTaskId");
            currentUserId = extras.getString("currentUserId");

            /**
             * Get Location Elements
             */
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
        changeLocationBtn = (Button) findViewById(R.id.locationBtn);
        updateBtn = (Button) findViewById(R.id.updateBtn);
        chooseDateBtn = (Button) findViewById(R.id.chooseDateBtn);
        chooseTimeBtn = (Button) findViewById(R.id.chooseTimeBtn);
        PlaceTitleTxt = (TextView) findViewById(R.id.placeTitleTxt);
        PlaceAddressTxt = (TextView) findViewById(R.id.placeAddressTxt);

        /**
         * Set all the displayed fields equal to the current Task's values
         */
        title.setText(db.getTaskById(Integer.parseInt(currentTaskId)).getTitle());
        description.setText(db.getTaskById(Integer.parseInt(currentTaskId)).getDescription());
        dateTxt.setText(db.getTaskById(Integer.parseInt(currentTaskId)).getDate());
        timeTxt.setText(db.getTaskById(Integer.parseInt(currentTaskId)).getTime());
        PlaceTitleTxt.setText(db.getPlaceById(db.getTaskById(Integer.parseInt(currentTaskId)).getPlaceId()).getTitle());
        PlaceAddressTxt.setText(db.getPlaceById(db.getTaskById(Integer.parseInt(currentTaskId)).getPlaceId()).getAddress());

        /**
         * Update Location field when updated via Place Picker
         */
        try{
            if(isUpdated.equals("yes")){
                PlaceTitleTxt.setText(placeTitle);
                PlaceAddressTxt.setText(placeAddress);
            }
        } catch(Exception e) {}

        /**
         * Location Onclick Listener to update the location from Place Picker
         */
        changeLocationBtn.setOnClickListener(
                new View.OnClickListener(){public void onClick(View view) {
                    Intent i = new Intent(UpdateTaskActivity.this, PlacePickerUpdateTaskActivity.class);
                    i.putExtra("currentTaskId", currentTaskId);
                    i.putExtra("currentUserId", currentUserId);
                    startActivity(i);
                }});

        /**
         * Create Time Picker
         */
        chooseTimeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(UpdateTaskActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        timeTxt.setText(selectedHour + ":" + selectedMinute);
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }
        });

        /**
         * Create Date Picker
         */
        myCalendar = Calendar.getInstance();
        dateDialog = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateDate();
            }

        };

        /**
         * Date Onclick Listener to update the date form Date Picker
         */
        chooseDateBtn.setOnClickListener(
                new View.OnClickListener(){public void onClick(View view) {
                    chooseDate();
                }});

        /**
         * Update Onclick Listener to update the task list with the new information provided by user
         */
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
    public void chooseDate(){
        new DatePickerDialog(UpdateTaskActivity.this, dateDialog, myCalendar
                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    private void updateDate() {

        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        dateTxt.setText(sdf.format(myCalendar.getTime()));
    }
}
