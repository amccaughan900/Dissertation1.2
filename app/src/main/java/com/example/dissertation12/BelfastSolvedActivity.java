package com.example.dissertation12;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class BelfastSolvedActivity extends AppCompatActivity
{

    DBHelper MyDB;

    TextView scoreCounter;

    private ListView belfastPuzzleSolvedLV;

    ArrayAdapter belfastPuzzleSolvedAdapter;

    int currentRegionID = 2;

    int userScore;
    int totalBelfastScore;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_belfast_solved);

        scoreCounter = findViewById(R.id.belfastScore);

        belfastPuzzleSolvedLV = findViewById(R.id.belfastSolvedRecordsLV);

        MyDB = new DBHelper(this);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        int spUserID = sharedPreferences.getInt("id", 0);

        userScore = MyDB.getUserScore(spUserID, currentRegionID);
        String strUserScore = String.valueOf(userScore);
        totalBelfastScore = MyDB.getTotalScore(currentRegionID);
        String strTotalScore = String.valueOf(totalBelfastScore);
        scoreCounter.setText("Belfast Score: " + strUserScore + "/" + strTotalScore);

        belfastPuzzleSolvedAdapter = new ArrayAdapter<>(BelfastSolvedActivity.this, android.R.layout.simple_list_item_1, MyDB.selectAllSolved(spUserID, currentRegionID));
        belfastPuzzleSolvedLV.setAdapter(belfastPuzzleSolvedAdapter);
    }
}