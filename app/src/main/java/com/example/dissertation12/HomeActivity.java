package com.example.dissertation12;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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
                Intent intent = new Intent(getApplicationContext(), SelectSolvedLocationActivity.class);
                startActivity(intent);
            }
        });

        howToPlay.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                AlertDialog alertDialog = new AlertDialog.Builder(HomeActivity.this).create();
                alertDialog.setTitle("How to play");
                alertDialog.setMessage("The aim of the game is to guess puzzles based on businesses and heritage sites in a region. Users are given clues that are word plays or definitions of the words used in the answer."
                        + System.lineSeparator() + System.lineSeparator()
                        + "An example of what a puzzle could look like is:" + System.lineSeparator() + System.lineSeparator()
                        + "Puzzle: This place is royalty when it comes to patties."
                        + System.lineSeparator() + System.lineSeparator()
                        + "Answer: Burger King"
                        + System.lineSeparator() + System.lineSeparator()
                        + "WARNING"
                        + System.lineSeparator() + System.lineSeparator()
                        + "When entering answers, usage of 'The' as the first word of any puzzles is invalid even if the answer is correct logically, this is to eliminate confusion on whether the answer should or should not have 'The' at the beginning of their answer. This does not apply to words that start with 'The' like theme or theory."
                        + System.lineSeparator() + System.lineSeparator()
                        + "Unlocking and using hint coins"
                        + System.lineSeparator() + System.lineSeparator()
                        + "Puzzles have extra clues that can be unlocked with hint coins. These are earned by increasing the step counter, which reads the amount of steps a user has done or by correctly answering two puzzles. Each extra clue will cost 1 hint coin.");
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                        new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialog, int i)
                            {
                                dialog.dismiss();
                            }
                        });

                alertDialog.show();
            }
        });

        logout.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
            }
        });
    }
}