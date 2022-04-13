package com.example.dissertation12;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
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

public class SolvePuzzleActivity extends AppCompatActivity
{
    //Names of spinners widgets
    Spinner spinner_puzzles;
    TextView textviewTitle, textview_puzzleSelected, scoreCounter, hintCoinCounter, textviewHint;
    EditText edittext_answer;
    Button btnGoBack, btn_answerPuzzle, btnHint;

    //Arrays for spinners to adapt
    String[] ballycastlePuzzleArray = new String[]{ "Puzzle 1: A shining form of potato?",
            "Puzzle 2: A colour and a mythic being, simple right?",
            "Puzzle 3: This bar will fill the spot after some dinner",
            "Puzzle 4: Bakers are expected to do this.",
            "Puzzle 5: A colour of the rainbow",
            "Puzzle 6: Two word answer: 1st - _ & pepper, 2nd - a building for living in.",
            "Puzzle 7: A walkway alongside the seafront",
            "Puzzle 8: The name of this bar is based on a precious gem usually given in an engagement.",
            "Puzzle 9: DIY that is in a gorgeous state",
            "Puzzle 10: Owning something together and a famous female country-pop singer",
            "Puzzle 11: Ususal award for being runners-up and an extremely steep incline made of rock",
            "Puzzle 12: I never knew Sherlock Holmes sidekick was struggling with his sight",
            "Puzzle 13: This area on Earth has less area discovered than Mars and a place of rest and safety",
            "Puzzle 14: Herb and a short term for a company",
            "Puzzle 15: A tool used to cut metal wiring",
            "Puzzle 16: Angels are usually depicted to have this above their head",
            "Puzzle 17: It does not have a music deck that the namesake would have us believe",
            "Puzzle 18: A chicken lives in this",
            "Puzzle 19: A metallic element in the periodic table, symbolised by pt.",
            "Puzzle 20: Another word for coast and a winged animal",
            "Puzzle 21: Flies do not want to get caught up in these",
            "Puzzle 22: This business shares the name of a magic nanny in a movie",
            "Puzzle 23: Where would most of the rackets be in the town",
            "Puzzle 24: Any living creature found in the sea are considered _ life and a building usually filled with tourists.",
            "Puzzle 25: Playing by the rules and the section of body that contain the most variety of senses."
    };

    //Arrays for spinners to adapt
    String[] belfastPuzzleArray = new String[]{ "Puzzle 1: This peeling vegetable is not in a good condition.",
            "Puzzle 2: This pub has a name that suggests it is holding some sort of score.",
            "Puzzle 3: An opaque, all-black gemstone.",
            "Puzzle 4: Having proof of where abouts during a crime",
            "Puzzle 5: An image usually framed and a place to view various art designs",
            "Puzzle 6: This pub seems to be popular with a certain greek demi-god",
            "Puzzle 7: A lot of these parts make up a puzzle",
            "Puzzle 8: This is worn on a foot and a predefined area.",
            "Puzzle 9: A mythical creature that depicts the head of a human and the body of a lion",
            "Puzzle 10: Usually comes with lemon, especially in drinks. The sun or a torch emits this to a degree",
            "Puzzle 11: Many people have done this with a blanket or duvet if they are cold",
            "Puzzle 12: Entering the world for the first time and growing up in the same place",
            "Puzzle 13: The name of this business is very ironic, something that is trash whilst also being clean.",
            "Puzzle 14: A item that is kept as a reminder of someone or something.",
            "Puzzle 15: A container that is full of flavour liquid"
    };

    ArrayAdapter ballycastlePuzzleArrayAdapter, belfastPuzzleArrayAdapter;
    int currentSpinnerItem;

    int userPuzzleScore;
    int totalPuzzleScore;

