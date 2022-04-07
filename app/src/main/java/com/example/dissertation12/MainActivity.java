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

public class MainActivity extends AppCompatActivity
{

    EditText username, password, repassword, editTextsecretAnswer;
    Button signup, signin;
    DBHelper MyDB;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        repassword = (EditText) findViewById(R.id.repassword);
        editTextsecretAnswer = findViewById(R.id.secretAnswer);
        signup = (Button) findViewById(R.id.btnsignup);
        signin = (Button) findViewById(R.id.btnsignin);

        MyDB = new DBHelper(this);

        registerUser();

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

    private void registerUser()
    {
        signup.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                String user = username.getText().toString();
                String pass = password.getText().toString();
                String repass = repassword.getText().toString();
                String secretAnswer = editTextsecretAnswer.getText().toString();

                if(user.equals("")||pass.equals("")||repass.equals("")||secretAnswer.equals(""))
                {
                    Toast.makeText(MainActivity.this, "Please enter all the fields", Toast.LENGTH_SHORT).show();
                }
                else if (pass.length() < 8)
                {
                    Toast.makeText(MainActivity.this, "Password must have at least 8 characters", Toast.LENGTH_SHORT).show();
                }
                else if (pass.equals(repass))
                {

                    Boolean checkUserExists = MyDB.checkUsername(user);

                    if(checkUserExists==false)
                    {
                        boolean insertUserInfo = MyDB.insertUserData(user, pass, secretAnswer);

                        if (insertUserInfo == true)
                        {
                            int thisUserID = MyDB.getUserID(user);

                            MyDB.insertHintCoin(thisUserID);

                            Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                            startActivity(intent);

                            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                            SharedPreferences.Editor editor = sharedPreferences.edit();

                            editor.putInt("id", thisUserID);
                            editor.apply();

                            Toast.makeText(MainActivity.this, user + " registered successfully" + System.lineSeparator() + "1 Hint coin for joining", Toast.LENGTH_LONG).show();
                        }
                        else
                        {
                            Toast.makeText(MainActivity.this, "Registration failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else
                    {
                        Toast.makeText(MainActivity.this, "User already exists!", Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    Toast.makeText(MainActivity.this, "Passwords not matching", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}