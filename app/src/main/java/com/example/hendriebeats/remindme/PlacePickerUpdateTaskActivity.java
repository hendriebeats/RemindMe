package com.example.hendriebeats.remindme;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;

public class PlacePickerUpdateTaskActivity extends AppCompatActivity {

    int PLACE_PICKER_REQUEST;
    String currentTaskId, title, description, date, time, currentUserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_task_place_picker);

        //Get Current Task ID from Previous Activity
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            currentTaskId = extras.getString("currentTaskId");
            currentUserId = extras.getString("currentUserId");
            title = extras.getString("title");
            description = extras.getString("description");
            date = extras.getString("date");
            time = extras.getString("time");
        }

        Toast.makeText(getApplicationContext(), currentUserId, Toast.LENGTH_SHORT).show();

        PlacePicker.IntentBuilder intentBuilder = new PlacePicker.IntentBuilder();
        // Start the Intent by requesting a result, identified by a request code.

        PLACE_PICKER_REQUEST = 1;
        PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();

        try {
            startActivityForResult(builder.build(this), PLACE_PICKER_REQUEST);
        } catch (GooglePlayServicesRepairableException e) {
            e.printStackTrace();
        } catch (GooglePlayServicesNotAvailableException e) {
            e.printStackTrace();
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == RESULT_OK) {
                Place place = PlacePicker.getPlace(this, data);

                Intent i = new Intent(PlacePickerUpdateTaskActivity.this, UpdateTaskActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                //place info
                i.putExtra("placeTitle", place.getName());
                i.putExtra("placeAddress", place.getAddress());
                i.putExtra("placeLatitude", place.getLatLng().latitude);
                i.putExtra("placeLongitude", place.getLatLng().longitude);
                i.putExtra("placeLocale", place.getLocale());

                //task info that is being updated
                i.putExtra("title", title);
                i.putExtra("description", description);
                i.putExtra("date", date);
                i.putExtra("time", time);

                i.putExtra("currentUserId", currentUserId);
                i.putExtra("currentTaskId", currentTaskId);

                //to tell the next activity if the place has been updated
                i.putExtra("isUpdated", "yes");
                startActivity(i);
            }
        }
    }
}
