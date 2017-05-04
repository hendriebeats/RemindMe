package com.example.hendriebeats.remindme;

import android.content.Context;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

import static com.example.hendriebeats.remindme.R.id.task_listview;

public class TaskListActivity extends AppCompatActivity {

    public DatabaseHandler db;
    ArrayList<String> TaskTitleList, PlaceTitleList;
    ArrayList<Task> TaskList;
    ListView listView;
    Button searchBtn;
    EditText searchTxt;
    String currentUserId;
    public static int [] prgmImages = {R.drawable.unchecked,R.drawable.checked};;


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

        //Link Listview on the .xml document to this .java document
        listView = (ListView)findViewById(R.id.task_listview);
        searchBtn = (Button) findViewById(R.id.searchBtn);
        searchTxt = (EditText) findViewById(R.id.searchTxt);

        //Get Lasklists for the Listview
        TaskTitleList = new ArrayList<>(db.getAllTaskTitlesByUser(Integer.parseInt(currentUserId)));
        PlaceTitleList = new ArrayList<>(db.getAllTaskPlaceTitlesByUser(Integer.parseInt(currentUserId)));
        TaskList = new ArrayList<>(db.getAllTasksByUser(Integer.parseInt(currentUserId)));

        //Make Adapter to populate listView from TaskList
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, TaskTitleList);
        listView.setAdapter(new CustomAdapter(this, TaskList, TaskTitleList, PlaceTitleList, prgmImages));

        //Refreshing List View
        listView.invalidateViews();

        //Register onClickListener to handle click events on each item
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            // argument position gives the index of item which is clicked
            public void onItemClick(AdapterView<?> arg0, View v,int position, long arg3) {
                clickTask(TaskList.get(position));
            }
        });

        //SubmitBtn Action
        searchBtn.setOnClickListener(
                new View.OnClickListener(){public void onClick(View view) {
                    searchLocation(Integer.parseInt(currentUserId), searchTxt.getText().toString(), getApplicationContext());
                }});
    }

    public void clickTask(Task currentTask){
        //Move to the full task description
        Intent i = new Intent(TaskListActivity.this, FullTaskActivity.class);
        i.putExtra("currentTaskId", Integer.toString(currentTask.getId()));
        i.putExtra("currentUserId", currentUserId);
        startActivity(i);

    }

    public void searchLocation(int userID, String locationTxt, Context context) {

        int latestTaskId = db.getTasksByLocation(userID, locationTxt, context);

        if(latestTaskId != -1){
            //Move to the full task description
            Intent i = new Intent(TaskListActivity.this, FullTaskActivity.class);
            i.putExtra("currentTaskId", Integer.toString(latestTaskId));
            i.putExtra("currentUserId", currentUserId);
            startActivity(i);
        }


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
                i = new Intent(TaskListActivity.this, Login.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
