package com.example.dissertation12;

import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
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
    Spinner spinner_puzzles;
    TextView textview_puzzleSelected, scoreCounter, hintCoinCounter, textviewHint;
    EditText edittext_answer;
    Button btnGoBack, btn_answerPuzzle, btnHint;

    //Arrays for spinners to adapt
    String[] puzzleArray = new String[]{ "Puzzle 1: A shining form of potato?",
            "Puzzle 2: A colour and a mythic being, simple right?",
            "Puzzle 3: This bar will fill the spot after some dinner",
            "Puzzle 4: Bakers are expected to do this.",
            "Puzzle 5: A colour of the rainbow",
            "Puzzle 6: Two word answer: 1st - _ & pepper, 2nd - a building used to live in.",
            "Puzzle 7: A walkway alongside the seafront",
            "Puzzle 8: The name of this bar is based on a precious gem usually given in an engagement.",
            "Puzzle 9: DIY that is in a gorgeous state",
            "Puzzle 10: Owning something together and a famous female country-pop singer",
            "Puzzle 11: Ususal award for being runners-up and an extremely steep incline made of rock",
            "Puzzle 12: I never knew Sherlock Holmes sidekick was struggling with his sight",
            "Puzzle 13: This area on Earth has less area disovered than Mars and a place of rest and safety",
            "Puzzle 14: Herb and friends",
            "Puzzle 15: A tool used to cut metal wiring",
            "Puzzle 16: Angels are usually depicted to have this above their head",
            "Puzzle 17: It does not have a deck that the namesake would have us believe",
            "Puzzle 18: Providing a helpful service",
            "Puzzle 19: A metallic element in the periodic table, symbolised by pt.",
            "Puzzle 20: Another word for coast and a winged animal",
            "Puzzle 21: _ & pestle",
            "Puzzle 22: This business shares the name of a magic nanny in a movie",
            "Puzzle 23: Where would  most of the rackets be in the town",
            "Puzzle 24: Any living creature found in the sea are considered _ life and a place usually filled with tourists.",
            "Puzzle 25: Playing by the rules and the section of body that contain the most variety of senses."
            };
    ArrayAdapter puzzleArrayAdapter;
    int currentSpinnerItem;

    int currentRegionID = 1;

    int userPuzzleScore;
    int totalPuzzleScore;


    int userHintCoinAmount;

    DBHelper MyDB;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ballycastle);

        spinner_puzzles = findViewById(R.id.spinnerPuzzle);

        textview_puzzleSelected = findViewById(R.id.fullPuzzle);
        scoreCounter = findViewById(R.id.textviewScore);
        edittext_answer = (EditText) findViewById(R.id.answerBox);
        hintCoinCounter = findViewById(R.id.hintCoins);
        textviewHint = findViewById(R.id.hintText);

        btnGoBack = findViewById(R.id.btnGoBack);
        btn_answerPuzzle = (Button) findViewById(R.id.btnAnswer);
        btnHint = findViewById(R.id.btnHint);

        MyDB = new DBHelper(BallycastleActivity.this);

