package com.example.hendriebeats.remindme;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import layout.FragmentOne;
import layout.FragmentTwo;

public class AddTaskActivity extends AppCompatActivity
                            /*implements OnMapReadyCallback*/{

    Button basicInformationBtn, dateTimeBtn;

    //private GoogleMap mMap;
    //private static final LatLng PCT = new LatLng(41.234641, -77.021090);



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        //Link buttons to this document
        basicInformationBtn = (Button) findViewById(R.id.btn1);
        dateTimeBtn = (Button) findViewById(R.id.btn2);

        //On Click Listener for Button 1
        basicInformationBtn.setOnClickListener(
                new View.OnClickListener(){public void onClick(View view) {
                    switchToBasicInformationFragment();
                }});

        //On Click Listener for Button 1
        dateTimeBtn.setOnClickListener(
                new View.OnClickListener(){public void onClick(View view) {
                    switchToDateTimeFragment();
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

    public void switchToDateTimeFragment(){
        // change the fragment to fragment 2
        Fragment frag = new FragmentTwo();
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.fragmentContainer, frag);
        ft.commit();
    }

    /*
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        Marker pct = mMap.addMarker(new MarkerOptions()
            .position(PCT));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(PCT, 15));
    }
    */
}
