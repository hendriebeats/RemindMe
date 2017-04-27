package com.example.hendriebeats.remindme;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import static com.example.hendriebeats.remindme.Password.checkPassword;
import static com.example.hendriebeats.remindme.Password.hashPassword;

public class MainActivity extends AppCompatActivity {

    public EditText emailTxt;
    public EditText passwordTxt;
    public Button submitBtn;
    public Button createAccountBtn;
    public DatabaseHandler db;
    private static int workload = 12;

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
        createAccountBtn = (Button)findViewById(R.id.createAccountBtn);



        //Hardcoded add user for testing
        //db.addUser(new User("James Hendrie","(717)778-7389","a","a"));

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

        try{
            User validate = db.getUserByEmail(emailTxt.getText().toString());

            if(checkPassword(passwordTxt.getText().toString(), validate.getPassword())){
                emailTxt.setText("");
                passwordTxt.setText("");

                Intent i=new Intent(MainActivity.this, TaskListActivity.class);
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
        Intent i=new Intent(MainActivity.this, CreateAccount.class);
        startActivity(i);
    }
}
