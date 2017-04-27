package com.example.hendriebeats.remindme;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class AddTaskActivity extends AppCompatActivity {

    Button locationBtn, chooseDateBtn, chooseTimeBtn, addTaskBtn;
    String placeName, placeAddress, currentUserId, title, description, date, time,
            placeLatitude, placeLongitude, placeLocale;
    EditText titleTxt, descriptionTxt;
    TextView locationTitleTxt, locationAddressTxt, dateTxt, timeTxt;
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
            currentUserId = extras.getString("currentUserId");

            //Get Location Elements
            placeName = extras.getString("placeTitle");
            placeAddress = extras.getString("placeAddress");
            placeLatitude = extras.getString("placeLatitude");
            placeLongitude = extras.getString("placeLongitude");
            placeLocale = extras.getString("placeLocale");

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
        locationTitleTxt = (TextView) findViewById(R.id.placeTitleTxt);
        locationAddressTxt = (TextView) findViewById(R.id.placeAddressTxt);
        dateTxt = (TextView) findViewById(R.id.dateTxt);
        timeTxt = (TextView) findViewById(R.id.timeTxt);

        //populate the text fields coming back from the location picker
        titleTxt.setText(title);
        descriptionTxt.setText(description);
        dateTxt.setText(date);
        timeTxt.setText(time);
        locationTitleTxt.setText(placeName);
        locationAddressTxt.setText(placeAddress);

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
                    i.putExtra("currentUserId", currentUserId);

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
                    Place newPlace = new Place();

                    newPlace.setTitle(placeName);
                    newPlace.setAddress(placeAddress);
                    newPlace.setLatitude(placeLatitude);
                    newPlace.setLongitude(placeLongitude);
                    newPlace.setLocale(placeLocale);
                    db.addPlace(newPlace);

                    //Toast.makeText(getApplicationContext(), "" + db.getMostRecentPlace().getId(), Toast.LENGTH_SHORT).show();

                    newTask.setTitle(titleTxt.getText().toString());
                    newTask.setDescription(descriptionTxt.getText().toString());
                    newTask.setDate(dateTxt.getText().toString());
                    newTask.setTime(timeTxt.getText().toString());
                    newTask.setOwnerId(Integer.parseInt(currentUserId));
                    newTask.setPlaceId(db.getMostRecentPlace().getId());
                    newTask.setComplete(false);
                    db.addTask(newTask);

                    Intent i = new Intent(AddTaskActivity.this, TaskListActivity.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    i.putExtra("currentUserId", currentUserId);
                    startActivity(i);
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
