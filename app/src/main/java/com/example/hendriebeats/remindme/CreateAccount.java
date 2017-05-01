package com.example.hendriebeats.remindme;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

        db = new DatabaseHandler(this);

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

        // grab info from the text fields
        String name = nameTxt.getText().toString();
        String phone = phoneTxt.getText().toString();
        String email = emailTxt.getText().toString();
        String pass = passwordTxt.getText().toString();
        String confirmPass = confirmPasswordTxt.getText().toString();

        // validate
        if (ifUserExists(email)) { //Break
        } else if(!validateName(name)) { //Break
        } else if(!validatePhone(phone)) { //Break
        } else if(!validateEmail(email)) { //Break
        }  else if(!pass.equals(confirmPass)){
            Toast.makeText(getApplicationContext(), "Passwords do not match.", Toast.LENGTH_LONG).show();
        } else if(!validatePassword(pass)) {
            //Break
        } else {
            // The input is validated!
            db.addUser(new User(name, formatPhone(phone), email, hashPassword(pass)));

            Intent i = new Intent(CreateAccount.this, Login.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
            Toast.makeText(getApplicationContext(), "Account Created Successfully", Toast.LENGTH_LONG).show();
        }
    }

    public boolean validateName(String name){
        if(name.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Please enter your Name.", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
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

    public boolean validatePhone(String phone){

        if(phone.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Please enter Phone Number.", Toast.LENGTH_LONG).show();
            return false;
        }

        //complicated regular expression that validates north american phones
        String regex = "^\\(?([0-9]{3})\\)?[-.\\s]?([0-9]{3})[-.\\s]?([0-9]{4})$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(phone);
        if(!matcher.matches()) {
            Toast.makeText(getApplicationContext(), "Please enter a valid Phone Number.", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    public boolean validateEmail(String email){

        if(email.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Please enter an Email.", Toast.LENGTH_LONG).show();
            return false;
        }

        //complicated regular expression that validates emails
        Pattern emailValidation =
                Pattern.compile("^[\\w!#$%&’*+/=?`{|}~^-]+(?:\\.[\\w!#$%&’*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
        Matcher matcher = emailValidation .matcher(email);
        if(!matcher.matches()) {
            Toast.makeText(getApplicationContext(), "Please enter a valid Email.", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    public boolean validatePassword(String password){

        if(password.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Please enter a password.", Toast.LENGTH_LONG).show();
            return false;
        }

        boolean hasUppercase = !password.equals(password.toLowerCase());
        boolean hasLowercase = !password.equals(password.toUpperCase());

        if(!hasUppercase) {
            Toast.makeText(getApplicationContext(), "Password must contain at least one uppercase character.", Toast.LENGTH_LONG).show();
            return false;
        }
        if(!hasLowercase) {
            Toast.makeText(getApplicationContext(), "Password must contain at least one lowercase character.", Toast.LENGTH_LONG).show();
            return false;
        }

        if(password.length() < 8) {
            Toast.makeText(getApplicationContext(), "Password must be at least 8 characters.", Toast.LENGTH_LONG).show();
            return false;
        }

        //Atleast one special character
        String regex = "^(?=[\\w!@#$%^&*()+]{6,})(?:.*[!@#$%^&*()+]+.*)$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(password);
        if(!matcher.matches()) {
            Toast.makeText(getApplicationContext(), "Password must contain at least one special character", Toast.LENGTH_LONG).show();
            return false;
        }

        //No white space in the entire string
        regex = "\\S+";
        pattern = Pattern.compile(regex);
        matcher = pattern.matcher(password);
        if(!matcher.matches()) {
            Toast.makeText(getApplicationContext(), "Password must not contain any white space", Toast.LENGTH_LONG).show();
            return false;
        }
        return true; //password is valid :)
    }

    public String formatPhone(String phone) {

        String regex = "^\\(?([0-9]{3})\\)?[-.\\s]?([0-9]{3})[-.\\s]?([0-9]{4})$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(phone);
        //Format it to (123)-456-7890
        return matcher.replaceFirst("($1) $2-$3");
    }
}
