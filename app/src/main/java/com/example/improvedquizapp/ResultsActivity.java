package com.example.improvedquizapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class ResultsActivity extends Activity {
    int curScore = 0;
    int maxScore = 0;

    TextView textViewScoreCurrent = null;
    TextView textViewScoreMax = null;
    TextView textViewResultDescription = null;

    Button buttonPlayAgain = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restult);

        Intent inent = getIntent();
        int curScore = inent.getIntExtra("current_score", 1);
        int maxScore = inent.getIntExtra("max_score", 1);

        String toStringCurScore = Integer.toString(curScore);
        String toStringMaxScore = Integer.toString(maxScore);

        textViewScoreCurrent = (TextView)findViewById(R.id.textViewScoreCurrent);
        textViewScoreCurrent.setText(toStringCurScore);

        textViewScoreMax = (TextView)findViewById(R.id.textViewScoreMax);
        textViewScoreMax.setText(toStringMaxScore);

        textViewResultDescription = (TextView)findViewById(R.id.textViewResultDescription);
        textViewResultDescription.setText("You answered " + getPercentage(curScore, maxScore) + "of the quiz question correctly");

        buttonPlayAgain = (Button)findViewById(R.id.buttonPlayAgain);
        this.buttonPlayAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ient = new Intent(com.example.improvedquizapp.ResultsActivity.this, com.example.improvedquizapp.MainActivity.class);
                startActivity(ient);
            }
        });
    }

    private String getPercentage(int current, int total) {
        float fPercent = (float)current/(float)total;
        return Integer.toString((int)Math.ceil((fPercent) * 100)) + "%";
    }
}
