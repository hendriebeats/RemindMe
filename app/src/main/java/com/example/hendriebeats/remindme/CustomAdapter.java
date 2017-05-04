package com.example.hendriebeats.remindme;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class CustomAdapter extends BaseAdapter{

    ArrayList<String> TaskTitleList, PlaceTitleList;
    Context context;
    int [] imageId;
    DatabaseHandler db;
    ArrayList<Task> TaskList;

    private static LayoutInflater inflater=null;
    public CustomAdapter(Context context, ArrayList<Task> TaskList, ArrayList<String> TaskTitleList, ArrayList<String> PlaceTitleList, int[] prgmImages) {
        this.TaskList = TaskList;
        this.TaskTitleList = TaskTitleList;
        this.PlaceTitleList = PlaceTitleList;
        this.context=context;
        db = new DatabaseHandler(context);
        imageId = prgmImages;
        inflater = ( LayoutInflater )context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        return TaskTitleList.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public class Holder
    {
        TextView titleTxt, locationTitleTxt;
        ImageView img;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        int isChecked = (TaskList.get(position).isComplete()) ? 1 : 0;

        Holder holder = new Holder();
        View rowView;
        rowView = inflater.inflate(R.layout.listview_layout, null);
        holder.titleTxt=(TextView) rowView.findViewById(R.id.titleTxt);
        holder.locationTitleTxt=(TextView) rowView.findViewById(R.id.locationTitleTxt);
        holder.img=(ImageView) rowView.findViewById(R.id.imageView1);

        holder.titleTxt.setText(TaskTitleList.get(position));
        holder.locationTitleTxt.setText(PlaceTitleList.get(position));
        holder.img.setImageResource(imageId[isChecked]);
        return rowView;
    }
}
