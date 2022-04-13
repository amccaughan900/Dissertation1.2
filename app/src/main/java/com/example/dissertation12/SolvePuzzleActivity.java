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
    //Widget variables
    Spinner spinner_puzzles;
    TextView textviewTitle, textview_puzzleSelected, scoreCounter, hintCoinCounter, textviewHint;
    EditText edittext_answer;
    Button btnGoBack, btn_answerPuzzle, btnHint;

    //Arrays for spinners to adapt
    //Ballycastle puzzles
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

    //Belfast puzzles
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

    //Adapter to hold the puzzles of a region to adapt to the spinner
    ArrayAdapter ballycastlePuzzleArrayAdapter, belfastPuzzleArrayAdapter;

    //Hold the value of the position of the current spinner item
    int currentSpinnerItem;

    //Store score
    int userPuzzleScore;
    int totalPuzzleScore;

    //Store user hint coins
    int userHintCoinAmount;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solve_puzzle);

        //Widgets being applied to variables
        textviewTitle = findViewById(R.id.textviewTitle);
        spinner_puzzles = findViewById(R.id.spinnerPuzzle);
        textview_puzzleSelected = findViewById(R.id.fullPuzzle);
        scoreCounter = findViewById(R.id.textviewScore);
        edittext_answer = findViewById(R.id.answerBox);
        hintCoinCounter = findViewById(R.id.hintCoins);
        textviewHint = findViewById(R.id.hintText);
        btnGoBack = findViewById(R.id.btnGoBack);
        btn_answerPuzzle = findViewById(R.id.btnAnswer);
        btnHint = findViewById(R.id.btnHint);

        //Retrieve the region ID from the region of choice upon entering this screen.
        SharedPreferences getRegionID = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        int currentRegionID = getRegionID.getInt("regionID", 0);

        //Retireve the current user's ID
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        int spUserID = sharedPreferences.getInt("id", 0);

        //If region chosen is Ballycastle
        if (currentRegionID == 1)
        {
            //Set title of page to Ballycastle
            textviewTitle.setText("Ballycastle's Puzzles");

            //Set up spinner by applying Ballycastle puzzles to an arrayAdapter
            ballycastlePuzzleArrayAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, ballycastlePuzzleArray);
            ballycastlePuzzleArrayAdapter.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
            spinner_puzzles.setAdapter(ballycastlePuzzleArrayAdapter);

            //This method builds and functions the screen based on Ballycastle choice
            regionSelectedBuilder(ballycastlePuzzleArray, ballycastlePuzzleArrayAdapter, spUserID, currentRegionID);
        }
        //If region chosen is Belfast
        else if(currentRegionID == 2)
        {
            //Set title of page to Belfast
            textviewTitle.setText("Belfast's Puzzles");

            //Set uo spinner by applying Belfast puzzles to an arrayAdapter
            belfastPuzzleArrayAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, belfastPuzzleArray);
            belfastPuzzleArrayAdapter.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
            spinner_puzzles.setAdapter(belfastPuzzleArrayAdapter);

            //This method builds and functions the screen based on Belfast choice
            regionSelectedBuilder(belfastPuzzleArray, belfastPuzzleArrayAdapter, spUserID, currentRegionID);
        }
    }

    //Method to function the region chosen for SolvePuzzleActivity
    private void regionSelectedBuilder(String puzzleArray[], ArrayAdapter puzzleArrayAdapter, int spUserID, int currentRegionID)
    {
        //Calls DBHelper
        DBHelper MyDB;
        MyDB = new DBHelper(this);

        //Scoreboard
        //User score is retrieved by parsing user ID and region ID
        userPuzzleScore = MyDB.getUserScore(spUserID, currentRegionID);
        //Made to string for display
        String strUserScore = String.valueOf(userPuzzleScore);
        //Total score is retrieved by counting all puzzles of a region
        totalPuzzleScore = MyDB.getTotalScore(currentRegionID);
        //Made to string for display
        String strTotalScore = String.valueOf(totalPuzzleScore);
        //Set score counter
        scoreCounter.setText("Score: " + strUserScore + "/" + strTotalScore);

        //Loops through each puzzle in the region and checks if the user has already solved them
        for (int i = 1; i<totalPuzzleScore; i++)
        {
            //Checks that the current puzzle has been solved
            Boolean ifPuzzleSolved = MyDB.checkPuzzleSolved(spUserID, puzzleArray[i], currentRegionID);

            //Text on spinner will display this variable
            int thisPuzzleNumber = i + 1;
            //If puzzle has been solved
            if (ifPuzzleSolved == true)
            {
                //Apply new string to current puzzle and reset the spinner to update.
                puzzleArray[i] = "PUZZLE " + thisPuzzleNumber + ": COMPLETED";
                spinner_puzzles.setAdapter(puzzleArrayAdapter);
            }
            else
            {
                //do nothing
            }
        }

        //Sets up hint counter
        //Retrieve user hint total
        userHintCoinAmount = MyDB.getUserHintAmount(spUserID);
        //Convert it to string for display
        String strHintCoinAmount = String.valueOf(userHintCoinAmount);
        //Set it to hint coin counter textview
        hintCoinCounter.setText("Hint coins: " + strHintCoinAmount);

        //Allows the user to navigate back to LocationActivity.class
        btnGoBack.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(getApplicationContext(), LocationActivity.class);
                startActivity(intent);
            }
        });

        //When an item on spinner is selected
        spinner_puzzles.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l)
            {
                //Set textview to current puzzle selected for better reading
                String itemSelectedPuzzle = spinner_puzzles.getSelectedItem().toString();
                textview_puzzleSelected.setText(itemSelectedPuzzle);

                //The current position value of spinner item.
                currentSpinnerItem = i;

                //Retrieves the current puzzle ID based on the puzzle picked
                int currentPuzzleID = MyDB.getPuzzleID(puzzleArray[currentSpinnerItem]);

                //Text on spinner will display this variable
                int puzzleNumber = currentSpinnerItem + 1;

                //Check if current puzzle has been solved
                Boolean ifPuzzleSolved = MyDB.checkPuzzleSolved(spUserID, puzzleArray[currentSpinnerItem], currentRegionID);

                //Check if current puzzle has the hint unlocked
                boolean checkHintUnlocked = MyDB.checkHintUnlocked(spUserID, currentPuzzleID);

                //Puzzle has already been solved
                if (currentPuzzleID == 0)
                {
                    textviewHint.setText("Hint not required, puzzle already solved");
                }
                //If puzzle has already been solved
                else if (ifPuzzleSolved == true)
                {
                    //Set current puzzle to completed
                    puzzleArray[currentSpinnerItem] = "PUZZLE " + puzzleNumber + ": COMPLETED";
                    //Set adapter to  spinner to update this change
                    spinner_puzzles.setAdapter(puzzleArrayAdapter);
                    //Update textviewHint to showcase this
                    textviewHint.setText("Hint not required, puzzle already solved");
                }
                //If the puzzle has it's hint unlocked already
                else if(checkHintUnlocked == true)
                {
                    //Retrieve the hint from the PUZZLE_DATA table and set it in textview
                    String hintText = MyDB.getPuzzleHint(currentPuzzleID);
                    textviewHint.setText(hintText);
                }
                //Else, nothing changes and textviewHint remains locked
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

        //When hint button is clicked
        btnHint.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                //Begin Alert Dialog message
                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        //Depending on user choice
                        switch (which){
                            case DialogInterface.BUTTON_POSITIVE:
                                //Yes button clicked

                                //Retrieve user hint amount and the current puzzle's ID they are on
                                int hintCoinOverZero = MyDB.getUserHintAmount(spUserID);
                                int currentPuzzleID = MyDB.getPuzzleID(puzzleArray[currentSpinnerItem]);

                                //If user has 0 coins
                                if (hintCoinOverZero < 1)
                                {
                                    Toast.makeText(SolvePuzzleActivity.this, "You do not have any coins", Toast.LENGTH_SHORT).show();
                                }
                                //If puzzle has already been solved
                                else if (currentPuzzleID == 0)
                                {
                                    Toast.makeText(SolvePuzzleActivity.this, "Puzzle already solved", Toast.LENGTH_SHORT).show();
                                }
                                else
                                {
                                    //Check if the hint has already been unlocked by the user
                                    boolean checkHintUnlocked = MyDB.checkHintUnlocked(spUserID, currentPuzzleID);

                                    //If the hint has been unlocked
                                    if (checkHintUnlocked == true)
                                    {
                                        Toast.makeText(SolvePuzzleActivity.this, "Hint already purchased", Toast.LENGTH_SHORT).show();
                                    }
                                    else
                                    {
                                        //remove a hint coin from the user hint amount
                                        int hintCoinUsed = hintCoinOverZero - 1;
                                        //Update user hint amount
                                        boolean check = MyDB.updateHintAmount(spUserID, hintCoinUsed);
                                        //If update is successful
                                        if (check == true)
                                        {
                                            //set hint coin counter as current hint coin amoun the user has
                                            String strHintCoinAmount = String.valueOf(hintCoinUsed);
                                            hintCoinCounter.setText("Hint coins: " + strHintCoinAmount);

                                            //Insert into HINT_USED_DATA to prevent user from buying the hint again for the same puzzle.
                                            boolean hintUnlocked = MyDB.insertPuzzleHintUnlocked(spUserID, currentPuzzleID);

                                            //If insert is successful
                                            if (hintUnlocked == true)
                                            {
                                                //Apply that puzzles hint into the textview for users to see
                                                String hintText = MyDB.getPuzzleHint(currentPuzzleID);
                                                textviewHint.setText(hintText);
                                            }
                                        }
                                    }
                                }
                                //message can close
                                break;

                            case DialogInterface.BUTTON_NEGATIVE:
                                //No button clicked
                                break;
                        }
                    }
                };

                //Contains the message that users will see when they click the hint button and two ooption to either accept or decline buying a hint coin.
                AlertDialog.Builder builder = new AlertDialog.Builder(SolvePuzzleActivity.this);
                builder.setMessage("Would you like to unlock this puzzle's hint?").setPositiveButton("Yes", dialogClickListener)
                        .setNegativeButton("No", dialogClickListener).show();
            }
        });

        //When clicks solve
        btn_answerPuzzle.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                //Retrieves user input for answer into string
                String userAnswer = edittext_answer.getText().toString();

                //Converts answer to lowercase
                String lowerCaseUserAnswer = userAnswer.toLowerCase();
                //Replaces all apostrophes with nothing
                lowerCaseUserAnswer = lowerCaseUserAnswer.replaceAll("\'","");
                //Replaces all whitespaces with nothing
                lowerCaseUserAnswer = lowerCaseUserAnswer.replaceAll(" ", "");

                //String to hold first four character of user answer
                String checkForInitialThe = "";

                //If user answer is 4 or more character long
                if (lowerCaseUserAnswer.length() >= 4)
                {
                    //Apply the first four characters of user answer to checkForInitialThe
                    checkForInitialThe = lowerCaseUserAnswer.substring(0, 4);
                }
                else
                {
                    //Set check blank as it is not warranted
                    checkForInitialThe = "";
                }

                //If user answer contains 'the ' as first four characters.
                if (checkForInitialThe.equals("the "))
                {
                    //Replace the first four characters with blank
                    lowerCaseUserAnswer = lowerCaseUserAnswer.replaceFirst("the ", "");
                }

                //Sets puzzle number ready for check
                int puzzleNumber = currentSpinnerItem + 1;

                //If user doesn't input an answer in the field
                if(userAnswer.equals(""))
                {
                    Toast.makeText(SolvePuzzleActivity.this, "Please enter an answer", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    //Check if puzzle has been solved already
                    Boolean checkPuzzleSolved = MyDB.checkPuzzleSolved(spUserID, puzzleArray[currentSpinnerItem], currentRegionID);

                    //If puzzle has not been solved and current puzzle does not equal completed
                    if(checkPuzzleSolved==false && !puzzleArray[currentSpinnerItem].equals("PUZZLE " + puzzleNumber + ": COMPLETED"))
                    {
                        //Check user answer against PUZZLE_DATA answers
                        Boolean checkAnswer = MyDB.checkUserVSPuzzleAnswer(puzzleArray[currentSpinnerItem], lowerCaseUserAnswer);
                        Boolean checkSecondAnswer = MyDB.checkUserVSPuzzleSecondAnswer(puzzleArray[currentSpinnerItem], lowerCaseUserAnswer);

                        //If either database answers match user answer
                        if (checkAnswer == true || checkSecondAnswer == true)
                        {
                            //Get puzzle ID of current puzzle
                            int puzzleID = MyDB.getPuzzleID(puzzleArray[currentSpinnerItem]);

                            //Insert into SOLVED_DATA table the puzzle has been solved, the user ID and the Puzzle ID
                            Boolean correctAnswer = MyDB.insertSolvedAnswer(spUserID, puzzleID);

                            //If insert is successful
                            if (correctAnswer == true)
                            {
                                //Update score counter
                                userPuzzleScore = MyDB.getUserScore(spUserID, currentRegionID);
                                String strScore = String.valueOf(userPuzzleScore);
                                scoreCounter.setText("Score: " + strScore + "/" + strTotalScore);

                                //Update spinner and textview that puzzle has been completed
                                puzzleArray[currentSpinnerItem] = "PUZZLE " + puzzleNumber + ": COMPLETED";
                                String itemPuzzle = spinner_puzzles.getSelectedItem().toString();
                                textview_puzzleSelected.setText(itemPuzzle);
                                edittext_answer.setText("");
                                spinner_puzzles.setAdapter(puzzleArrayAdapter);

                                //Moves onto the next puzzle on the spinner
                                //Unless it is the final puzzle
                                if (currentSpinnerItem + 1 == totalPuzzleScore)
                                {
                                    spinner_puzzles.setSelection(currentSpinnerItem);
                                }
                                else
                                {
                                    spinner_puzzles.setSelection(currentSpinnerItem + 1);
                                }

                                //If user successfully answers 4 puzzles within a region
                                if (userPuzzleScore % 4 == 0)
                                {
                                    //Update user hint amount in database and counter
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
                                //Still display correct answer but without the reward
                                else
                                {
                                    Toast.makeText(SolvePuzzleActivity.this, "Correct, the answer is " + userAnswer, Toast.LENGTH_SHORT).show();
                                }
                            }
                            //If there is an issue with the database
                            else
                            {
                                Toast.makeText(SolvePuzzleActivity.this, "There's been an error ", Toast.LENGTH_SHORT).show();
                            }
                        }
                        //If user is incorrect
                        else
                        {
                            Toast.makeText(SolvePuzzleActivity.this, "Incorrect, please try again.", Toast.LENGTH_SHORT).show();
                        }
                    }
                    //If the user has already solved the puzzle
                    else
                    {
                        Toast.makeText(SolvePuzzleActivity.this, "This puzzle has already been solved!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}