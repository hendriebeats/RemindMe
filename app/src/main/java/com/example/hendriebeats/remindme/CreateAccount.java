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

    /** 4/15/17 - Michael changes
     * Tests username and password. Checks for blank fields.
     *
     * Could previously create an account with blank username and password and login
     * would be successful with those fields empty.
     */
    public void submit(){

        DatabaseHandler db = new DatabaseHandler(this);
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
            } else if (pass.equals(confirmPass)) {
                // Add user to database, then return to main activity
                // NOTE: Might want to go back an activity instead of creating a new activity as done below
                Log.d("Insert: ", "Inserting ..");
                db.addUser(new User(
                        name,
                        phone,
                        email,
                        pass
                ));

                Intent i = new Intent(CreateAccount.this, MainActivity.class);
                startActivity(i);
            } else {
                Toast.makeText(getApplicationContext(), "Password did not match. Please try again.", Toast.LENGTH_LONG).show();
            }
    }

    /**
     * 4/15/17 - Michael created
     * Used to check if a user already exists in the database
     *
     * DOESNT CURRENTLY WORK
     *
     * @param email - email to check if already exists within table
     * @return TRUE if another user already has that email. FALSE if not
     */
    /*
    public boolean userExists(String email, SQLiteDatabase sqldb){
        String query = "SELECT * FROM TABLE_USERS WHERE KEY_USER_EMAIL = " + email;
        Cursor cursor = sqldb.rawQuery(query, null);
        if(cursor.getCount() <= 0){
            cursor.close();
            return false;
        }
        cursor.close();
        return true;
    }
    */
}
