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

    Button home, ballycastle, belfast;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);

        home = (Button) findViewById(R.id.btnhome);
        ballycastle = (Button) findViewById(R.id.btnballycastle);
        belfast = (Button) findViewById(R.id.btnbelfast);

        home.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                startActivity(intent);
            }
        });

        ballycastle.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                int regionID = 1;
                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                SharedPreferences.Editor editor = sharedPreferences.edit();

                editor.putInt("regionID", regionID);
                editor.apply();

                Intent intent = new Intent(getApplicationContext(), SolvePuzzleActivity.class);
                startActivity(intent);
            }
        });

        belfast.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                int regionID = 2;
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