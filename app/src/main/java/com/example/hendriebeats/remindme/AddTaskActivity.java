package com.example.hendriebeats.remindme;

import android.app.DatePickerDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import layout.FragmentOne;

public class AddTaskActivity extends AppCompatActivity {

    Button basicInformationBtn, locationBtn, chooseDateBtn, chooseTimeBtn;
    String placeName, currentUserId;
    EditText titleTxt, descriptionTxt;
    TextView locationTxt, dateTxt;
    Calendar myCalendar;
    DatePickerDialog.OnDateSetListener date;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        //Grab location from Place Picker
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            placeName = extras.getString("placeName");
            currentUserId = extras.getString("currentUserId");
        }

        //Link XML Objects to this document
        basicInformationBtn = (Button) findViewById(R.id.basicInformationBtn);
        locationBtn = (Button) findViewById(R.id.locationBtn);
        chooseDateBtn = (Button) findViewById(R.id.chooseDateBtn);
        chooseTimeBtn = (Button) findViewById(R.id.chooseTimeBtn);

        titleTxt = (EditText) findViewById(R.id.titleTxt);
        descriptionTxt = (EditText) findViewById(R.id.descriptionTxt);
        locationTxt = (TextView) findViewById(R.id.locationTxt);
        dateTxt = (TextView) findViewById(R.id.dateTxt);

        //Set up date picker
        myCalendar = Calendar.getInstance();
        date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };

        //Populate Location Text
        locationTxt.setText(placeName);

        //On Click Listener
        basicInformationBtn.setOnClickListener(
                new View.OnClickListener(){public void onClick(View view) {
                    switchToBasicInformationFragment();
                }});

        //On Click Listener
        locationBtn.setOnClickListener(
                new View.OnClickListener(){public void onClick(View view) {
                    switchToLocationActivity();
                }});

        //On Click Listener
        chooseDateBtn.setOnClickListener(
                new View.OnClickListener(){public void onClick(View view) {
                    chooseDate();
                }});

    }
    public void switchToBasicInformationFragment(){
        // change the fragment to fragment 1
        Fragment frag = new FragmentOne();
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.fragmentContainer, frag);
        ft.commit();
    }

    public void switchToLocationActivity(){
        Intent i = new Intent(AddTaskActivity.this, PlacePickerActivity.class);
        i.putExtra("activityFrom", "AddTaskActivity");
        startActivity(i);
    }

    public void chooseDate(){
        new DatePickerDialog(AddTaskActivity.this, date, myCalendar
                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    private void updateLabel() {

        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        dateTxt.setText(sdf.format(myCalendar.getTime()));
    }
}
