package com.example.hendriebeats.remindme;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import layout.FragmentOne;
import layout.FragmentTwo;

public class AddTaskActivity extends AppCompatActivity {

    Button basicInformationBtn, dateTimeBtn;

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
}
