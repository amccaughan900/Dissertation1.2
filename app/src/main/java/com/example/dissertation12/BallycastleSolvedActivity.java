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

public class BallycastleSolvedActivity extends AppCompatActivity
{

    DBHelper MyDB;

    TextView scoreCounter;

    private ListView ballycastlePuzzleSolvedLV;

    ArrayAdapter ballycastlePuzzleSolvedAdapter;

    int currentRegionID = 1;

    int userScore;
    int totalBallycastleScore;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ballycastle_solved);

        scoreCounter = findViewById(R.id.ballycastleScore);

        ballycastlePuzzleSolvedLV = findViewById(R.id.ballycastleSolvedRecordsLV);

        MyDB = new DBHelper(this);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        int spUserID = sharedPreferences.getInt("id", 0);

        userScore = MyDB.getUserScore(spUserID, currentRegionID);
        String strUserScore = String.valueOf(userScore);
        totalBallycastleScore = MyDB.getTotalScore(currentRegionID);
        String strTotalScore = String.valueOf(totalBallycastleScore);
        scoreCounter.setText("Ballycastle Score: " + strUserScore + "/" + strTotalScore);

        ballycastlePuzzleSolvedAdapter = new ArrayAdapter<>(BallycastleSolvedActivity.this, android.R.layout.simple_list_item_1, MyDB.selectAllSolved(spUserID, currentRegionID));
        ballycastlePuzzleSolvedLV.setAdapter(ballycastlePuzzleSolvedAdapter);

    }

}