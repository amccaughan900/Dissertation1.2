package com.example.dissertation12;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class BelfastActivity extends AppCompatActivity
{
    //Names of spinners widgets
    Spinner spinner_belfast;
    TextView textview_puzzleSelected, scoreCounter;
    EditText edittext_answer;
    Button btn_answerPuzzle;

    //Arrays for spinners to adapt
    String[] belfastPuzzles = new String[]
            { "Puzzle 1: This peeling vegetable is not in a good condition.",
            "Puzzle 2: This pub has a name that suggests it is holding some sort of score.",
            "Puzzle 3: An opaque, all-black gemstone."
            };

    int currentRegionID = 2;
    int currentItem;

    int userScore;
    int totalBelfastScore;

    //ArrayAdapters to attach the arrays above to
    ArrayAdapter belfastAdapter;

    DBHelper MyDB;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_belfast);

        spinner_belfast = findViewById(R.id.spinner_belfast);

        textview_puzzleSelected = findViewById(R.id.fullPuzzle);
        edittext_answer = (EditText) findViewById(R.id.answerBox);
        scoreCounter = findViewById(R.id.textviewScore);

        btn_answerPuzzle = (Button) findViewById(R.id.btnAnswer);

        MyDB = new DBHelper(BelfastActivity.this);

        //Makes the drop down list for valence level by setting an adapter onto the spinner containing vItems array
        belfastAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, belfastPuzzles);
        belfastAdapter.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        spinner_belfast.setAdapter(belfastAdapter);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        int spUserID = sharedPreferences.getInt("id", 0);

        userScore = MyDB.getUserScore(spUserID, currentRegionID);
        String strUserScore = String.valueOf(userScore);
        totalBelfastScore = MyDB.getTotalScore(currentRegionID);
        String strTotalScore = String.valueOf(totalBelfastScore);
        scoreCounter.setText("Score: " + strUserScore + "/" + strTotalScore);

        for (int i = 1; i<totalBelfastScore; i++)
        {
            Boolean ifPuzzleSolved = MyDB.checkPuzzleSolved(spUserID, belfastPuzzles[i], currentRegionID);

            int puzzleNumber = i + 1;
            if (ifPuzzleSolved == true)
            {
                belfastPuzzles[i] = "PUZZLE " + puzzleNumber + ": COMPLETED";
                spinner_belfast.setAdapter(belfastAdapter);
            }
            else
            {
                //do nothing
            }

        }

        spinner_belfast.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l)
            {
                String itemPuzzle = spinner_belfast.getSelectedItem().toString();
                textview_puzzleSelected.setText(itemPuzzle);
                currentItem = i;

                int puzzleNumber = currentItem + 1;

                Boolean ifPuzzleSolved = MyDB.checkPuzzleSolved(spUserID, belfastPuzzles[currentItem], currentRegionID);

                if (ifPuzzleSolved == true)
                {
                    belfastPuzzles[currentItem] = "PUZZLE " + puzzleNumber + ": COMPLETED";
                    spinner_belfast.setAdapter(belfastAdapter);
                }
                else
                {
                    //Do nothing
                }
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
                String lowerCaseUserAnswer = userAnswer.toLowerCase();

                int puzzleNumber = currentItem + 1;

                if(lowerCaseUserAnswer.equals(""))
                {
                    Toast.makeText(BelfastActivity.this, "Please enter an answer", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Boolean checkPuzzleSolved = MyDB.checkPuzzleSolved(spUserID, belfastPuzzles[currentItem], currentRegionID);

                    if(checkPuzzleSolved==false && !belfastPuzzles[currentItem].equals("PUZZLE " + puzzleNumber + ": COMPLETED"))
                    {
                        Boolean checkAnswer = MyDB.checkUserVSPuzzleAnswer(belfastPuzzles[currentItem], lowerCaseUserAnswer);

                        if (checkAnswer == true)
                        {
                            int puzzleID = MyDB.getPuzzleID(belfastPuzzles[currentItem]);
                            Boolean correctAnswer = MyDB.insertSolvedAnswer(spUserID, puzzleID);

                            if (correctAnswer == true)
                            {
                                Toast.makeText(BelfastActivity.this, "Correct, the answer is " + lowerCaseUserAnswer, Toast.LENGTH_SHORT).show();

                                userScore = MyDB.getUserScore(spUserID, currentRegionID);
                                String strScore = String.valueOf(userScore);
                                scoreCounter.setText("Score: " + strScore + "/" + strTotalScore);

                                belfastPuzzles[currentItem] = "PUZZLE " + puzzleNumber + ": COMPLETED";

                                String itemPuzzle = spinner_belfast.getSelectedItem().toString();
                                textview_puzzleSelected.setText(itemPuzzle);

                                spinner_belfast.setAdapter(belfastAdapter);
                                spinner_belfast.setSelection(currentItem);
                            }
                            else
                            {
                                Toast.makeText(BelfastActivity.this, "There's been an error ", Toast.LENGTH_SHORT).show();
                            }
                        }
                        else
                        {
                            Toast.makeText(BelfastActivity.this, "Incorrect, please try again.", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else
                    {
                        Toast.makeText(BelfastActivity.this, "This puzzle has already been solved!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }
}