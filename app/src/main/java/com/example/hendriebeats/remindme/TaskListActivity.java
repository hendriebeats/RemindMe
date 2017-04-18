package com.example.hendriebeats.remindme;

import android.content.Intent;
import android.content.SyncStatusObserver;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import static com.example.hendriebeats.remindme.R.id.task_listview;

public class TaskListActivity extends AppCompatActivity {

    public DatabaseHandler db;
    ArrayList<String> TaskTitleList;
    ArrayList<Task> TaskList;
    ListView listView;

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
                Intent i=new Intent(TaskListActivity.this, AddTaskActivity.class);
                startActivity(i);
            }
        });
        //Initiate database handler to interface with the database
        db = new DatabaseHandler(this);

        //Used to initially populate Tasks
        /*db.addTask(new Task("CIT399 HW", "00:001:12-38:583", "Only helpful with Golshan", "-1,5"));
        db.addTask(new Task("CIT243 HW", "22:003:26-21:159", "Not Helpful", "2,5"));
        db.addTask(new Task("CIT382 HW", "11:002:48-15:274", "Do it!", "7,3"));*/

        //Link Listview on the .xml document to this .java document
        listView =(ListView)findViewById(task_listview);

        //Populate TaskList with all the current database task titles
        TaskTitleList = new ArrayList<>(db.getAllTaskTitles());

        //Return all Tasks
        TaskList = new ArrayList<>(db.getAllTasks());

        //Make Adapter to populate listView from TaskList
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, TaskTitleList);
        listView.setAdapter(arrayAdapter);

        //Register onClickListener to handle click events on each item
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            // argument position gives the index of item which is clicked
            public void onItemClick(AdapterView<?> arg0, View v,int position, long arg3) {
                clickTask(TaskList.get(position));
                Toast.makeText(getApplicationContext(), "Title : " + TaskTitleList.get(position),   Toast.LENGTH_LONG).show();
            }
        });
    }

    public void clickTask(Task currentTask){
        //Move to the full task description
        Intent i = new Intent(TaskListActivity.this, FullTaskActivity.class);
        i.putExtra("currentTaskId", Integer.toString(currentTask.getId()));
        startActivity(i);
    }
}
