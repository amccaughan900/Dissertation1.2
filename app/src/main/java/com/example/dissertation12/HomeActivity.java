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

public class HomeActivity extends AppCompatActivity implements SensorEventListener
{
    Button solvePuzzles, completePuzzles, howToPlay, logout, allowTrackerOnSleep;
    TextView textViewStepCounter;
    private SensorManager sensorManager;
    private Sensor pedometer;
    private boolean isCounterSensorPresent;
    int userSteps;
    int oldStepCount;
    int hintCoinsAwarded;
    boolean coinCannotBeAwarded = false;
    private boolean userAllowsTrackingOnPause;

    int spUserID;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        SharedPreferences getUser = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        spUserID = getUser.getInt("id", 0);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        textViewStepCounter = findViewById(R.id.textViewStepCounter);

        solvePuzzles = (Button) findViewById(R.id.btnsolvepuzzles);
        completePuzzles = (Button) findViewById(R.id.btncompletedpuzzles);
        howToPlay = (Button) findViewById(R.id.btnhowtoplay);
        logout = (Button) findViewById(R.id.btnlogout);
        allowTrackerOnSleep = findViewById(R.id.btnAllowUserToTrackDuringSleep);


        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

        if (sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)!=null)
        {
            pedometer = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
            isCounterSensorPresent = true;
        }
        else
        {
            textViewStepCounter.setText("Walking sensor not found");
            isCounterSensorPresent = false;
        }

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        userAllowsTrackingOnPause = sharedPreferences.getBoolean("Tracker Setting", userAllowsTrackingOnPause);

        Log.i("user choice", String.valueOf(userAllowsTrackingOnPause));

        if (isCounterSensorPresent == false)
        {
            allowTrackerOnSleep.setText("Tracker disabled");
        }
        else if (userAllowsTrackingOnPause == true)
        {
            allowTrackerOnSleep.setText("Tracker sleep mode: on");
        }
        else
        {
            allowTrackerOnSleep.setText("Tracker sleep mode: off");
        }

        allowTrackerOnSleep.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if (isCounterSensorPresent == false)
                {
                    //do nothing
                }
                else if (userAllowsTrackingOnPause == true)
                {
                    userAllowsTrackingOnPause = false;

                    SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                    SharedPreferences.Editor editor = sharedPreferences.edit();

                    editor.putBoolean("Tracker Setting", userAllowsTrackingOnPause);
                    editor.apply();

                    allowTrackerOnSleep.setText("Tracker sleep mode: off");
                }
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

        solvePuzzles.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent toRegion = new Intent(getApplicationContext(), LocationActivity.class);
                startActivity(toRegion);
            }
        });

        completePuzzles.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(getApplicationContext(), SelectSolvedLocationActivity.class);
                startActivity(intent);
            }
        });

        howToPlay.setOnClickListener(new View.OnClickListener()
        {   @Override
            public void onClick(View view)
            { AlertDialog alertDialog = new AlertDialog.Builder(HomeActivity.this).create();
                alertDialog.setTitle("How to play");
                alertDialog.setMessage("The aim of the game is to guess puzzles based on businesses and heritage sites in a region. Users are given clues that are word plays or definitions of the words used in the answer."
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
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
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
        });



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

    @Override
    public void onSensorChanged(SensorEvent sensorEvent)
    {
        if (sensorEvent.sensor == pedometer)
        {
            SharedPreferences getOldSteps = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            oldStepCount = getOldSteps.getInt("User steps before next activity", 0);

            int stepCount = (int) sensorEvent.values[0];

            if (stepCount == oldStepCount)
            {
                DBHelper MyDB;
                MyDB = new DBHelper(this);
                int userSteps = MyDB.getUserStepAmount(spUserID);

                textViewStepCounter.setText("Steps walked: " + String.valueOf(userSteps));
            }
            else
            {
                DBHelper MyDB;
                MyDB = new DBHelper(this);
                int userSteps = MyDB.getUserStepAmount(spUserID);

                userSteps = userSteps+ 1;
                MyDB.updateUserStepAmount(spUserID, userSteps);

                if (userSteps % 4000 == 0)
                {
                    hintCoinsAwarded = 1;

                    int userHintAmount = MyDB.getUserHintAmount(spUserID);
                    int updatedUserAmount = userHintAmount + hintCoinsAwarded;
                    MyDB.updateHintAmount(spUserID, updatedUserAmount);

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

                textViewStepCounter.setText("Steps walked: " + String.valueOf(userSteps));

                oldStepCount = stepCount;

                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                SharedPreferences.Editor editor = sharedPreferences.edit();

                editor.putInt("User steps before next activity", oldStepCount);
                editor.apply();
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        sensorManager.unregisterListener(this, pedometer);
    }

    @Override
    protected void onPause()
    {

        super.onPause();

        if (userAllowsTrackingOnPause == true)
        {
            //Allow step count to continue on pause
        }
        else if (userAllowsTrackingOnPause == false)
        {
            if (sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER) != null)
            {
                sensorManager.unregisterListener(this, pedometer);
            }
        }
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        if (sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER) !=null)
        {
            sensorManager.registerListener(this, pedometer, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }
}