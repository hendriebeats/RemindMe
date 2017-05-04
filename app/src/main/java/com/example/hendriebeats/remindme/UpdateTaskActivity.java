package com.example.hendriebeats.remindme;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class UpdateTaskActivity extends AppCompatActivity {

    Button updateDateBtn;
    Button updateTimeBtn;
    Button updateLocationBtn;
    Button updateTaskBtn;

    EditText updateTitleTxt;
    EditText updateDescriptionTxt;

    TextView updateTitleLbl;
    TextView updateDescriptionLbl;
    TextView updateDateLbl;
    TextView updateDateTxt;
    TextView updateTimeLbl;
    TextView updateTimeTxt;
    TextView updateLocationLbl;
    TextView updatePlaceTitleTxt;
    TextView updatePlaceAddressTxt;

    //Used to hide keyboard when pressed outside of an EditText field
    public FrameLayout touchInterceptor;

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

        updateDateBtn = (Button) findViewById(R.id.updateDateBtn);
        updateTimeBtn = (Button) findViewById(R.id.updateTimeBtn);
        updateLocationBtn = (Button) findViewById(R.id.updateLocationBtn);
        updateTaskBtn = (Button) findViewById(R.id.updateTaskBtn);

        updateTitleTxt = (EditText) findViewById(R.id.updateTitleTxt);
        updateDescriptionTxt = (EditText) findViewById(R.id.updateDescriptionTxt);

        updateTitleLbl = (TextView) findViewById(R.id.updateTitleLbl);
        updateDescriptionLbl = (TextView) findViewById(R.id.updateDescriptionLbl);
        updateDateLbl = (TextView) findViewById(R.id.updateDateLbl);
        updateDateTxt = (TextView) findViewById(R.id.updateDateTxt);
        updateTimeLbl = (TextView) findViewById(R.id.updateTimeLbl);
        updateTimeTxt = (TextView) findViewById(R.id.updateTimeTxt);
        updateLocationLbl = (TextView) findViewById(R.id.updateLocationLbl);
        updatePlaceTitleTxt = (TextView) findViewById(R.id.updatePlaceTitleTxt);
        updatePlaceAddressTxt = (TextView) findViewById(R.id.updatePlaceAddressTxt);

        touchInterceptor = (FrameLayout) findViewById(R.id.touchInterceptorUpdateTask);
        touchInterceptor.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                hideKeyboard(v, event);
                return false;
            }
        });

        /**
         * Set all the displayed fields equal to the current Task's values
         */
        updateTitleTxt.setText(db.getTaskById(Integer.parseInt(currentTaskId)).getTitle());
        updateDescriptionTxt.setText(db.getTaskById(Integer.parseInt(currentTaskId)).getDescription());
        updateDateTxt.setText(db.getTaskById(Integer.parseInt(currentTaskId)).getDate());
        updateTimeTxt.setText(db.getTaskById(Integer.parseInt(currentTaskId)).getTime());
        updatePlaceTitleTxt.setText(db.getPlaceById(db.getTaskById(Integer.parseInt(currentTaskId)).getPlaceId()).getTitle());
        updatePlaceAddressTxt.setText(db.getPlaceById(db.getTaskById(Integer.parseInt(currentTaskId)).getPlaceId()).getAddress());

        /**
         * Update Location field when updated via Place Picker
         */
        try{
            if(isUpdated.equals("yes")){
                updatePlaceTitleTxt.setText(placeTitle);
                updatePlaceAddressTxt.setText(placeAddress);
            }
        } catch(Exception e) {}

        /**
         * Location Onclick Listener to update the location from Place Picker
         */
        updateLocationBtn.setOnClickListener(
                new View.OnClickListener(){public void onClick(View view) {
                    Intent i = new Intent(UpdateTaskActivity.this, PlacePickerUpdateTaskActivity.class);
                    i.putExtra("currentTaskId", currentTaskId);
                    i.putExtra("currentUserId", currentUserId);
                    startActivity(i);
                }});

        /**
         * Create Time Picker
         */
        updateTimeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(UpdateTaskActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        updateTimeTxt.setText(selectedHour + ":" + selectedMinute);
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
        updateDateBtn.setOnClickListener(
                new View.OnClickListener(){public void onClick(View view) {
                    chooseDate();
                }});

        /**
         * Update Onclick Listener to update the task list with the new information provided by user
         */
        updateTaskBtn.setOnClickListener(
                new View.OnClickListener(){public void onClick(View view) {
                    //create task object
                    Task updatedTask = new Task();
                    Place updatedPlace = db.getPlaceById(db.getTaskById(Integer.parseInt(currentTaskId)).getId());
                    updatedTask.setId(Integer.parseInt(currentTaskId));
                    updatedTask.setOwnerId(Integer.parseInt(currentUserId));
                    updatedTask.setDescription(updateDescriptionTxt.getText().toString());
                    updatedTask.setDate(updateDateTxt.getText().toString());
                    updatedTask.setTime(updateTimeTxt.getText().toString());
                    updatedTask.setTitle(updateTitleTxt.getText().toString());
                    updatedTask.setComplete(db.getTaskById(Integer.parseInt(currentTaskId)).isComplete());

                    /**
                     * Update Location field when updated via Place Picker
                     */
                    try{
                        if(isUpdated.equals("yes")){
                            updatedPlace.setTitle(placeTitle);
                            updatedPlace.setLatitude(placeLatitude);
                            updatedPlace.setLongitude(placeLongitude);
                            updatedPlace.setAddress(placeAddress);
                            updatedPlace.setLocale(placeLocale);
                            db.updatePlace(updatedPlace);
                        }
                    } catch(Exception e) {}

                    db.updateTask(updatedTask);

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
        updateDateTxt.setText(sdf.format(myCalendar.getTime()));
    }

    public void hideKeyboard(View v, MotionEvent event){
        //Check through all EditText fields to see if one is selected
        if(event.getAction() == MotionEvent.ACTION_DOWN) {
            //NAME TEXT
            if (updateTitleTxt.isFocused()) {
                Rect outRect = new Rect();
                updateTitleTxt.getGlobalVisibleRect(outRect);
                if (!outRect.contains((int) event.getRawX(), (int) event.getRawY())) {
                    updateTitleTxt.clearFocus();
                    InputMethodManager imm = (InputMethodManager)
                            v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
            //PHONE TEXT
            else if (updateDescriptionTxt.isFocused()) {
                Rect outRect = new Rect();
                updateDescriptionTxt.getGlobalVisibleRect(outRect);
                if (!outRect.contains((int) event.getRawX(), (int) event.getRawY())) {
                    updateDescriptionTxt.clearFocus();
                    InputMethodManager imm = (InputMethodManager)
                            v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
        }
    }
}
