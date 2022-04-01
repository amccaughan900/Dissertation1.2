package com.example.dissertation12;

import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.util.Log;
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
    Spinner spinner_puzzles;
    TextView textview_puzzleSelected, scoreCounter, hintCoinCounter, textviewHint;
    EditText edittext_answer;
    Button btnGoBack, btn_answerPuzzle, btnHint;

    //Arrays for spinners to adapt
    String[] puzzleArray = new String[]{ "Puzzle 1: This peeling vegetable is not in a good condition.",
            "Puzzle 2: This pub has a name that suggests it is holding some sort of score.",
            "Puzzle 3: An opaque, all-black gemstone."
    };

    int currentRegionID = 2;
    int currentItem;

    int userScore;
    int totalScore;

    ArrayAdapter puzzleAdapter;

    int hintCoinAmount;

    DBHelper MyDB;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_belfast);

        spinner_puzzles = findViewById(R.id.spinnerPuzzle);

        textview_puzzleSelected = findViewById(R.id.fullPuzzle);
        scoreCounter = findViewById(R.id.textviewScore);
        edittext_answer = (EditText) findViewById(R.id.answerBox);
        hintCoinCounter = findViewById(R.id.hintCoins);
        textviewHint = findViewById(R.id.hintText);

        btnGoBack = findViewById(R.id.btnGoBack);
        btn_answerPuzzle = (Button) findViewById(R.id.btnAnswer);
        btnHint = findViewById(R.id.btnHint);

        MyDB = new DBHelper(BelfastActivity.this);

