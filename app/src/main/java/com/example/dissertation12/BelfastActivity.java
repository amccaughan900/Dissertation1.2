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
    TextView textview_puzzleSelected;
    EditText edittext_answer;
    Button btn_answerPuzzle;

    //Arrays for spinners to adapt
    String[] belfastPuzzles = new String[]
            { "Puzzle 1: This peeling vegetable is not in a good condition.",
            "Puzzle 2: This pub's name suggests it's holding some sort of score.",
            "Puzzle 3: An opaque, all-black gemstone."
            };

    int currentItem;

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

        btn_answerPuzzle = (Button) findViewById(R.id.btnAnswer);

        MyDB = new DBHelper(BelfastActivity.this);

        //Makes the drop down list for valence level by setting an adapter onto the spinner containing vItems array
        belfastAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, belfastPuzzles);
        belfastAdapter.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        spinner_belfast.setAdapter(belfastAdapter);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        int spUserID = sharedPreferences.getInt("id", 0);

        spinner_belfast.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l)
            {
                String itemPuzzle = spinner_belfast.getSelectedItem().toString();
                textview_puzzleSelected.setText(itemPuzzle);
                currentItem = i + 4;
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
                    Toast.makeText(BelfastActivity.this, "Please enter an answer", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Boolean checkPuzzleSolved = MyDB.checkPuzzleSolved(spUserID, currentItem);

                    if(checkPuzzleSolved==false)
                    {
                        Boolean checkAnswer = MyDB.checkPuzzleAnswer(currentItem, userAnswer);

                        if (checkAnswer == true)
                        {
                            Boolean correctAnswer = MyDB.insertSolvedAnswer(spUserID, currentItem);
                            if (correctAnswer == true)
                            {
                                Toast.makeText(BelfastActivity.this, "Correct, the answer is " + userAnswer, Toast.LENGTH_SHORT).show();
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