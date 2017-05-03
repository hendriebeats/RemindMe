package com.example.hendriebeats.remindme;

import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import static com.example.hendriebeats.remindme.Password.checkPassword;
import static com.example.hendriebeats.remindme.Password.hashPassword;

public class Login extends AppCompatActivity {

    public EditText emailTxt;
    public EditText passwordTxt;
    public Button submitBtn;
    //public Button createAccountBtn;
    public TextView createAccountTxtView;
    public DatabaseHandler db;
    private static int workload = 12;

    //Used to hide keyboard when pressed outside of an EditText field
    public FrameLayout touchInterceptor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new DatabaseHandler(this);

        try{
            if(db.getUserById(1).getName().equals(""));
        } catch(Exception e) {
            db.addUser(new User("James Hendrie", "(123) 456-7890", "a", hashPassword("a")));
        }

        emailTxt = (EditText)findViewById(R.id.emailTxt);
        passwordTxt = (EditText)findViewById(R.id.passwordTxt);
        submitBtn = (Button)findViewById(R.id.submitBtn);
        createAccountTxtView = (TextView) findViewById(R.id.textViewCreateAccount);
        createAccountTxtView.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                createAccount();
            }
        });

        touchInterceptor = (FrameLayout) findViewById(R.id.touchInterceptorMain);


        //SubmitBtn Action
        submitBtn.setOnClickListener(
                new View.OnClickListener(){public void onClick(View view) {
                    submit();
                }});

        //hide keyboard for touchInterceptor Action
        touchInterceptor.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                hideKeyboard(v, event);
                return false;
            }
        });
    }
    //Test email/pass and act appropriately
    public void submit(){

        try{
            User validate = db.getUserByEmail(emailTxt.getText().toString());

            if(checkPassword(passwordTxt.getText().toString(), validate.getPassword())){
                emailTxt.setText("");
                passwordTxt.setText("");

                Intent i=new Intent(Login.this, TaskListActivity.class);
                i.putExtra("currentUserId", Integer.toString(validate.getId()));
                startActivity(i);
            } else {
                Toast.makeText(getApplicationContext(), "Error, you entered the wrong email or password", Toast.LENGTH_SHORT).show();
            }
        }catch (Exception e){
            Toast.makeText(getApplicationContext(), "An account does not exist with that email.", Toast.LENGTH_SHORT).show();
        }
    }

    //Moves to the create account page
    public void createAccount(){
        //Move to the create Account Page
        Intent i=new Intent(Login.this, CreateAccount.class);
        startActivity(i);
    }

    //Hides the keyboard for touchInterceptor
    public void hideKeyboard(View v, MotionEvent event){
        //Check through all EditText fields to see if one is selected
        if(event.getAction() == MotionEvent.ACTION_DOWN) {
            //EMAIL TEXT
            if (emailTxt.isFocused()) {
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
        }
    }
}