//        Makes the drop down list for valence level by setting an adapter onto the spinner containing vItems array
        puzzleArrayAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, puzzleArray);
        puzzleArrayAdapter.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        spinner_puzzles.setAdapter(puzzleArrayAdapter);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        int spUserID = sharedPreferences.getInt("id", 0);


        userPuzzleScore = MyDB.getUserScore(spUserID, currentRegionID);
        String strUserScore = String.valueOf(userPuzzleScore);
        totalPuzzleScore = MyDB.getTotalScore(currentRegionID);
        String strTotalScore = String.valueOf(totalPuzzleScore);
        scoreCounter.setText("Score: " + strUserScore + "/" + strTotalScore);

        for (int i = 1; i<totalPuzzleScore; i++)
        {
            Boolean ifPuzzleSolved = MyDB.checkPuzzleSolved(spUserID, puzzleArray[i], currentRegionID);

            int thisPuzzleNumber = i + 1;
            if (ifPuzzleSolved == true)
            {
                puzzleArray[i] = "PUZZLE " + thisPuzzleNumber + ": COMPLETED";
                spinner_puzzles.setAdapter(puzzleArrayAdapter);
            }
            else
            {
                //do nothing
            }
        }

        userHintCoinAmount = MyDB.getUserHintAmount(spUserID);
        String strHintCoinAmount = String.valueOf(userHintCoinAmount);
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
                String itemSelectedPuzzle = spinner_puzzles.getSelectedItem().toString();
                textview_puzzleSelected.setText(itemSelectedPuzzle);
                currentSpinnerItem = i;

                Log.i("puzzle", puzzleArray[currentSpinnerItem]);

                int currentPuzzleID = MyDB.getPuzzleID(puzzleArray[currentSpinnerItem]);

                int puzzleNumber = currentSpinnerItem + 1;

                Boolean ifPuzzleSolved = MyDB.checkPuzzleSolved(spUserID, puzzleArray[currentSpinnerItem], currentRegionID);

                boolean checkHintUnlocked = MyDB.checkHintUnlocked(spUserID, currentPuzzleID);

                if (currentPuzzleID == 0)
                {
                    textviewHint.setText("Hint not required, puzzle already solved");
                }
                else if (ifPuzzleSolved == true)
                {
                    puzzleArray[currentSpinnerItem] = "PUZZLE " + puzzleNumber + ": COMPLETED";
                    spinner_puzzles.setAdapter(puzzleArrayAdapter);
                    textviewHint.setText("Hint not required, puzzle already solved");
                }
                else if(checkHintUnlocked == true)
                {
                    String hintText = MyDB.getPuzzleHint(currentPuzzleID);
                    textviewHint.setText(hintText);
                }
                else
                {
                    textviewHint.setText("Locked Hint. Buy hint to unlock");
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
                                int currentPuzzleID = MyDB.getPuzzleID(puzzleArray[currentSpinnerItem]);

                                if (hintCoinOverZero < 1)
                                {
                                    Toast.makeText(BallycastleActivity.this, "You do not have any coins", Toast.LENGTH_SHORT).show();
                                }
                                else if (currentPuzzleID == 0)
                                {
                                    Toast.makeText(BallycastleActivity.this, "Puzzle already solved", Toast.LENGTH_SHORT).show();
                                }
                                else
                                {
                                    boolean checkHintUnlocked = MyDB.checkHintUnlocked(spUserID, currentPuzzleID);

                                    if (checkHintUnlocked == true)
                                    {
                                        Toast.makeText(BallycastleActivity.this, "Hint already purchased", Toast.LENGTH_SHORT).show();
                                    }

                                    else
                                    {
                                        int hintCoinUsed = hintCoinOverZero - 1;
                                        boolean check = MyDB.updateHintAmount(spUserID, hintCoinUsed);
                                        if (check == true)
                                        {
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

                AlertDialog.Builder builder = new AlertDialog.Builder(BallycastleActivity.this);
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

                int puzzleNumber = currentSpinnerItem + 1;

                if(lowerCaseUserAnswer.equals(""))
                {
                    Toast.makeText(BallycastleActivity.this, "Please enter an answer", Toast.LENGTH_SHORT).show();
                }

                else if(firstFourChars.equals("the "))
                {
                    Toast.makeText(BallycastleActivity.this, "Start of an answer can't be 'the '", Toast.LENGTH_SHORT).show();
                }

                else
                {
                    Boolean checkPuzzleSolved = MyDB.checkPuzzleSolved(spUserID, puzzleArray[currentSpinnerItem], currentRegionID);

                    if(checkPuzzleSolved==false && !puzzleArray[currentSpinnerItem].equals("PUZZLE " + puzzleNumber + ": COMPLETED"))
                    {
                        Boolean checkAnswer = MyDB.checkUserVSPuzzleAnswer(puzzleArray[currentSpinnerItem], lowerCaseUserAnswer);
                        Boolean checkSecondAnswer = MyDB.checkUserVSPuzzleSecondAnswer(puzzleArray[currentSpinnerItem], lowerCaseUserAnswer);

                        if (checkAnswer == true || checkSecondAnswer == true)
                        {
                            int puzzleID = MyDB.getPuzzleID(puzzleArray[currentSpinnerItem]);
                            Boolean correctAnswer = MyDB.insertSolvedAnswer(spUserID, puzzleID);

                            if (correctAnswer == true)
                            {
                                userPuzzleScore = MyDB.getUserScore(spUserID, currentRegionID);
                                String strScore = String.valueOf(userPuzzleScore);
                                scoreCounter.setText("Score: " + strScore + "/" + strTotalScore);

                                puzzleArray[currentSpinnerItem] = "PUZZLE " + puzzleNumber + ": COMPLETED";

                                String itemPuzzle = spinner_puzzles.getSelectedItem().toString();
                                textview_puzzleSelected.setText(itemPuzzle);
                                edittext_answer.setText("");
                                //textviewHint.setText("Hint not required, puzzle already solved");

                                spinner_puzzles.setAdapter(puzzleArrayAdapter);
                                if (currentSpinnerItem + 1 == totalPuzzleScore)
                                {
                                    spinner_puzzles.setSelection(currentSpinnerItem);
                                }
                                else
                                {
                                    spinner_puzzles.setSelection(currentSpinnerItem + 1);
                                }
                                if (userPuzzleScore % 4 == 0)
                                {
                                    int hintCoinAmount = MyDB.getUserHintAmount(spUserID);

                                    int hintCoinGained = hintCoinAmount + 1;
                                    boolean check = MyDB.updateHintAmount(spUserID, hintCoinGained);
                                    if (check == true)
                                    {
                                        String strHintCoinAmount = String.valueOf(hintCoinGained);
                                        hintCoinCounter.setText("Hint coins: " + strHintCoinAmount);

                                        Toast.makeText(BallycastleActivity.this, "Correct, the answer is " + lowerCaseUserAnswer + System.lineSeparator() + "Reward: 1 hint coin gained.", Toast.LENGTH_SHORT).show();
                                    }
                                }
                                else
                                {
                                    Toast.makeText(BallycastleActivity.this, "Correct, the answer is " + lowerCaseUserAnswer, Toast.LENGTH_SHORT).show();
                                }
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