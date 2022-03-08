package com.example.dissertation12;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class HomeActivity extends AppCompatActivity
{

    Button solvePuzzles, completePuzzles, howToPlay, logout;
    TextView userID, sp;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        solvePuzzles = (Button) findViewById(R.id.btnsolvepuzzles);
        completePuzzles = (Button) findViewById(R.id.btncompletedpuzzles);
        howToPlay = (Button) findViewById(R.id.btnhowtoplay);
        logout = (Button) findViewById(R.id.btnlogout);
        userID = (TextView) findViewById(R.id.textUserID);
        sp = (TextView) findViewById(R.id.textViewSharedPreferences);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
//        SharedPreferences.Editor editor = sharedPreferences.edit();

        // int userid = 1;
        // editor.putInt("id", userid);
        // editor.putString("Username", "Conor");
        // editor.apply();

        String spUsername = sharedPreferences.getString("Username", "No Username Found");
        int spUserID = sharedPreferences.getInt("id", 0);
        String txtSpUserID = String.valueOf(spUserID);
        userID.setText(txtSpUserID);
        sp.setText(spUsername);
//        Bundle extras = getIntent().getExtras();
//        if(extras!=null)
//        {
//            userID.setText("User ID is "+extras.getString("UserName"));
//        }

        solvePuzzles.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent toRegion = new Intent(getApplicationContext(), LocationActivity.class);
                startActivity(toRegion);
            }
        });

        completePuzzles.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(getApplicationContext(), puzzlesSolved.class);
                startActivity(intent);
            }
        });
    }
}