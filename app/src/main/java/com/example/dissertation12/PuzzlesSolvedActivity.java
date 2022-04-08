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
    Button btnGoHome;

    TextView textviewTitle, scoreCounter;

    private ListView listviewPuzzlesSolved;

    ArrayAdapter puzzleSolvedAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_puzzles_solved);

        btnGoHome = findViewById(R.id.btnGoBack);

        textviewTitle = findViewById(R.id.textviewTitle);

        scoreCounter = findViewById(R.id.score);

        listviewPuzzlesSolved = findViewById(R.id.solvedRecordsLV);

        SharedPreferences getRegionID = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        int currentRegionID = getRegionID.getInt("regionID", 0);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        int spUserID = sharedPreferences.getInt("id", 0);

        btnGoHome.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(getApplicationContext(), SelectSolvedLocationActivity.class);
                startActivity(intent);
            }
        });

        if (currentRegionID == 1)
        {
            textviewTitle.setText("Ballycastle's Puzzles");

            String userScore = getUserRegionScore(spUserID, currentRegionID);

            String totalScore = getTotalRegionScore(currentRegionID);

            scoreCounter.setText("Ballycastle Score: " + userScore + "/" + totalScore);
        }
        else if (currentRegionID == 2)
        {
            textviewTitle.setText("Belfast's Puzzles");

            String userScore = getUserRegionScore(spUserID, currentRegionID);

            String totalScore = getTotalRegionScore(currentRegionID);

            scoreCounter.setText("Belfast Score: " + userScore + "/" + totalScore);
        }

        setListview(spUserID, currentRegionID);

    }

    private String getUserRegionScore(int spUserID, int currentRegionID)
    {
        DBHelper MyDB;
        MyDB = new DBHelper(this);

        int userScore = MyDB.getUserScore(spUserID, currentRegionID);
        String strUserScore = String.valueOf(userScore);

        return strUserScore;
    }

    private String getTotalRegionScore(int currentRegionID)
    {
        DBHelper MyDB;
        MyDB = new DBHelper(this);

        int totalScore = MyDB.getTotalScore(currentRegionID);
        String strTotalScore = String.valueOf(totalScore);

        return strTotalScore;
    }

    private void setListview(int spUserID, int currentRegionID)
    {
        DBHelper MyDB;
        MyDB = new DBHelper(this);

        puzzleSolvedAdapter = new ArrayAdapter<>(PuzzlesSolvedActivity.this, android.R.layout.simple_list_item_1, MyDB.selectAllSolved(spUserID, currentRegionID));
        listviewPuzzlesSolved.setAdapter(puzzleSolvedAdapter);
    }
}