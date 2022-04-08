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

    EditText username, password;
    Button btnlogin, btnsignup, btnreset;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username = (EditText) findViewById(R.id.username1);
        password = (EditText) findViewById(R.id.password1);
        btnlogin = (Button) findViewById(R.id.btnsignin1);
        btnsignup = findViewById(R.id.btnsignup);
        btnreset = findViewById(R.id.btnreset);

        clickBtnLogin();

        btnsignup.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });

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

    private void clickBtnLogin()
    {
        DBHelper MyDB;
        MyDB = new DBHelper(this);

        btnlogin.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                String user = username.getText().toString();
                String pass = password.getText().toString();

                if(user.equals("")||pass.equals(""))
                {
                    Toast.makeText(LoginActivity.this, "Please enter all the fields", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Boolean checkuserpass = MyDB.checkusernamepassword(user, pass);

                    if(checkuserpass==true)
                    {
                        int thisUserID = MyDB.getUserID(user);

                        Toast.makeText(LoginActivity.this,user + " signed in successfully", Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                        startActivity(intent);


                        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                        SharedPreferences.Editor editor = sharedPreferences.edit();

                        editor.putInt("id", thisUserID);
                        editor.apply();

                    }
                    else
                    {
                        Toast.makeText(LoginActivity.this, "Incorrect username or password", Toast.LENGTH_SHORT).show();
                    }

                }

            }
        });
    }
}