package com.example.bhuvaneshvar.sqlitedemo;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EditData extends AppCompatActivity
{
    EditText etNameupdate  , etEmailupdate, etMobileupdate;
    Button btnUpdate;
    WorkingWithDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_data);
        etNameupdate = (EditText)findViewById(R.id.etNameUp);
        etEmailupdate = (EditText)findViewById(R.id.etEmailUp);
        etMobileupdate = (EditText)findViewById(R.id.etMobUp);
        btnUpdate = (Button)findViewById(R.id.btnUpdate);

        Intent theSenderIntentInfo = getIntent();

        final String rollNumberToEdit = theSenderIntentInfo.getStringExtra("Rollnumber");
        System.out.println(rollNumberToEdit);

        database = new WorkingWithDatabase(this);
        database.open();
        final SQLiteDatabase sqLiteDatabase = database.getDataBaseInstance();


        final String qry = "SELECT * FROM STUDENT WHERE ROLLNUMBER = " + rollNumberToEdit;
        final Cursor cursor = sqLiteDatabase.rawQuery(qry,null);
        cursor.moveToFirst();
        if (cursor.getCount() <= 0)
        {
            Toast.makeText(this,"DATA NOT FOUND",Toast.LENGTH_SHORT).show();
        }
        else
        {
            final Data_is_here StudentData = new Data_is_here();
            StudentData.name = cursor.getString(cursor.getColumnIndex("NAME"));
            StudentData.Email = cursor.getString(cursor.getColumnIndex("EMAIL"));
            StudentData.Mobile = Long.parseLong(cursor.getString(cursor.getColumnIndex("MOBILE")));

            etNameupdate.setText(StudentData.name);
            etEmailupdate.setText(StudentData.Email);
            etMobileupdate.setText(String.valueOf(StudentData.Mobile));

            btnUpdate.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v) {
                    String updatedName = etNameupdate.getText().toString();
                    if (updatedName.isEmpty() || updatedName.length() < 5)
                    {
                        etNameupdate.setError("Name Please");
                        return;
                    }


                    String updatedEmail = etEmailupdate.getText().toString().trim();

                    boolean isemailcorect = isEmailValid(updatedEmail);
                    if (!isemailcorect)
                    {
                        etEmailupdate.setError("Email is not Correct");
                        return;
                    }

                    long updatedMobile = Long.parseLong(etMobileupdate.getText().toString());
                    if (String.valueOf(updatedMobile).length() < 10)
                    {
                        etMobileupdate.setError("Mobile number must have 10 digit");
                        return;
                    }



                   // String QERy = "UPDATE STUDENT SET NAME = " + updatedName + " ,"+ "ROLLNUMBER = "+ updatedRoll + ", "+"EMAIL = " + updatedEmail+", "+"MOBILE = "+", "+"WHERE ROLLNUMBER = "+rollNumberToEdit+" ;";

                  // Cursor cursor = sqLiteDatabase.rawQuery(QERy,null);
                    ContentValues contentValues = new ContentValues();
                    contentValues.put("NAME",updatedName);
                    contentValues.put("ROLLNUMBER",rollNumberToEdit);
                    contentValues.put("EMAIL",updatedEmail);
                    contentValues.put("MOBILE",updatedMobile);
                    database.updateDatabse(contentValues,rollNumberToEdit);

                    database.close();

                    etMobileupdate.setText("");
                    etEmailupdate.setText("");
                    etNameupdate.setText("");

                    cursor.close();



                }
            });



        }



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
