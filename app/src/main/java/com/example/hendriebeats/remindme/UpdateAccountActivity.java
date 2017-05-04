package com.example.hendriebeats.remindme;

import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.example.hendriebeats.remindme.Password.getNextSalt;
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

    //Used to hide keyboard when pressed outside of an EditText field
    public FrameLayout touchInterceptor;

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

        touchInterceptor = (FrameLayout) findViewById(R.id.touchInterceptorUpdateAccount);
        touchInterceptor.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                hideKeyboard(v, event);
                return false;
            }
        });
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

        // validate
        if (ifDifferentUserExists(email)) { //Break
        } else if(!validateName(name)) { //Break
        } else if(!validatePhone(phone)) { //Break
        } else if(!validateEmail(email)) { //Break
        }  else if(!pass.equals(confirmPass)){
            Toast.makeText(getApplicationContext(), "Passwords do not match.", Toast.LENGTH_LONG).show();
        } else if(!validatePassword(pass)) {
            //Break
        } else {
            // The input is validated!
            String salt = getNextSalt();
            db.addUser(new User(name, formatPhone(phone), email, hashPassword(pass+salt), salt));

            Intent i = new Intent(UpdateAccountActivity.this, Login.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
            Toast.makeText(getApplicationContext(), "Account Created Successfully", Toast.LENGTH_LONG).show();
        }
    }

    public void hideKeyboard(View v, MotionEvent event){
        //Check through all EditText fields to see if one is selected
        if(event.getAction() == MotionEvent.ACTION_DOWN) {
            //NAME TEXT
            if (nameTxt.isFocused()) {
                Rect outRect = new Rect();
                nameTxt.getGlobalVisibleRect(outRect);
                if (!outRect.contains((int) event.getRawX(), (int) event.getRawY())) {
                    nameTxt.clearFocus();
                    InputMethodManager imm = (InputMethodManager)
                            v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
            //PHONE TEXT
            else if (phoneTxt.isFocused()) {
                Rect outRect = new Rect();
                phoneTxt.getGlobalVisibleRect(outRect);
                if (!outRect.contains((int) event.getRawX(), (int) event.getRawY())) {
                    phoneTxt.clearFocus();
                    InputMethodManager imm = (InputMethodManager)
                            v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
            //EMAIL TEXT
            else if (emailTxt.isFocused()) {
                Rect outRect = new Rect();
                emailTxt.getGlobalVisibleRect(outRect);
                if (!outRect.contains((int) event.getRawX(), (int) event.getRawY())) {
                    emailTxt.clearFocus();
                    InputMethodManager imm = (InputMethodManager)
                            v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
            //PASSWORD TEXT
            else if (passwordTxt.isFocused()) {
                Rect outRect = new Rect();
                passwordTxt.getGlobalVisibleRect(outRect);
                if (!outRect.contains((int) event.getRawX(), (int) event.getRawY())) {
                    passwordTxt.clearFocus();
                    InputMethodManager imm = (InputMethodManager)
                            v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
            //CONFIRM PASSWORD TEXT
            else if (confirmPasswordTxt.isFocused()) {
                Rect outRect = new Rect();
                confirmPasswordTxt.getGlobalVisibleRect(outRect);
                if (!outRect.contains((int) event.getRawX(), (int) event.getRawY())) {
                    confirmPasswordTxt.clearFocus();
                    InputMethodManager imm = (InputMethodManager)
                            v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
        }
    }


    public boolean validateName(String name){
        if(name.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Please enter your Name.", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    public boolean ifDifferentUserExists(String email){

        try{
            if(db.getUserById(Integer.parseInt(currentUserId)).getEmail().equals(email))
                return false;

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
                Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
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
