package com.example.hendriebeats.remindme;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import static com.example.hendriebeats.remindme.R.id.task_listview;

public class TaskListActivity extends AppCompatActivity {

    public DatabaseHandler db;
    ArrayList<String> TaskTitleList;
    ArrayList<Task> TaskList;
    ListView listView;
    String currentUserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_list_floating_button);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.addTaskBtn);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(TaskListActivity.this, AddTaskActivity.class);
                i.putExtra("currentUserId", currentUserId);
                startActivity(i);
            }
        });
        //Initiate database handler to interface with the database
        db = new DatabaseHandler(this);

        //Get Current User ID from Previous Activity
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            currentUserId = extras.getString("currentUserId");
        }
        //Test if User id is correct
        //Toast.makeText(getApplicationContext(), currentUserId, Toast.LENGTH_LONG).show();

        //Used to initially populate Tasks
        /*db.addTask(new Task("CIT399 HW", "00:001:12-38:583", "Only helpful with Golshan", "-1,5", Integer.parseInt(currentUserId)));
        db.addTask(new Task("CIT243 HW", "22:003:26-21:159", "Not Helpful", "2,5", Integer.parseInt(currentUserId)));
        db.addTask(new Task("CIT382 HW", "11:002:48-15:274", "Do it!", "7,3", Integer.parseInt(currentUserId)));*/

        //Link Listview on the .xml document to this .java document
        listView =(ListView)findViewById(task_listview);

        //Populate TaskList with all the current database task titles
        TaskTitleList = new ArrayList<>(db.getAllTaskTitlesByUser(Integer.parseInt(currentUserId)));

        //Return all Tasks
        TaskList = new ArrayList<>(db.getAllTasksByUser(Integer.parseInt(currentUserId)));

        //Make Adapter to populate listView from TaskList
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, TaskTitleList);
        listView.setAdapter(arrayAdapter);

        //Register onClickListener to handle click events on each item
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            // argument position gives the index of item which is clicked
            public void onItemClick(AdapterView<?> arg0, View v,int position, long arg3) {
                clickTask(TaskList.get(position));
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_add_task, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        Intent i;
        switch (item.getItemId()) {
            case R.id.action_update_account:

                //Move to the full task description
                i = new Intent(TaskListActivity.this, UpdateAccountActivity.class);
                i.putExtra("currentUserId", currentUserId);
                startActivity(i);
                return true;

            case R.id.action_logout:
                i = new Intent(TaskListActivity.this, MainActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void clickTask(Task currentTask){
        //Move to the full task description
        Intent i = new Intent(TaskListActivity.this, FullTaskActivity.class);
        i.putExtra("currentTaskId", Integer.toString(currentTask.getId()));
        startActivity(i);
    }
}
