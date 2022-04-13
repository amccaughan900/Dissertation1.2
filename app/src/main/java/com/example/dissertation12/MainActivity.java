package com.example.dissertation12;

import androidx.appcompat.app.ActionBar;
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

public class MainActivity extends AppCompatActivity
{
    //Widget variables
    EditText username, password, repassword, editTextsecretAnswer;
    Button signup, signin;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        //References layout design for this screen
        setContentView(R.layout.activity_main);

        //Applies widgets to variables
        username =  findViewById(R.id.username);
        password =  findViewById(R.id.password);
        repassword =  findViewById(R.id.repassword);
        editTextsecretAnswer = findViewById(R.id.secretAnswer);
        signup =  findViewById(R.id.btnsignup);
        signin =  findViewById(R.id.btnsignin);

        //Method to register a user
        registerUser();

        //Navigation to LoginActivity.class
        signin.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
            }
        });
    }

    //Method to register a user
    private void registerUser()
    {
        //Calls DNHelper
        DBHelper MyDB;
        MyDB = new DBHelper(this);

        //When user clicks sign up button
        signup.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                //Converts user input into string
                String user = username.getText().toString();
                String pass = password.getText().toString();
                String repass = repassword.getText().toString();
                String secretAnswer = editTextsecretAnswer.getText().toString();

                //Clears all memory on shared preference file
                SharedPreferences removeUser = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                SharedPreferences.Editor deleteCurrentUser = removeUser.edit();
                deleteCurrentUser.clear();
                deleteCurrentUser.commit();

                //If user doesn't enter all fields, prevent them from signing up
                if(user.equals("")||pass.equals("")||repass.equals("")||secretAnswer.equals(""))
                {
                    Toast.makeText(MainActivity.this, "Please enter all the fields", Toast.LENGTH_SHORT).show();
                }
                //If password isn't 8 characters long
                else if (pass.length() < 8)
                {
                    Toast.makeText(MainActivity.this, "Password must have at least 8 characters", Toast.LENGTH_SHORT).show();
                }
                //If both password fields match
                else if (pass.equals(repass))
                {
                    //Check if the username already exists
                    Boolean checkUserExists = MyDB.checkUsername(user);
                    //If username doesn't exist
                    if(checkUserExists==false)
                    {
                        //Insert user information into USER_DATA table
                        boolean insertUserInfo = MyDB.insertUserData(user, pass, secretAnswer);

                        //If correctly inserted
                        if (insertUserInfo == true)
                        {
                            //Get userID by using username
                            int thisUserID = MyDB.getUserID(user);

                            //Insert an initial hint coin for new users
                            MyDB.insertHintCoin(thisUserID);

                            //Navigate to HomeActivity
                            Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                            startActivity(intent);

                            //Save user ID for usage throughout app.
                            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putInt("id", thisUserID);
                            editor.apply();

                            Toast.makeText(MainActivity.this, user + " registered successfully" + System.lineSeparator() + "1 Hint coin for joining", Toast.LENGTH_LONG).show();
                        }
                        //An error has occurred with registering
                        else
                        {
                            Toast.makeText(MainActivity.this, "Registration failed, there seems to be an issue on our behalf", Toast.LENGTH_SHORT).show();
                        }
                    }
                    //If usernames already exists in the user table
                    else
                    {
                        Toast.makeText(MainActivity.this, "User already exists!", Toast.LENGTH_SHORT).show();
                    }
                }
                //If passwords are not matching
                else
                {
                    Toast.makeText(MainActivity.this, "Passwords not matching", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}