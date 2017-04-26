package com.example.hendriebeats.remindme;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;

public class PlacePickerUpdateTaskActivity extends AppCompatActivity {

    int PLACE_PICKER_REQUEST;
    String currentTaskId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_task_place_picker);

        //Get Current Task ID from Previous Activity
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            currentTaskId = extras.getString("currentTaskId");
        }

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
                i.putExtra("placeTitle", place.getName());
                i.putExtra("placeAddress", place.getAddress());
                i.putExtra("placeLatitude", place.getLatLng().latitude);
                i.putExtra("placeLongitude", place.getLatLng().longitude);
                i.putExtra("placeLocale", place.getLocale());
                i.putExtra("currentTaskId", currentTaskId);
                startActivity(i);
            }
        }
    }
}
