package com.example.dissertation12;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SelectSolvedLocationActivity extends AppCompatActivity
{

    Button home, solvedBallycastle, solvedBelfast;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_solved_location);

        home = findViewById(R.id.btnToHome);
        solvedBallycastle = findViewById(R.id.btnToBallycastleSolved);
        solvedBelfast = findViewById(R.id.btnToBelfastSolved);

        home.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent toRegion = new Intent(getApplicationContext(), HomeActivity.class);
                startActivity(toRegion);
            }
        });

        solvedBallycastle.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(getApplicationContext(), BallycastleSolvedActivity.class);
                startActivity(intent);
            }
        });

        solvedBelfast.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(getApplicationContext(), BelfastSolvedActivity.class);
                startActivity(intent);
            }
        });
    }
}