//        Makes the drop down list for valence level by setting an adapter onto the spinner containing vItems array
        puzzleAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, puzzleArray);
        puzzleAdapter.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        spinner_puzzles.setAdapter(puzzleAdapter);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        int spUserID = sharedPreferences.getInt("id", 0);

        userScore = MyDB.getUserScore(spUserID, currentRegionID);
        String strUserScore = String.valueOf(userScore);
        totalScore = MyDB.getTotalScore(currentRegionID);
        String strTotalScore = String.valueOf(totalScore);
        scoreCounter.setText("Score: " + strUserScore + "/" + strTotalScore);

        for (int i = 1; i<totalScore; i++)
        {
            Boolean ifPuzzleSolved = MyDB.checkPuzzleSolved(spUserID, puzzleArray[i], currentRegionID);

            int puzzleNumber = i + 1;
            if (ifPuzzleSolved == true)
            {
                puzzleArray[i] = "PUZZLE " + puzzleNumber + ": COMPLETED";
                spinner_puzzles.setAdapter(puzzleAdapter);
            }
            else
            {
                //do nothing
            }
        }

        hintCoinAmount = MyDB.getUserHintAmount(spUserID);
        String strHintCoinAmount = String.valueOf(hintCoinAmount);
        hintCoinCounter.setText("Hint coins: " + strHintCoinAmount);

        btnGoBack.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(getApplicationContext(), LocationActivity.class);
                startActivity(intent);
            }
        });

        spinner_puzzles.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l)
            {
                String itemPuzzle = spinner_puzzles.getSelectedItem().toString();
                textview_puzzleSelected.setText(itemPuzzle);
                currentItem = i;

                int currentPuzzleID = MyDB.getPuzzleID(puzzleArray[currentItem]);

                int puzzleNumber = currentItem + 1;

                Boolean ifPuzzleSolved = MyDB.checkPuzzleSolved(spUserID, puzzleArray[currentItem], currentRegionID);

                boolean checkHintUnlocked = MyDB.checkHintUnlocked(spUserID, currentPuzzleID);

                if (currentPuzzleID == 0)
                {
                    Log.i("Ignore", "Ignore false puzzle id");
                    textviewHint.setText("Hint can be found in solved puzzles");
                }
                else if (ifPuzzleSolved == true)
                {
                    puzzleArray[currentItem] = "PUZZLE " + puzzleNumber + ": COMPLETED";
                    spinner_puzzles.setAdapter(puzzleAdapter);
                    textviewHint.setText("Hint can be found in solved puzzles");
                    Log.i("a", "b");
                }
                else if(checkHintUnlocked == true)
                {
                    String hintText = MyDB.getPuzzleHint(currentPuzzleID);
                    textviewHint.setText(hintText);
                    Log.i("b", "b");
                }
                else
                {
                    textviewHint.setText("Locked Hint. Buy hint to unlock");
                    Log.i("c", "c");
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView)
            {

            }
        });

        btnHint.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        switch (which){
                            case DialogInterface.BUTTON_POSITIVE:
                                //Yes button clicked

                                int hintCoinOverZero = MyDB.getUserHintAmount(spUserID);
                                int currentPuzzleID = MyDB.getPuzzleID(puzzleArray[currentItem]);

                                if (hintCoinOverZero < 1)
                                {
                                    Log.i("e", "e");
                                    Toast.makeText(BelfastActivity.this, "You do not have any coins", Toast.LENGTH_SHORT).show();
                                }
                                else if (currentPuzzleID == 0)
                                {
                                    Toast.makeText(BelfastActivity.this, "Puzzle already solved", Toast.LENGTH_SHORT).show();
                                    Log.i("f", "f");
                                }
                                else
                                {
                                    boolean checkHintUnlocked = MyDB.checkHintUnlocked(spUserID, currentPuzzleID);

                                    if (checkHintUnlocked == true)
                                    {
                                        Toast.makeText(BelfastActivity.this, "Hint already purchased", Toast.LENGTH_SHORT).show();
                                    }

                                    else
                                    {
                                        Log.i("g", "g");
                                        int hintCoinUsed = hintCoinOverZero - 1;
                                        boolean check = MyDB.updateHintAmount(spUserID, hintCoinUsed);
                                        if (check == true)
                                        {
                                            Log.i("h", "h");
                                            String strHintCoinAmount = String.valueOf(hintCoinUsed);
                                            hintCoinCounter.setText("Hint coins: " + strHintCoinAmount);

                                            boolean hintUnlocked = MyDB.insertPuzzleHintUnlocked(spUserID, currentPuzzleID);

                                            if (hintUnlocked == true)
                                            {
                                                String hintText = MyDB.getPuzzleHint(currentPuzzleID);

                                                textviewHint.setText(hintText);
                                            }
                                        }
                                    }
                                }
                                break;

                            case DialogInterface.BUTTON_NEGATIVE:
                                //No button clicked
                                break;
                        }
                    }
                };

                AlertDialog.Builder builder = new AlertDialog.Builder(BelfastActivity.this);
                builder.setMessage("Would you like to unlock this puzzle's hint?").setPositiveButton("Yes", dialogClickListener)
                        .setNegativeButton("No", dialogClickListener).show();
            }
        });

        btn_answerPuzzle.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v)
            {
                String userAnswer = edittext_answer.getText().toString();
                String lowerCaseUserAnswer = userAnswer.toLowerCase();

                String firstFourChars = "";   //substring that will contain first 4 characters of user answer

                if (lowerCaseUserAnswer.length() > 4)
                {
                    firstFourChars = lowerCaseUserAnswer.substring(0, 4);
                }
                else
                {
                    firstFourChars = lowerCaseUserAnswer;
                }

                int puzzleNumber = currentItem + 1;

                if(lowerCaseUserAnswer.equals(""))
                {
                    Toast.makeText(BelfastActivity.this, "Please enter an answer", Toast.LENGTH_SHORT).show();
                }

                else if(firstFourChars.equals("the "))
                {
                    Toast.makeText(BelfastActivity.this, "First word can't be 'the_' Read how to play for more info.", Toast.LENGTH_LONG).show();
                }

                else
                {
                    Boolean checkPuzzleSolved = MyDB.checkPuzzleSolved(spUserID, puzzleArray[currentItem], currentRegionID);

                    if(checkPuzzleSolved==false && !puzzleArray[currentItem].equals("PUZZLE " + puzzleNumber + ": COMPLETED"))
                    {
                        Boolean checkAnswer = MyDB.checkUserVSPuzzleAnswer(puzzleArray[currentItem], lowerCaseUserAnswer);
                        Boolean checkSecondAnswer = MyDB.checkUserVSPuzzleSecondAnswer(puzzleArray[currentItem], lowerCaseUserAnswer);

                        if (checkAnswer == true || checkSecondAnswer == true)
                        {
                            int puzzleID = MyDB.getPuzzleID(puzzleArray[currentItem]);
                            Boolean correctAnswer = MyDB.insertSolvedAnswer(spUserID, puzzleID);

                            if (correctAnswer == true)
                            {
                                userScore = MyDB.getUserScore(spUserID, currentRegionID);
                                String strScore = String.valueOf(userScore);
                                scoreCounter.setText("Score: " + strScore + "/" + strTotalScore);

                                puzzleArray[currentItem] = "PUZZLE " + puzzleNumber + ": COMPLETED";

                                String itemPuzzle = spinner_puzzles.getSelectedItem().toString();
                                textview_puzzleSelected.setText(itemPuzzle);
                                //textviewHint.setText("Hint not required, puzzle already solved");

                                spinner_puzzles.setAdapter(puzzleAdapter);
                                spinner_puzzles.setSelection(currentItem);

                                if (userScore%2 == 0)
                                {
                                    int hintCoinAmount = MyDB.getUserHintAmount(spUserID);

                                    int hintCoinGained = hintCoinAmount + 1;
                                    boolean check = MyDB.updateHintAmount(spUserID, hintCoinGained);
                                    if (check == true)
                                    {
                                        Log.i("c", "c");
                                        String strHintCoinAmount = String.valueOf(hintCoinGained);
                                        hintCoinCounter.setText("Hint coins: " + strHintCoinAmount);

                                        Toast.makeText(BelfastActivity.this, "Correct, the answer is " + lowerCaseUserAnswer + System.lineSeparator() + "Reward: 1 hint coin gained.", Toast.LENGTH_SHORT).show();
                                    }
                                }
                                else
                                {
                                    Toast.makeText(BelfastActivity.this, "Correct, the answer is " + lowerCaseUserAnswer, Toast.LENGTH_SHORT).show();
                                }
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