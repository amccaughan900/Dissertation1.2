package com.example.dissertation12;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;

public class LocationActivity extends AppCompatActivity
{

    //Button widget variables
    Button home, ballycastle, belfast;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);

        //applies button widgets to their variables
        home = findViewById(R.id.btnhome);
        ballycastle = findViewById(R.id.btnballycastle);
        belfast = findViewById(R.id.btnbelfast);

        //Navigation to HomeActivity.class
        home.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                //Navigates the user to HomeActivity.
                Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                startActivity(intent);
            }
        });

        //Navigate the user SolvePuzzleActivity.class with region ID of 1.
        ballycastle.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                //Ballycastle's region ID in the database
                int regionID = 1;

                //Saves this region ID to shared preferences for manipulation in SolvePuzzleActivity
                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                SharedPreferences.Editor editor = sharedPreferences.edit();

                editor.putInt("regionID", regionID);
                editor.apply();

                Intent intent = new Intent(getApplicationContext(), SolvePuzzleActivity.class);
                startActivity(intent);
            }
        });

        //Navigate the user SolvePuzzleActivity.class with region ID of 2.
        belfast.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                //Belfast's region ID in database
                int regionID = 2;

                //Saves this region ID to shared preferences for manipulation in SolvePuzzleActivity
                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                SharedPreferences.Editor editor = sharedPreferences.edit();

                editor.putInt("regionID", regionID);
                editor.apply();

                Intent intent = new Intent(getApplicationContext(), SolvePuzzleActivity.class);
                startActivity(intent);
            }
        });
    }
}