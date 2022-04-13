package com.example.dissertation12;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class PuzzlesSolvedActivity extends AppCompatActivity
{
    //Widget variables
    Button btnGoHome;
    TextView textviewTitle, scoreCounter;
    private ListView listviewPuzzlesSolved;

    //Listview adapter
    ArrayAdapter puzzleSolvedAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_puzzles_solved);

        //Applying widgets to their variable
        btnGoHome = findViewById(R.id.btnGoBack);
        textviewTitle = findViewById(R.id.textviewTitle);
        scoreCounter = findViewById(R.id.score);
        listviewPuzzlesSolved = findViewById(R.id.solvedRecordsLV);

        //Retrieving region ID based on region picked
        SharedPreferences getRegionID = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        int currentRegionID = getRegionID.getInt("regionID", 0);

        //Retrieving current user ID
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        int spUserID = sharedPreferences.getInt("id", 0);

        //Navigation button to SelectSolvedLocationActivity.class
        btnGoHome.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(getApplicationContext(), SelectSolvedLocationActivity.class);
                startActivity(intent);
            }
        });

        //If user picked Ballycastle
        if (currentRegionID == 1)
        {
            //Set up title of page and score counter based off Ballycastle
            textviewTitle.setText("Ballycastle's Puzzles");

            String userScore = getUserRegionScore(spUserID, currentRegionID);

            String totalScore = getTotalRegionScore(currentRegionID);

            scoreCounter.setText("Ballycastle Score: " + userScore + "/" + totalScore);
        }
        //If user picked Belfast
        else if (currentRegionID == 2)
        {
            //Set up title of page and score counter based off Belfast
            textviewTitle.setText("Belfast's Puzzles");

            String userScore = getUserRegionScore(spUserID, currentRegionID);

            String totalScore = getTotalRegionScore(currentRegionID);

            scoreCounter.setText("Belfast Score: " + userScore + "/" + totalScore);
        }

        //Create listview based on region chosen
        setListview(spUserID, currentRegionID);
    }

    //Method to return user score
    private String getUserRegionScore(int spUserID, int currentRegionID)
    {
        //Calls DBHelper
        DBHelper MyDB;
        MyDB = new DBHelper(this);

        int userScore = MyDB.getUserScore(spUserID, currentRegionID);
        String strUserScore = String.valueOf(userScore);

        return strUserScore;
    }

    //Method to return total score of region
    private String getTotalRegionScore(int currentRegionID)
    {
        DBHelper MyDB;
        MyDB = new DBHelper(this);

        int totalScore = MyDB.getTotalScore(currentRegionID);
        String strTotalScore = String.valueOf(totalScore);

        return strTotalScore;
    }

    //Method to set up listview
    private void setListview(int spUserID, int currentRegionID)
    {
        DBHelper MyDB;
        MyDB = new DBHelper(this);

        //Set adapter to listiview based on region picked.
        //The adapter uses a DBHelper method that gets all the solved puzzles from a region by a user and organises it via Puzzle Model to display neatly on the listview
        puzzleSolvedAdapter = new ArrayAdapter<>(PuzzlesSolvedActivity.this,
                android.R.layout.simple_list_item_1, MyDB.selectAllSolved(spUserID, currentRegionID));
        //Sets adapter to listview
        listviewPuzzlesSolved.setAdapter(puzzleSolvedAdapter);
    }
}