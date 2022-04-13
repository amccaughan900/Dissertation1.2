package com.example.dissertation12;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ResetActivity extends AppCompatActivity
{
    //Widget variables
    EditText username, editTextsecretAnswer;
    Button signup, signin, btnreset;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset);

        //Applies widgets to variables
        username =  findViewById(R.id.username);
        editTextsecretAnswer = findViewById(R.id.secretAnswer);
        btnreset = findViewById(R.id.btnreset);
        signup =  findViewById(R.id.btnsignup);
        signin =  findViewById(R.id.btnsignin);

        //Method called to check if user is allowed to reset their password
        resetPassed();

        //Navigation button to LoginActivity.class
        signin.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
            }
        });

        //Navigation button to MainActivity.class
        signup.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private void resetPassed()
    {
        //DBHelper called
        DBHelper MyDB;
        MyDB = new DBHelper(this);

        //When button reset is clicked
        btnreset.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                //Convert input fields into string
                String user = username.getText().toString();
                String secretAnswer = editTextsecretAnswer.getText().toString();

                //If any fields are left blank
                if(user.equals("")||secretAnswer.equals(""))
                {
                    Toast.makeText(ResetActivity.this, "Please enter all the fields", Toast.LENGTH_SHORT).show();
                }

                else
                {
                    //Checks if username and secret answer exists in the database
                    Boolean checkUserSecretAnswer = MyDB.checkResetAllowed(user, secretAnswer);

                    //If it does exist
                    if(checkUserSecretAnswer == true)
                    {
                        //Get user ID
                        int thisUserID = MyDB.getUserID(user);

                        Toast.makeText(ResetActivity.this,"Reset check passed", Toast.LENGTH_SHORT).show();

                        //Navigate to ResetPasswordActivity.class
                        Intent intent = new Intent(getApplicationContext(), ResetPasswordActivity.class);
                        startActivity(intent);

                        //Save user ID in shared preferences
                        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                        SharedPreferences.Editor editor = sharedPreferences.edit();

                        editor.putInt("id", thisUserID);
                        editor.apply();

                    }
                    //If user has enter the wrong user name or secret answer
                    else
                    {
                        Toast.makeText(ResetActivity.this, "Incorrect username or secret answer", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}