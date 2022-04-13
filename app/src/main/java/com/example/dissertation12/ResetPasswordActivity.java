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

public class ResetPasswordActivity extends AppCompatActivity
{
    //Widget variables
    EditText password, repassword;
    Button signup, signin, btnreset;

    //Store user ID from shared preferences
    int spUserID;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        //Applying widgets to variables
        password = findViewById(R.id.password);
        repassword =  findViewById(R.id.repassword);
        signup =  findViewById(R.id.btnsignup);
        signin =  findViewById(R.id.btnsignin);
        btnreset = findViewById(R.id.btnreset);

        //button click to reset password
        resetUser();

        //button click navigates to LoginActivity.class
        signin.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
            }
        });

        //button click navigates to MainActivity.class
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

    private void resetUser()
    {
        //DBHelper called
        DBHelper MyDB;
        MyDB = new DBHelper(this);

        //Button reset when clicked
        btnreset.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                //Converts input fields to string
                String pass = password.getText().toString();
                String repass = repassword.getText().toString();

                //If either field is left empty
                if(pass.equals("")||repass.equals(""))
                {
                    Toast.makeText(ResetPasswordActivity.this, "Please enter all the fields", Toast.LENGTH_SHORT).show();
                }
                //If password is not 8 characters long
                else if (pass.length() < 8)
                {
                    Toast.makeText(ResetPasswordActivity.this, "Password must have at least 8 characters", Toast.LENGTH_SHORT).show();
                }
                //If passwords match
                else if (pass.equals(repass))
                {
                    //Get user ID from shared preferences
                    SharedPreferences getUser = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                    spUserID = getUser.getInt("id", 0);

                    //Update the user's password
                    boolean updateUserPassword = MyDB.updateUserInfo(spUserID, pass);

                    //If update is successful
                    if (updateUserPassword == true)
                    {
                        //Navigate to HomeActivity.class
                        Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                        startActivity(intent);

                        Toast.makeText(ResetPasswordActivity.this, "Updated successfully", Toast.LENGTH_SHORT).show();
                    }
                    //Update failed for an unknown reason
                    else
                    {
                        Toast.makeText(ResetPasswordActivity.this, "Update failed!", Toast.LENGTH_SHORT).show();
                    }
                }
                //If passwords are not matching
                else
                {
                    Toast.makeText(ResetPasswordActivity.this, "Passwords not matching", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}