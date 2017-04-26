package com.example.hendriebeats.remindme;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import static com.example.hendriebeats.remindme.Password.hashPassword;

public class UpdateAccountActivity extends AppCompatActivity {

    public EditText nameTxt;
    public EditText phoneTxt;
    public EditText emailTxt;
    public EditText passwordTxt;
    public EditText confirmPasswordTxt;
    public Button updateBtn;
    public DatabaseHandler db;
    User validate;
    String currentUserId;
    User currentUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_account);

        db = new DatabaseHandler(this);

        //Get Current User ID from Previous Activity
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            currentUserId = extras.getString("currentUserId");
        }

        currentUser = db.getUserById(Integer.parseInt(currentUserId));

        //connect fields and populate with proper user info
        nameTxt = (EditText)findViewById(R.id.nameTxt);
        nameTxt.setText(currentUser.getName());

        phoneTxt = (EditText)findViewById(R.id.phoneTxt);
        phoneTxt.setText(currentUser.getPhoneNumber());

        emailTxt = (EditText)findViewById(R.id.emailTxt);
        emailTxt.setText(currentUser.getEmail());

        //Connect password fields from XML document
        passwordTxt = (EditText)findViewById(R.id.passwordTxt);
        confirmPasswordTxt = (EditText)findViewById(R.id.confirmPasswordTxt);

        updateBtn = (Button)findViewById(R.id.updateBtn);



        //SubmitBtn Action
        updateBtn.setOnClickListener(
                new View.OnClickListener(){public void onClick(View view) {
                    update(currentUser.getEmail());
                }});
    }

    // seems to work
    public void update(String userEmail){
        db = new DatabaseHandler(this);

        User user = db.getUserByEmail(userEmail);

        // adding these variables because they are used in multiple locations below
        String name = nameTxt.getText().toString().trim();
        String phone = phoneTxt.getText().toString().trim();
        String email = emailTxt.getText().toString().trim();
        String pass = passwordTxt.getText().toString().trim();
        String confirmPass = confirmPasswordTxt.getText().toString().trim();

            if (email.isEmpty()) {
                Toast.makeText(getApplicationContext(), "Please enter an email and password.", Toast.LENGTH_LONG).show();
            } else if (pass.equals(confirmPass)) {

                user.setName(name);
                user.setPhoneNumber(phone);
                user.setEmail(email);
                if(!pass.isEmpty())
                    user.setPassword(hashPassword(pass));

                Log.d("Update: ", "Updating ..");
                db.updateUser(user);

                //Clear values if create account is successful
                nameTxt.setText("");
                phoneTxt.setText("");
                emailTxt.setText("");
                passwordTxt.setText("");
                confirmPasswordTxt.setText("");

                // Calling finish on activity instead of creating new
                finish();
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
