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

    private static final String[] ACTIVTITY_PERMISSION = {
        Manifest.permission.ACTIVITY_RECOGNITION
    };

    Button solvePuzzles, completePuzzles, howToPlay, logout, allowTrackerOnSleep;
    TextView textViewStepCounter;
    private SensorManager sensorManager;
    private Sensor pedometer;
    private boolean isCounterSensorPresent;
    int stepCount = 0;
    private boolean userAllowsTrackingOnPause;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        verifyPermission();

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
        {
            @Override
            public void onClick(View view)
            {
                AlertDialog alertDialog = new AlertDialog.Builder(HomeActivity.this).create();
                alertDialog.setTitle("How to play");
                alertDialog.setMessage("The aim of the game is to guess puzzles based on businesses and heritage sites in a region. Users are given clues that are word plays or definitions of the words used in the answer."
                        + System.lineSeparator() + System.lineSeparator()
                        + "An example of what a puzzle could look like is:" + System.lineSeparator() + System.lineSeparator()
                        + "Puzzle: This place is royalty when it comes to patties."
                        + System.lineSeparator() + System.lineSeparator()
                        + "Answer: Burger King"
                        + System.lineSeparator() + System.lineSeparator()
                        + "WARNING"
                        + System.lineSeparator() + System.lineSeparator()
                        + "When entering answers, there can be up to two different answers. Both are the same answer but just a slight variation if a business may have extra words. An example might be chipco or chipco fish and chips. The use of 'The ' as the first word is not allowed when answering. Ever business could logically have 'the ' therefore being redundant."
                        + System.lineSeparator() + System.lineSeparator()
                        + "Unlocking and using hint coins"
                        + System.lineSeparator() + System.lineSeparator()
                        + "Puzzles have extra clues that can be unlocked with hint coins. These are earned by walking 2000 steps so this can be 2000, 4000, 2000 x n, etc, or by correctly answering four puzzles in one region. Each hint will cost 1 hint coin."
                        + System.lineSeparator() + System.lineSeparator()
                        + "Users can turn tracking on and off during sleep at any time on the home screen. If you are confused about what sleep means, It means when the user has the app active but not currently opened. This means you can earn coins without having to keep the screen on as long as you're walking and the button 'tracker on sleep' is switched to on. Ensure the text on the button is on for sleep mode to be on. Once the app is destroyed, it will stop."
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

    private void verifyPermission()
    {
        Log.d(TAG,"verify Permissions: checking Permisssions.");

        int permisssionPedometer = ActivityCompat.checkSelfPermission(HomeActivity.this, Manifest.permission.ACTIVITY_RECOGNITION);

        if (permisssionPedometer != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(HomeActivity.this, ACTIVTITY_PERMISSION, 1);
        }
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if (sensorEvent.sensor == pedometer)
        {
            stepCount = (int) sensorEvent.values[0];
            //String steps = String.valueOf(event.values[0]);
            textViewStepCounter.setText("Steps walked: " + String.valueOf(stepCount));
            if (stepCount % 2000 == 0)
            {

                AlertDialog alertDialog = new AlertDialog.Builder(HomeActivity.this).create();
                alertDialog.setTitle("Congratulations!");
                alertDialog.setMessage("By walking " + stepCount + " steps, you have earned a hint coin");

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
            //Do nothing
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