    int userHintCoinAmount;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solve_puzzle);

        textviewTitle = findViewById(R.id.textviewTitle);

        spinner_puzzles = findViewById(R.id.spinnerPuzzle);

        textview_puzzleSelected = findViewById(R.id.fullPuzzle);
        scoreCounter = findViewById(R.id.textviewScore);
        edittext_answer = (EditText) findViewById(R.id.answerBox);
        hintCoinCounter = findViewById(R.id.hintCoins);
        textviewHint = findViewById(R.id.hintText);

        btnGoBack = findViewById(R.id.btnGoBack);
        btn_answerPuzzle = (Button) findViewById(R.id.btnAnswer);
        btnHint = findViewById(R.id.btnHint);

        SharedPreferences getRegionID = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        int currentRegionID = getRegionID.getInt("regionID", 0);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        int spUserID = sharedPreferences.getInt("id", 0);

        if (currentRegionID == 1)
        {
            textviewTitle.setText("Ballycastle's Puzzles");

            ballycastlePuzzleArrayAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, ballycastlePuzzleArray);
            ballycastlePuzzleArrayAdapter.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
            spinner_puzzles.setAdapter(ballycastlePuzzleArrayAdapter);

            regionSelectedBuilder(ballycastlePuzzleArray, ballycastlePuzzleArrayAdapter, spUserID, currentRegionID);
        }
        else if(currentRegionID == 2)
        {
            textviewTitle.setText("Belfast's Puzzles");

            belfastPuzzleArrayAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, belfastPuzzleArray);
            belfastPuzzleArrayAdapter.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
            spinner_puzzles.setAdapter(belfastPuzzleArrayAdapter);

            regionSelectedBuilder(belfastPuzzleArray, belfastPuzzleArrayAdapter, spUserID, currentRegionID);
        }
    }

    private void regionSelectedBuilder(String puzzleArray[], ArrayAdapter puzzleArrayAdapter, int spUserID, int currentRegionID)
    {
        DBHelper MyDB;
        MyDB = new DBHelper(this);

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
                                    Toast.makeText(SolvePuzzleActivity.this, "You do not have any coins", Toast.LENGTH_SHORT).show();
                                }
                                else if (currentPuzzleID == 0)
                                {
                                    Toast.makeText(SolvePuzzleActivity.this, "Puzzle already solved", Toast.LENGTH_SHORT).show();
                                }
                                else
                                {
                                    boolean checkHintUnlocked = MyDB.checkHintUnlocked(spUserID, currentPuzzleID);

                                    if (checkHintUnlocked == true)
                                    {
                                        Toast.makeText(SolvePuzzleActivity.this, "Hint already purchased", Toast.LENGTH_SHORT).show();
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

                AlertDialog.Builder builder = new AlertDialog.Builder(SolvePuzzleActivity.this);
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
                lowerCaseUserAnswer = lowerCaseUserAnswer.replaceAll("\'","");
                lowerCaseUserAnswer = lowerCaseUserAnswer.replaceAll(" ", "");

                String checkForInitialThe = "";

                if (lowerCaseUserAnswer.length() >= 4)
                {
                    checkForInitialThe = lowerCaseUserAnswer.substring(0, 4);
                }
                else
                {
                    checkForInitialThe = "";
                }

                if (checkForInitialThe.equals("the "))
                {
                    lowerCaseUserAnswer = lowerCaseUserAnswer.replaceFirst("the ", "");
                }

                int puzzleNumber = currentSpinnerItem + 1;

                if(userAnswer.equals(""))
                {
                    Toast.makeText(SolvePuzzleActivity.this, "Please enter an answer", Toast.LENGTH_SHORT).show();
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

                                        Toast.makeText(SolvePuzzleActivity.this, "Correct, the answer is " + userAnswer + System.lineSeparator() + "Reward: 1 hint coin gained.", Toast.LENGTH_SHORT).show();
                                    }
                                }
                                else
                                {
                                    Toast.makeText(SolvePuzzleActivity.this, "Correct, the answer is " + userAnswer, Toast.LENGTH_SHORT).show();
                                }
                            }
                            else
                            {
                                Toast.makeText(SolvePuzzleActivity.this, "There's been an error ", Toast.LENGTH_SHORT).show();
                            }
                        }
                        else
                        {
                            Toast.makeText(SolvePuzzleActivity.this, "Incorrect, please try again.", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else
                    {
                        Toast.makeText(SolvePuzzleActivity.this, "This puzzle has already been solved!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}