package com.example.dissertation12;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity
{

    //Widget variables
    EditText username, password;
    Button btnlogin, btnsignup, btnreset;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Applying widgets to variables
        username =  findViewById(R.id.username1);
        password =  findViewById(R.id.password1);
        btnlogin =  findViewById(R.id.btnsignin1);
        btnsignup = findViewById(R.id.btnsignup);
        btnreset = findViewById(R.id.btnreset);

        //Calls method to login
        clickBtnLogin();

        //Navigates user to MainActivity.class
        btnsignup.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        //Navigates user to ResetActivity.class
        btnreset.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(getApplicationContext(), ResetActivity.class);
                startActivity(intent);
            }
        });
    }

    //Method for logging in
    private void clickBtnLogin()
    {
        //Calls on DBHelper method
        DBHelper MyDB;
        MyDB = new DBHelper(this);

        //Login button on click listener
        btnlogin.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                //Getting user input into string
                String user = username.getText().toString();
                String pass = password.getText().toString();

                //Clears all memory on Shared Preferences . This is a fix to multiple users earning steps even when only one is logged in.
                SharedPreferences removeUser = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                SharedPreferences.Editor deleteCurrentUser = removeUser.edit();
                deleteCurrentUser.clear();
                deleteCurrentUser.commit();

                //If either input fields are empty, prevent the user from logging in.
                if(user.equals("")||pass.equals(""))
                {
                    Toast.makeText(LoginActivity.this, "Please enter all the fields", Toast.LENGTH_SHORT).show();
                }

                else
                {
                    //Check if username and password matches any records in USER_DATA table.
                    Boolean checkuserpass = MyDB.checkusernamepassword(user, pass);

                    //If true
                    if(checkuserpass==true)
                    {
                        //Retrieve user ID from user table through username
                        int thisUserID = MyDB.getUserID(user);

                        //pop up message
                        Toast.makeText(LoginActivity.this,user + " signed in successfully", Toast.LENGTH_SHORT).show();

                        //Saves user ID into shared preferences to be used elsewhere in the app
                        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                        SharedPreferences.Editor editor = sharedPreferences.edit();

                        editor.putInt("id", thisUserID);
                        editor.apply();

                        //Begin navigating to HomeActivity
                        Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                        startActivity(intent);
                    }
                    //If user entered in an invalid username or password
                    else
                    {
                        Toast.makeText(LoginActivity.this, "Incorrect username or password", Toast.LENGTH_SHORT).show();
                    }

                }

            }
        });
    }
}