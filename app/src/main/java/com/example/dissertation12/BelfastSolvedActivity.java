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

public class BelfastSolvedActivity extends AppCompatActivity
{

    DBHelper MyDB;

    Button btnGoHome;

    TextView scoreCounter;

    private ListView listviewPuzzlesSolved;

    ArrayAdapter puzzleSolvedAdapter;

    int currentRegionID = 2;

    int userScore;
    int totalScore;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_belfast_solved);

        btnGoHome = findViewById(R.id.btnGoBack);

        scoreCounter = findViewById(R.id.score);

        listviewPuzzlesSolved = findViewById(R.id.solvedRecordsLV);

        MyDB = new DBHelper(this);

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

        userScore = MyDB.getUserScore(spUserID, currentRegionID);
        String strUserScore = String.valueOf(userScore);
        totalScore = MyDB.getTotalScore(currentRegionID);
        String strTotalScore = String.valueOf(totalScore);
        scoreCounter.setText("Belfast Score: " + strUserScore + "/" + strTotalScore);

        puzzleSolvedAdapter = new ArrayAdapter<>(BelfastSolvedActivity.this, android.R.layout.simple_list_item_1, MyDB.selectAllSolved(spUserID, currentRegionID));
        listviewPuzzlesSolved.setAdapter(puzzleSolvedAdapter);
    }
}