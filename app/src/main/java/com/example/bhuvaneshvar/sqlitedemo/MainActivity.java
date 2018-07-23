package com.example.bhuvaneshvar.sqlitedemo;


import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity
{
    EditText etName;
    EditText etRollnumber;
    EditText etgetBranch;
    EditText etGetSemeseter;
    Button btnInsert;
    WorkingWithDatabase database;


    @Override
    public void onBackPressed()
    {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setTitle("DO you Really want to close app").setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).show();
    }



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnInsert = findViewById(R.id.btnSave);
        btnInsert.setOnClickListener(
                new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {

                etName = findViewById(R.id.etName);
                etRollnumber = findViewById(R.id.etRollnumber);
                etgetBranch = findViewById(R.id.etbranch);
                etGetSemeseter = findViewById(R.id.etsemester);


               String name = etName.getText().toString();
               String rollNum  = (etRollnumber.getText().toString());
               String branch = etgetBranch.getText().toString();
               String semeseter = etGetSemeseter.getText().toString();


                if (name.isEmpty() || rollNum.isEmpty() || branch.isEmpty() || semeseter.isEmpty())
                {
                    Toast.makeText(getApplicationContext(),"Enter data",Toast.LENGTH_LONG).show();
                }
                else
                {
                    boolean isvalid= isEmailValid(branch);
                    if (!isvalid)
                    {
                        etgetBranch.setError("Not a Valid Email");
                        return;
                    }

                    // creating db instance
                    database = new WorkingWithDatabase(MainActivity.this);
                    long rollNumber = Long.parseLong(rollNum);

                    if (String.valueOf(semeseter).length() < 10)
                    {
                        etGetSemeseter.setError("Mobile number must have 10 digit");
                        return;
                    }

                    if (String.valueOf(rollNumber).length() < 10)
                    {
                        etRollnumber.setError("Roll number must have 10 digit");
                        return;
                    }

                    boolean dataexist = checkifRollnumberExist("STUDENT",rollNumber);

                     if (dataexist)
                     {
                        etRollnumber.setError("Roll number already saved");
                         return;

                     }
                     else
                     {
                         //opening database to writable form
                         database.open();
                         //to insert data
                         // this will store data in it as hash-map to insert in  db
                        // Using DATA_IS HERE CLASS
                         // these data will be inserting in db

                         Data_is_here studentData = new Data_is_here();
                         studentData.name =name;
                         studentData.rollNumber = rollNumber;
                         studentData.Email = branch;
                         long Mobilenumber = Long.parseLong(semeseter);
                         studentData.Mobile = Mobilenumber;

                         // table name , null ,values to insert

                         database.insertIntry(studentData);

                         etRollnumber.setText("");
                         etGetSemeseter.setText("");
                         etName.setText("");
                         etgetBranch.setText("");

                         database.close();
                     }
                }


            }
        });



    }

    public boolean checkifRollnumberExist(String tablename, long roll)
    {
        // create instance of readable database
        database.open();
        SQLiteDatabase db =database.getDataBaseInstance();
        String query = "SELECT ROLLNUMBER FROM "+tablename+" WHERE ROLLNUMBER "+" = "+roll ;
        Cursor cursor = db.rawQuery(query,null);
        cursor.moveToFirst();

        cursor.close();//todo if error remove this line
        return cursor.getCount() > 0;

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.mymenu,menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int MenuId = item.getItemId();
        if (MenuId == R.id.menuGetall)
        {
            Intent gotoAll = new Intent(MainActivity.this, GetAllData.class);
            startActivity(gotoAll);
        }
        return super.onOptionsItemSelected(item);
    }

    public static boolean isEmailValid(String email) {
        boolean isValid = false;

        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        CharSequence inputStr = email;

        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);
        if (matcher.matches()) {
            isValid = true;
        }
        return isValid;
    }
}
