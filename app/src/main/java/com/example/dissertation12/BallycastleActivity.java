package com.example.dissertation12;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class BallycastleActivity extends AppCompatActivity
{

    //Names of spinners widgets
    Spinner spinner_ballycastle;
    TextView textview_puzzleSelected, scoreCounter;
    EditText edittext_answer;
    Button btn_answerPuzzle;

    //Arrays for spinners to adapt
    String[] ballycastlePuzzles = new String[]{ "Puzzle 1: A shining form of potato?",
            "Puzzle 2: A colour and a mythic being, simple right?",
            "Puzzle 3: This bar will fill the spot after some dinner",
            "Puzzle 4: Bakers are expected to do this.",
            "Puzzle 5: A colour of the rainbow",
            "Puzzle 6: Two word answer: 1st - _ & pepper, 2nd - a building used to live in.",
            "Puzzle 7: A walkway alongside the seafront",
            "Puzzle 8: This bar's name is based on a precious gem usually given in an engagement",
            "Puzzle 9: DIY that is in a gorgeous state",
            "Puzzle 10: Owning something together and a famous female country-pop singer"
            };

    int currentRegionID = 1;
    int currentItem;
    int userScore;

    //ArrayAdapters to attach the arrays above to
    ArrayAdapter ballycastleAdapter;

    DBHelper MyDB;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ballycastle);

        spinner_ballycastle = findViewById(R.id.spinner_ballycastle);

        textview_puzzleSelected = findViewById(R.id.fullPuzzle);
        scoreCounter = findViewById(R.id.textviewScore);
        edittext_answer = (EditText) findViewById(R.id.answerBox);

        btn_answerPuzzle = (Button) findViewById(R.id.btnAnswer);

        MyDB = new DBHelper(BallycastleActivity.this);

        //Makes the drop down list for valence level by setting an adapter onto the spinner containing vItems array
        ballycastleAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, ballycastlePuzzles);
        ballycastleAdapter.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        spinner_ballycastle.setAdapter(ballycastleAdapter);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        int spUserID = sharedPreferences.getInt("id", 0);

        userScore = MyDB.getUserBallycastleScore(spUserID);
        String strScore = String.valueOf(userScore);
        scoreCounter.setText("Score: " + strScore + "/10");

        spinner_ballycastle.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l)
            {
                String itemPuzzle = spinner_ballycastle.getSelectedItem().toString();
                textview_puzzleSelected.setText(itemPuzzle);
                currentItem = i;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView)
            {

            }
        });



        btn_answerPuzzle.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v)
            {
                String userAnswer = edittext_answer.getText().toString();

                if(userAnswer.equals(""))
                {
                    Toast.makeText(BallycastleActivity.this, "Please enter an answer", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Boolean checkPuzzleSolved = MyDB.checkPuzzleSolved(spUserID, ballycastlePuzzles[currentItem], currentRegionID);

                    if(checkPuzzleSolved==false)
                    {
                        Boolean checkAnswer = MyDB.checkPuzzleAnswer(ballycastlePuzzles[currentItem], userAnswer);

                        if (checkAnswer == true)
                        {
                            int puzzleID = MyDB.getPuzzleID(ballycastlePuzzles[currentItem]);
                            Boolean correctAnswer = MyDB.insertSolvedAnswer(spUserID, puzzleID);

                            if (correctAnswer == true)
                            {
                                Toast.makeText(BallycastleActivity.this, "Correct, the answer is " + userAnswer, Toast.LENGTH_SHORT).show();

                                userScore = MyDB.getUserBallycastleScore(spUserID);
                                String strScore = String.valueOf(userScore);
                                scoreCounter.setText("Score: " + strScore + "/10");
                            }
                            else
                            {
                                Toast.makeText(BallycastleActivity.this, "There's been an error ", Toast.LENGTH_SHORT).show();
                            }
                        }
                        else
                        {
                            Toast.makeText(BallycastleActivity.this, "Incorrect, please try again.", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else
                    {
                        Toast.makeText(BallycastleActivity.this, "This puzzle has already been solved!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }
}