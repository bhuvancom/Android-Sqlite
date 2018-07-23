package com.example.bhuvaneshvar.sqlitedemo;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.LightingColorFilter;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class GetAllData extends AppCompatActivity
{
    ListView lvDATAS;
    String RollNumber;
    WorkingWithDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_all_data);
        lvDATAS = (ListView)findViewById(R.id.lvData);
        database = new WorkingWithDatabase(this);
        database.open();
        Cursor cur =database.getAllFromDataBase();
        final ArrayList<Data_is_here> studentData = new ArrayList<Data_is_here>();

        while (cur.moveToNext())//for each data fetch data and store in object of DATA_IS HERE
        {
            Data_is_here students = new Data_is_here();
            students.name = cur.getString(cur.getColumnIndex("NAME"));
            students.rollNumber = cur.getLong(cur.getColumnIndex("ROLLNUMBER"));
            students.Email = cur.getString(cur.getColumnIndex("EMAIL"));
            students.Mobile = cur.getLong(cur.getColumnIndex("MOBILE"));
            studentData.add(students);
        }

        CustomAdapter customAdapter = new CustomAdapter(GetAllData.this,studentData);
        customAdapter.notifyDataSetChanged();
        lvDATAS.setAdapter(customAdapter);

        lvDATAS.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                TextView tvName = (TextView)view.findViewById(R.id.tvName);
                TextView tvRoll = (TextView)view.findViewById(R.id.tvRollnumber);
                TextView tvBranch = (TextView)view.findViewById(R.id.tvBranch);
                TextView tvSemester = (TextView)view.findViewById(R.id.tvSemester);

                String Name = tvName.getText().toString();
                RollNumber = tvRoll.getText().toString();
                String Branch = tvBranch.getText().toString();
                String Semester = tvSemester.getText().toString();

                AlertDialog.Builder builder = new AlertDialog.Builder(GetAllData.this,R.style.Diolog);
                builder.setTitle("Info");
                builder.setMessage(Name+"\n"+RollNumber+"\n"+Branch+"\n"+Semester);
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        dialog.dismiss();

                    }
                });
                builder.show();
                System.out.println("rolllllllll in onclick "+RollNumber);
        }
        });

        System.out.println("rolllllllll "+RollNumber);

        lvDATAS.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener()
        {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id)
            {
                System.out.println("rolllllllll in long "+RollNumber);
                TextView tvRoll = (TextView)view.findViewById(R.id.tvRollnumber);
                final String rollInLong = tvRoll.getText().toString();
                //TODO make deletable
                AlertDialog.Builder alertTodelete = new AlertDialog.Builder(GetAllData.this);
                alertTodelete.setTitle("DELETE THIS DATA ?").setMessage("CLICKING OK WILL DELETE THIS DATA").setCancelable(false)
                        .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which)
                            {
                                //GetAllData allData =new  GetAllData();
                                //allData.open();
                                String query ="ROLLNUMBER = ?";
                                System.out.println("ROll Number is "+rollInLong);
                                database.delete(new String[]{rollInLong});
                                database.close();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).setNeutralButton("Edit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent gotoEdit = new Intent(GetAllData.this,EditData.class);
                        gotoEdit.putExtra("Rollnumber",rollInLong);
                        startActivity(gotoEdit);

                    }
                })
                        .show();
                return true;
            }
        });

    }

    @Override
    public void onBackPressed() {
        database.close();
        super.onBackPressed();
    }
}
