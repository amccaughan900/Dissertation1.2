package com.example.dissertation12;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;

public class SelectSolvedLocationActivity extends AppCompatActivity
{

    //Widgets variables
    Button home, solvedBallycastle, solvedBelfast;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_solved_location);

        //Widgets being applied variables
        home = findViewById(R.id.btnToHome);
        solvedBallycastle = findViewById(R.id.btnToBallycastleSolved);
        solvedBelfast = findViewById(R.id.btnToBelfastSolved);

        //Navigation button to HomeActivity.class
        home.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent toRegion = new Intent(getApplicationContext(), HomeActivity.class);
                startActivity(toRegion);
            }
        });

        //Navigation button to Ballycastle theme, PuzzlesSolvedActivity.class
        solvedBallycastle.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(getApplicationContext(), PuzzlesSolvedActivity.class);
                startActivity(intent);

                //Save region ID in shared preferences as 1 to represent Ballycastle.
                int regionID = 1;
                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                SharedPreferences.Editor editor = sharedPreferences.edit();

                editor.putInt("regionID", regionID);
                editor.apply();
            }
        });

        //Navigation button to Belfast theme, PuzzlesSolvedActivity.class
        solvedBelfast.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                //Save region ID in shared preferences as 2 to represent Belfast.
                int regionID = 2;
                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                SharedPreferences.Editor editor = sharedPreferences.edit();

                editor.putInt("regionID", regionID);
                editor.apply();

                Intent intent = new Intent(getApplicationContext(), PuzzlesSolvedActivity.class);
                startActivity(intent);
            }
        });
    }
}