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

public class AddTaskActivity extends AppCompatActivity {

    Button locationBtn, chooseDateBtn, chooseTimeBtn, addTaskBtn;
    String placeName, currentUserId, title, description, date, time;
    EditText titleTxt, descriptionTxt;
    TextView locationTxt, dateTxt, timeTxt;
    Calendar myCalendar;
    DatePickerDialog.OnDateSetListener dateDialog;
    DatabaseHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        db = new DatabaseHandler(this);

        //Grab location from Place Picker
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            placeName = extras.getString("placeName");
            currentUserId = extras.getString("currentUserId");

            //pass in parameters from the place picker
            title = extras.getString("title");
            description = extras.getString("description");
            date = extras.getString("date");
            time = extras.getString("time");
        }

        //Link XML Objects to this document
        locationBtn = (Button) findViewById(R.id.locationBtn);
        chooseDateBtn = (Button) findViewById(R.id.chooseDateBtn);
        chooseTimeBtn = (Button) findViewById(R.id.chooseTimeBtn);
        addTaskBtn = (Button) findViewById(R.id.addTaskBtn);

        titleTxt = (EditText) findViewById(R.id.titleTxt);
        descriptionTxt = (EditText) findViewById(R.id.descriptionTxt);
        locationTxt = (TextView) findViewById(R.id.locationTxt);
        dateTxt = (TextView) findViewById(R.id.dateTxt);
        timeTxt = (TextView) findViewById(R.id.timeTxt);

        //populate the text fields coming back from the location picker
        titleTxt.setText(title);
        descriptionTxt.setText(description);
        dateTxt.setText(date);
        timeTxt.setText(time);

        //Set up time picker

        chooseTimeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(AddTaskActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        timeTxt.setText(selectedHour + ":" + selectedMinute);
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }
        });

        //Set up date picker
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

        //Update Location field when updated via Place Picker
        try{
            if(placeName.length()>2);

            //Populate Location Text
            locationTxt.setText(placeName);
        } catch(Exception e) {}

        //On Click Listener
        locationBtn.setOnClickListener(
                new View.OnClickListener(){public void onClick(View view) {
                    Intent i = new Intent(AddTaskActivity.this, PlacePickerAddTaskActivity.class);
                    i.putExtra("activityFrom", "AddTaskActivity");

                    //send textView values to locationPicker
                    i.putExtra("title", titleTxt.getText().toString());
                    i.putExtra("description", descriptionTxt.getText().toString());
                    i.putExtra("date", dateTxt.getText().toString());
                    i.putExtra("time", timeTxt.getText().toString());

                    startActivity(i);
                }});

        //On Click Listener
        chooseDateBtn.setOnClickListener(
                new View.OnClickListener(){public void onClick(View view) {
                    chooseDate();
                }});

        //On Click Listener
        addTaskBtn.setOnClickListener(
                new View.OnClickListener(){public void onClick(View view) {
                    Task newTask = new Task();
                    newTask.setOwnerId(Integer.parseInt(currentUserId));
                    newTask.setLocation(locationTxt.getText().toString());
                    newTask.setDescription(descriptionTxt.getText().toString());
                    newTask.setDate(dateTxt.getText().toString());
                    newTask.setTime(timeTxt.getText().toString());
                    newTask.setTitle(titleTxt.getText().toString());

                    db.addTask(newTask);
                }});
    }

    public void chooseDate(){
        new DatePickerDialog(AddTaskActivity.this, dateDialog, myCalendar
                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    private void updateDate() {

        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        dateTxt.setText(sdf.format(myCalendar.getTime()));
    }
}

/*
// perform click event listener on edit text
        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(MainActivity.this, new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                                time.setText(selectedHour + ":" + selectedMinute);
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();

            }
        });
 */
