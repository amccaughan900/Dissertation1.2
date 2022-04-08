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

    EditText password, repassword;
    Button signup, signin, btnreset;
    int spUserID;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        password = (EditText) findViewById(R.id.password);
        repassword = (EditText) findViewById(R.id.repassword);
        signup = (Button) findViewById(R.id.btnsignup);
        signin = (Button) findViewById(R.id.btnsignin);
        btnreset = findViewById(R.id.btnreset);

        resetUser();

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

    private void resetUser()
    {
        DBHelper MyDB;
        MyDB = new DBHelper(this);

        btnreset.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                String pass = password.getText().toString();
                String repass = repassword.getText().toString();

                if(pass.equals("")||repass.equals(""))
                {
                    Toast.makeText(ResetPasswordActivity.this, "Please enter all the fields", Toast.LENGTH_SHORT).show();
                }
                else if (pass.length() < 8)
                {
                    Toast.makeText(ResetPasswordActivity.this, "Password must have at least 8 characters", Toast.LENGTH_SHORT).show();
                }
                else if (pass.equals(repass))
                {
                    SharedPreferences getUser = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                    spUserID = getUser.getInt("id", 0);

                    boolean updateUserPassword = MyDB.updateUserInfo(spUserID, pass);

                    if (updateUserPassword == true)
                    {
                        Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                        startActivity(intent);

                        Toast.makeText(ResetPasswordActivity.this, "Updated successfully", Toast.LENGTH_SHORT).show();
                    }

                    else
                    {
                        Toast.makeText(ResetPasswordActivity.this, "Update failed!", Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    Toast.makeText(ResetPasswordActivity.this, "Passwords not matching", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}