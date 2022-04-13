package com.example.dissertation12;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

//Implements an external class called SensorEventListener to allow reading of steps
public class HomeActivity extends AppCompatActivity implements SensorEventListener
{
    //Button widgets
    Button solvePuzzles, completePuzzles, howToPlay, logout, allowTrackerOnSleep;
    //Textview widget
    TextView textViewStepCounter;
    //SensorManager variable
    private SensorManager sensorManager;
    //Sensor variable acting as the pedometer
    private Sensor pedometer;
    //Relays if device is compatible
    private boolean isCounterSensorPresent;
    //Store user steps walked
    int userSteps;
    //Store stepCount before sensor changes
    int oldStepCount = 0;
    //Hint coin provided when user walks a certain amount of steps
    int hintCoinsAwarded;
    //Boolean to contain if user chooses to have steps tracked while app is on sleep mode
    private boolean userAllowsTrackingOnPause;
    //Used to store current user's ID
    int spUserID;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //Gets the user ID from shared preferences and applies it to spUserID
        SharedPreferences getUser = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        spUserID = getUser.getInt("id", 0);

        //Emsures this activity is kept active to allow tracking of steps during other activities
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);


        //Attaches each widget variable to it's corresponding layout widget
        textViewStepCounter = findViewById(R.id.textViewStepCounter);
        solvePuzzles =  findViewById(R.id.btnsolvepuzzles);
        completePuzzles =findViewById(R.id.btncompletedpuzzles);
        howToPlay =  findViewById(R.id.btnhowtoplay);
        logout =  findViewById(R.id.btnlogout);
        allowTrackerOnSleep = findViewById(R.id.btnAllowUserToTrackDuringSleep);

        //Retrieves sensor services to manipulate
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

        //Checks whether device is compatible with sensor service
        if (sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)!=null)
        {
            //Applies sensor to variable pedometer
            pedometer = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
            //Sets to true to manipulate sensor activities
            isCounterSensorPresent = true;
        }
        else
        {
            //Device is not compatible
            textViewStepCounter.setText("Walking sensor not found");
            isCounterSensorPresent = false;
        }

        //Implementation to save user tracker settings. Currently unavailable due to bug fix in Unit Test ID 2.
        //Still required to set up button on create however; it will automatically set to sleep mode off if sensor is found
        //Future implementation would have the database save this information
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        userAllowsTrackingOnPause = sharedPreferences.getBoolean("Tracker Setting", userAllowsTrackingOnPause);

        //Sensor cannot be used
        if (isCounterSensorPresent == false)
        {
            allowTrackerOnSleep.setText("Tracker disabled");
        }
        //Sensor is found and sleeping mode is turned to on.
        else if (userAllowsTrackingOnPause == true)
        {
            allowTrackerOnSleep.setText("Tracker sleep mode: on");
        }
        //Sensor is found and sleeping mode is turned to off
        else
        {
            allowTrackerOnSleep.setText("Tracker sleep mode: off");
        }

        //Button to switch sleep mode
        allowTrackerOnSleep.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                //Sensor not found
                if (isCounterSensorPresent == false)
                {
                    //do nothing
                }
                //Sets sleeping mode to off
                else if (userAllowsTrackingOnPause == true)
                {
                    userAllowsTrackingOnPause = false;

                    SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                    SharedPreferences.Editor editor = sharedPreferences.edit();

                    editor.putBoolean("Tracker Setting", userAllowsTrackingOnPause);
                    editor.apply();

                    allowTrackerOnSleep.setText("Tracker sleep mode: off");
                }
                //Sets sleeping mode to on
                else
                {
                    userAllowsTrackingOnPause = true;

                    SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                    SharedPreferences.Editor editor = sharedPreferences.edit();

                    editor.putBoolean("Tracker Setting", userAllowsTrackingOnPause);
                    editor.apply();
                    allowTrackerOnSleep.setText("Tracker sleep mode: on");
                }
            }
        });

        //Navigation button to LocationActivity.class
        solvePuzzles.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                //Intent method to navigate user
                Intent toRegion = new Intent(getApplicationContext(), LocationActivity.class);
                startActivity(toRegion);
            }
        });

        //Navigation button to SelectSolvedLocationActivity.class
        completePuzzles.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                //Intent method to navigate user
                Intent intent = new Intent(getApplicationContext(), SelectSolvedLocationActivity.class);
                startActivity(intent);
            }
        });

        //How to play button.
        //Message will appear with all the details of what is expected in the app.
        howToPlay.setOnClickListener(new View.OnClickListener()
        {   @Override
            public void onClick(View view)
            { AlertDialog alertDialog = new AlertDialog.Builder(HomeActivity.this).create();
                //Sets title of message
                alertDialog.setTitle("How to play");
                //Sets main body of message
                alertDialog.setMessage("The aim of the game is to guess puzzles based on businesses and heritage sites in a region. Users are given clues that are word plays or definitions of the words used in the answer."
                        //Separate lines, acting as paragraph breaks
                        + System.lineSeparator() + System.lineSeparator()
                        + "An example of what a puzzle could look like is:"
                        + System.lineSeparator() + System.lineSeparator()
                        + "Puzzle: This place is royalty when it comes to patties."
                        + System.lineSeparator() + System.lineSeparator()
                        + "Answer: Burger King"
                        + System.lineSeparator() + System.lineSeparator()
                        + "Some clues may show this symbol '_'. This represents the word in the answer, an example may be puzzle1: '_' shore. The answer would be sea, as it fits into sea shore"
                        + System.lineSeparator() + System.lineSeparator()
                        + "WARNING"
                        + System.lineSeparator() + System.lineSeparator()
                        + "When entering answers, there can be up to two different answers. Both are the same answer but just a slight variation, if a business may have extra words. An example might be chipco, or chipco fish and chips."
                        + System.lineSeparator() + System.lineSeparator()
                        + "Unlocking and using hint coins"
                        + System.lineSeparator() + System.lineSeparator()
                        + "Puzzles have extra clues that can be unlocked with hint coins. These are earned by walking 4000 steps, so this can be 4000, 8000, 12000, etc, or by correctly answering four puzzles in one region. Each hint will cost 1 hint coin."
                        + System.lineSeparator() + System.lineSeparator()
                        + "A hint can be earned while not on the home screen however, you will not be notified if that is the case. The hint coin will still be added. If you are solving a puzzle and buy a hint, it may not reduce the counter by 1. This will be the cause. Otherwise, navigating the screens will update the hint coin counter."
                        + System.lineSeparator() + System.lineSeparator()
                        + "Users can turn tracking on and off during sleep at any time on the home screen. If you are confused about what sleep means, it is when the user has the app active but not currently opened. This means you can earn coins without having to keep the screen active as long as you're walking and the button 'tracker on sleep' is switched to on. Ensure the text within the button is turned on for sleep mode to be on. Once the app is fully closed, it will stop and the button will reset to off, upon opening again."
                );
                //Ok button to allow users to navigate off the button
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                        new DialogInterface.OnClickListener()
                        {
                            //Removes the message
                            @Override
                            public void onClick(DialogInterface dialog, int i)
                            {
                                dialog.dismiss();
                            }
                        });
                //Shows message when button is clicked
                alertDialog.show();
            }
        });

        //Navigation to LoginActivity.class
        logout.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
            }
        });
    }


    //Method for everytime the sensor changes
    @Override
    public void onSensorChanged(SensorEvent sensorEvent)
    {
        //Checks if sensor is available
        if (sensorEvent.sensor == pedometer)
        {
            //Retrieve olsStepCount from last sensor change
            SharedPreferences getOldSteps = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            oldStepCount = getOldSteps.getInt("User steps before next activity", 0);

            //Call DBHelper to use database methods
            DBHelper MyDB;
            MyDB = new DBHelper(this);

            //Counts the number of steps taken in the device.
            //Flexibly changes when user walks
            int stepCount = (int) sensorEvent.values[0];

            //If step hasn't been made, it will be the same as oldStepCount
            //IF oldStepCount equals 0, the user has just logged in
            if (stepCount == oldStepCount || oldStepCount == 0)
            {
                //Get user steps and apply it to step counter textview
                userSteps = MyDB.getUserStepAmount(spUserID);
                textViewStepCounter.setText("Steps walked: " + String.valueOf(userSteps));
            }
            //Allow steps to be increased as false step checks have been covered
            else
            {
                //Gets user steps amount from user table
                userSteps = MyDB.getUserStepAmount(spUserID);

                //Applies a step onto the count
                userSteps = userSteps+ 1;
                //update the user table to configure the change in steps
                MyDB.updateUserStepAmount(spUserID, userSteps);

                //Rewards users if they walked 4000 steps or a multiplication of 4000.
                if (userSteps % 4000 == 0)
                {
                    //User earns a hint coin
                    hintCoinsAwarded = 1;

                    //Retrieves user's current hint total
                    int userHintAmount = MyDB.getUserHintAmount(spUserID);
                    //Updates the amount a user has by one
                    int updatedUserAmount = userHintAmount + hintCoinsAwarded;
                    //Update hint total by one for that user
                    MyDB.updateHintAmount(spUserID, updatedUserAmount);

                    //Provide a message congratulating the user on earning their reward.
                    AlertDialog alertDialog = new AlertDialog.Builder(HomeActivity.this).create();
                    alertDialog.setTitle("Congratulations!");
                    alertDialog.setMessage("By walking " + userSteps + " steps, you have earned a hint coin");

                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Continue",
                            new DialogInterface.OnClickListener()
                            {
                                @Override
                                public void onClick(DialogInterface dialog, int i)
                                {
                                    dialog.dismiss();
                                }
                            });

                    alertDialog.show();
                }

                //Applies user steps into step counter
                textViewStepCounter.setText("Steps walked: " + String.valueOf(userSteps));
            }
            //Applies stepCount to oldStepCount to ensure steps can only increase if stepCount increases.
            oldStepCount = stepCount;

            //Saves oldStepCount into shared preferences to recycle the method.
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            SharedPreferences.Editor editor = sharedPreferences.edit();

            editor.putInt("User steps before next activity", oldStepCount);
            editor.apply();
        }
    }

    //Mot Required
    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    //Removes sensor listener on app destroy
    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        sensorManager.unregisterListener(this, pedometer);
    }

    //Method for when app is on pause, what does the listener do.
    @Override
    protected void onPause()
    {

        super.onPause();

        //User has picked to keep sleep mode on during pause
        if (userAllowsTrackingOnPause == true)
        {
            //Allow step count to continue on pause
        }
        //User has chosen to not allow sensor tracking on pause
        else if (userAllowsTrackingOnPause == false)
        {
            //Unregisters listener/sensor when user leaves app
            if (sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER) != null)
            {
                sensorManager.unregisterListener(this, pedometer);
            }
        }
    }

    //When app resumes
    @Override
    protected void onResume()
    {
        super.onResume();
        //If sensor can be found
        if (sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER) !=null)
        {
            //Begin sensor
            sensorManager.registerListener(this, pedometer, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }
}