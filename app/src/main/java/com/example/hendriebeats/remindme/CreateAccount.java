package com.example.hendriebeats.remindme;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

import static com.example.hendriebeats.remindme.Password.hashPassword;

public class CreateAccount extends AppCompatActivity {

    public EditText nameTxt;
    public EditText phoneTxt;
    public EditText emailTxt;
    public EditText passwordTxt;
    public EditText confirmPasswordTxt;
    public Button submitBtn;
    public DatabaseHandler db;
    User validate;


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

    public void submit(){

        db = new DatabaseHandler(this);
        //SQLiteDatabase sqldb = db.getReadableDatabase();

        // adding these variables because they are used in multiple locations below
        String name = nameTxt.getText().toString();
        String phone = phoneTxt.getText().toString();
        String email = emailTxt.getText().toString();
        String pass = passwordTxt.getText().toString();
        String confirmPass = confirmPasswordTxt.getText().toString();

        //trimming email and password fields to remove possible excess whitespace
        email = email.trim();
        pass = pass.trim();
        confirmPass = confirmPass.trim();


            // ensure email + password boxes are not empty.
            if (email.isEmpty() || pass.isEmpty()) {
                Toast.makeText(getApplicationContext(), "Please enter an email and password.", Toast.LENGTH_LONG).show();
            } else if (pass.equals(confirmPass) && !ifUserExists(email)) {
                // Add user to database, then return to main activity
                // NOTE: Might want to go back an activity instead of creating a new activity as done below
                Log.d("Insert: ", "Inserting ..");
                db.addUser(new User(
                        name,
                        phone,
                        email,
                        hashPassword(pass)
                ));

                //Clear values if create account is successful
                nameTxt.setText("");
                phoneTxt.setText("");
                emailTxt.setText("");
                passwordTxt.setText("");
                confirmPasswordTxt.setText("");

                Intent i = new Intent(CreateAccount.this, MainActivity.class);
                startActivity(i);
                Toast.makeText(getApplicationContext(), "Account Created Successfully", Toast.LENGTH_LONG).show();

            } else {
                Toast.makeText(getApplicationContext(), "Password did not match. Please try again.", Toast.LENGTH_LONG).show();
            }
    }


    public boolean ifUserExists(String email){
        try{
            validate = db.getUserByEmail(email);
            Toast.makeText(getApplicationContext(), "A user already exists with the same Email. Please try again.", Toast.LENGTH_LONG).show();
            return true;
        }catch (Exception e){
            return false;
        }
    }
}
