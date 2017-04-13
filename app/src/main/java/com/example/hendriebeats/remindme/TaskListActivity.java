package com.example.hendriebeats.remindme;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;

public class TaskListActivity extends AppCompatActivity {

    public DatabaseHandler db;
    ArrayList<Task> dataModels;
    ListView listView;
    private static TaskListViewAdapter adapter;

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
                Intent i=new Intent(TaskListActivity.this, AddTask.class);
                startActivity(i);
            }
        });

        db = new DatabaseHandler(this);
        dataModels= new ArrayList<>();
        listView=(ListView)findViewById(R.id.task_listview);

        db.addTask(new Task("CIT399 HW", "00:001:12-38:583", "Only helpful with Golshan", "-1,5"));
        db.addTask(new Task("CIT243 HW", "22:003:26-21:159", "Not Helpful", "2,5"));
        db.addTask(new Task("CIT382 HW", "11:002:48-15:274", "Do it!", "7,3"));

        dataModels = db.getAllTasks();

        dataModels.add(new Task("Apple Pie", "Android 1.0", "1","September 23, 2008"));
        dataModels.add(new Task("Banana Bread", "Android 1.1", "2","February 9, 2009"));

        adapter= new CustomAdapter(dataModels,getApplicationContext());

        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                DataModel dataModel= dataModels.get(position);

                Snackbar.make(view, dataModel.getName()+"\n"+dataModel.getType()+" API: "+dataModel.getVersion_number(), Snackbar.LENGTH_LONG)
                        .setAction("No action", null).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
