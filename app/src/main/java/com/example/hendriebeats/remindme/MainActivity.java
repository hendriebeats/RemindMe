package com.example.hendriebeats.remindme;

import android.content.Intent;
import android.icu.util.Calendar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    public EditText emailTxt;
    public EditText passwordTxt;
    public Button submitBtn;
    public Button createAccountBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        emailTxt = (EditText)findViewById(R.id.emailTxt);
        passwordTxt = (EditText)findViewById(R.id.passwordTxt);
        submitBtn = (Button)findViewById(R.id.submitBtn);
        createAccountBtn = (Button)findViewById(R.id.createAccountBtn);

        //SubmitBtn Action
        submitBtn.setOnClickListener(
                new View.OnClickListener(){public void onClick(View view) {
                    submit();
                }});

        //createAccountBtn Action
        createAccountBtn.setOnClickListener(
                new View.OnClickListener(){public void onClick(View view) {
                    createAccount();
                }});
    }
    //Test email/pass and act appropriately
    public void submit(){
        Toast.makeText(getApplicationContext(), "Email: " + emailTxt.getText().toString() +
                ", Password: " + passwordTxt.getText().toString(), Toast.LENGTH_LONG).show();

        //Add an If statement here if the credentials pass
        Intent i=new Intent(MainActivity.this, TaskListActivity.class);
        startActivity(i);

        //Uncomment error message
        //Toast.makeText(getApplicationContext(), "Error, you entered the wrong username or password", Toast.LENGTH_LONG).show();

    }

    //Moves to the create account page
    public void createAccount(){
        //Move to the create Account Page
        Intent i=new Intent(MainActivity.this, CreateAccount.class);
        startActivity(i);
    }
}
