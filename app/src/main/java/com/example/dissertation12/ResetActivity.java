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

    EditText username, editTextsecretAnswer;
    Button signup, signin, btnreset;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset);

        username = (EditText) findViewById(R.id.username);
        editTextsecretAnswer = findViewById(R.id.secretAnswer);
        btnreset = findViewById(R.id.btnreset);
        signup = (Button) findViewById(R.id.btnsignup);
        signin = (Button) findViewById(R.id.btnsignin);

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

    private void registerUser()
    {
        DBHelper MyDB;
        MyDB = new DBHelper(this);

        btnreset.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                String user = username.getText().toString();
                String secretAnswer = editTextsecretAnswer.getText().toString();

                if(user.equals("")||secretAnswer.equals(""))
                {
                    Toast.makeText(ResetActivity.this, "Please enter all the fields", Toast.LENGTH_SHORT).show();
                }

                else
                {
                    Boolean checkUserSecretAnswer = MyDB.checkResetAllowed(user, secretAnswer);

                    if(checkUserSecretAnswer == true)
                    {
                        int thisUserID = MyDB.getUserID(user);

                        Toast.makeText(ResetActivity.this,"Reset check passed", Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(getApplicationContext(), ResetPasswordActivity.class);
                        startActivity(intent);


                        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                        SharedPreferences.Editor editor = sharedPreferences.edit();

                        editor.putInt("id", thisUserID);
                        editor.apply();

                    }
                    else
                    {
                        Toast.makeText(ResetActivity.this, "Incorrect username or secret answer", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}