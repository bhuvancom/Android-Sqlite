package com.example.bhuvaneshvar.sqlitedemo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomAdapter extends BaseAdapter
{
    private Context mcontext;
    ArrayList<Data_is_here> mstudentData;

    public CustomAdapter(Context context, ArrayList<Data_is_here> studentData)
    {
        super();
        mcontext = context;
        mstudentData = studentData;
    }

    @Override
    public int getCount() {
        return mstudentData.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        LayoutInflater layoutInflater  = (LayoutInflater)mcontext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = layoutInflater.inflate(R.layout.listview_each_item,null);

        Data_is_here studata = mstudentData.get(position);

        //fetching data
        String nameData = studata.name;
        long rollData = studata.rollNumber;
        String BranchData  = studata.Email;
        long SemesterData = studata.Mobile;


    // getting reference to show data
        TextView Name = (TextView)convertView.findViewById(R.id.tvName);
        TextView Roll = (TextView)convertView.findViewById(R.id.tvRollnumber);
        TextView Branch = (TextView)convertView.findViewById(R.id.tvBranch);
        TextView Semester = (TextView)convertView.findViewById(R.id.tvSemester);

        //setting data to respective view

        Name.setText(nameData);
        Roll.setText(String.valueOf(rollData));
        Branch.setText(BranchData);
        Semester.setText(String.valueOf(SemesterData));


        return convertView;
    }
}
