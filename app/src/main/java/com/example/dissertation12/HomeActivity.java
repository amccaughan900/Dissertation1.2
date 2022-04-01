package com.example.dissertation12;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class HomeActivity extends AppCompatActivity implements SensorEventListener
{

    Button solvePuzzles, completePuzzles, howToPlay, logout;
    TextView textViewStepCounter, textViewStepDetector;
    private SensorManager sensorManager;
    private Sensor mStepCounter;
    private boolean isCounterSensorPresent;
    int stepCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        textViewStepCounter = findViewById(R.id.textViewStepCounter);

        solvePuzzles = (Button) findViewById(R.id.btnsolvepuzzles);
        completePuzzles = (Button) findViewById(R.id.btncompletedpuzzles);
        howToPlay = (Button) findViewById(R.id.btnhowtoplay);
        logout = (Button) findViewById(R.id.btnlogout);

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

        if (sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)!=null)
        {
            mStepCounter = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
            isCounterSensorPresent = true;
        }
        else
        {
            textViewStepCounter.setText("Walking sensor not found");
            isCounterSensorPresent = false;
        }

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
                        + "When entering answers, usage of 'The' as the first word of any puzzles is invalid even if the answer is correct logically, this is to eliminate confusion on whether the answer should or should not have 'The' at the beginning of their answer. This does not apply to words that start with 'The' like theme or theory."
                        + System.lineSeparator() + System.lineSeparator()
                        + "Unlocking and using hint coins"
                        + System.lineSeparator() + System.lineSeparator()
                        + "Puzzles have extra clues that can be unlocked with hint coins. These are earned by increasing the step counter, which reads the amount of steps a user has done or by correctly answering two puzzles. Each extra clue will cost 1 hint coin.");
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
    public void onSensorChanged(SensorEvent sensorEvent) {
        if (sensorEvent.sensor == mStepCounter)
        {
            stepCount = (int) sensorEvent.values[0];
            //String steps = String.valueOf(event.values[0]);
            textViewStepCounter.setText("Steps walked: " + String.valueOf(stepCount));
            if (stepCount % 100 == 0)
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
    protected void onPause()
    {
        super.onPause();
        if (sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER) !=null)
        {
            sensorManager.unregisterListener(this, mStepCounter);
        }
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        if (sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER) !=null)
        {
            sensorManager.registerListener(this, mStepCounter, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }
}