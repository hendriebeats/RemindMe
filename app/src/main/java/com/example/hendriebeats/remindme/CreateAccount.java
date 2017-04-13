package com.example.hendriebeats.remindme;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

public class CreateAccount extends AppCompatActivity {

    public EditText nameTxt;
    public EditText phoneTxt;
    public EditText emailTxt;
    public EditText passwordTxt;
    public EditText confirmPasswordTxt;
    public Button submitBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        nameTxt = (EditText)findViewById(R.id.nameTxt);
        phoneTxt = (EditText)findViewById(R.id.phoneTxt);
        emailTxt = (EditText)findViewById(R.id.emailTxt);
        passwordTxt = (EditText)findViewById(R.id.passwordTxt);
        confirmPasswordTxt = (EditText)findViewById(R.id.confirmPasswordTxt);
        submitBtn = (Button)findViewById(R.id.submitBtn);

        //SubmitBtn Action
        submitBtn.setOnClickListener(
                new View.OnClickListener(){public void onClick(View view) {
                    submit();
                }});
    }

    //Test email/pass and act appropriately
    public void submit(){

        DatabaseHandler db = new DatabaseHandler(this);


        if(passwordTxt.getText().toString().equals(confirmPasswordTxt.getText().toString())){
            // Add user
            Log.d("Insert: ", "Inserting ..");
            db.addUser(new User(
                    nameTxt.getText().toString(),
                    phoneTxt.getText().toString(),
                    emailTxt.getText().toString(),
                    passwordTxt.getText().toString()
            ));

            Intent i=new Intent(CreateAccount.this, MainActivity.class);
            startActivity(i);
        } else{
            Toast.makeText(getApplicationContext(), "Password did not match. Please try again.", Toast.LENGTH_LONG).show();
        }


    }
}
