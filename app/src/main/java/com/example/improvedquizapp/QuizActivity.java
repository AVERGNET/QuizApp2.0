package com.example.improvedquizapp;

import android.app.Activity;


import android.os.Bundle;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import okhttp3.internal.concurrent.Task;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QuizActivity extends Activity {

    //Widgets
    private ArrayList<QuestionsResult> quizQuestionList = null;
    QuestionsResult currentQuestion = null;
    int currentQuestionNumber = 1;

    private int currentScore = 0;
    private int maxScore = 0;

    TextView textViewQuestionTitle = null;
    TextView textViewQuestion = null;
    TextView textViewScore = null;
    TextView secretAnswer = null;
    RadioGroup radioGroupQuestion = null;
    RadioButton radioButtonA = null;
    RadioButton radioButtonB = null;
    RadioButton radioButtonC = null;
    RadioButton radioButtonD = null;

    Button buttonSubmit = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        // Initialize widgets.
        this.textViewQuestionTitle = (TextView)findViewById(R.id.textViewQuestionTitle);    // The question title
        this.textViewQuestion = (TextView)findViewById(R.id.textViewQuestion);              // The question asked.
        this.textViewScore = (TextView)findViewById(R.id.textViewScore);                    // The current score.
        this.secretAnswer = (TextView)findViewById(R.id.secretAnswer);
        // Initialize the radio buttons for question multiple choice answers.
        radioGroupQuestion = (RadioGroup)findViewById(R.id.radioGroup);             // Create a group for radio buttons.
        radioButtonA = (RadioButton)findViewById(R.id.ChoiceA);
        radioButtonB = (RadioButton)findViewById(R.id.ChoiceB);
        radioButtonC = (RadioButton)findViewById(R.id.ChoiceC);
        radioButtonD = (RadioButton)findViewById(R.id.ChoiceD);
//        String answer = "";
//        try {
//            answer = fetchSingle();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        buttonSubmit = (Button)findViewById(R.id.buttonSubmit);
//        String finalAnswer = answer;
        String finalAnswer = fetchSingle();
        this.buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validateAnswer(finalAnswer)) {
                    if(currentQuestionNumber < maxScore) {
                        currentQuestion = quizQuestionList.get(currentQuestionNumber);
                        setQuestionView(currentQuestion);
                        currentQuestionNumber++;
                    }
                    else {
                        Intent inent = new Intent(com.example.improvedquizapp.QuizActivity.this, com.example.improvedquizapp.ResultsActivity.class);
                        inent.putExtra("current_score", currentScore);
                        inent.putExtra("max_score", maxScore);
                        startActivity(inent);
                    }
                }
            }
        });
    }

    private String fetchSingle(){
        Call<List<QuestionsResult>> call = RetrofitClient.getInstance().getMyApi().fetchQuestion();
        call.enqueue(new Callback<List<QuestionsResult>>() {
            @Override
            public void onResponse(Call<List<QuestionsResult>> call, Response<List<QuestionsResult>> response) {
                List<QuestionsResult> fetchedQuestions = response.body();
                String[] possibleAnswers = new String[4];
                possibleAnswers[0] = fetchedQuestions.get(0).getIncorrectAnswers()[0];
                possibleAnswers[1] = fetchedQuestions.get(0).getIncorrectAnswers()[1];
                possibleAnswers[2] = fetchedQuestions.get(0).getIncorrectAnswers()[2];
                possibleAnswers[3] = fetchedQuestions.get(0).getCorrectAnswer();
                Arrays.sort(possibleAnswers);
                String corAns = fetchedQuestions.get(0).getCorrectAnswer();
                secretAnswer.setText(corAns);
                //setQuestionView
                // Clear the radio button checks just encase it was been set previously.
                radioGroupQuestion.clearCheck();
                textViewScore.setText("Score: " + Integer.toString(currentScore));
                textViewQuestion.setText(fetchedQuestions.get(0).getQuestion());
                radioButtonA.setText(possibleAnswers[0]);
                radioButtonB.setText(possibleAnswers[1]);
                radioButtonC.setText(possibleAnswers[2]);
                radioButtonD.setText(possibleAnswers[3]);
                textViewQuestionTitle.setText("Question #" + currentQuestionNumber);
            }
            @Override
            public void onFailure(Call<List<QuestionsResult>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "An error has occured CUSTOM ", Toast.LENGTH_LONG).show();
            }
        });
        
        String corAns = secretAnswer.getText().toString();
        Log.d("insideFetch", corAns);
        return corAns;
    }

//    private String fetchSingle() throws IOException {
//        Api client = ServiceGenerator.createService(Api.class);
//        Call<List<QuestionsResult>> call = client.fetchQuestion();
//        List<QuestionsResult> fetchedQuestions = call.execute().body();
//        String[] possibleAnswers = new String[4];
//        possibleAnswers[0] = fetchedQuestions.get(0).getIncorrectAnswers()[0];
//        possibleAnswers[1] = fetchedQuestions.get(0).getIncorrectAnswers()[1];
//        possibleAnswers[2] = fetchedQuestions.get(0).getIncorrectAnswers()[2];
//        possibleAnswers[3] = fetchedQuestions.get(0).getCorrectAnswer();
//        Arrays.sort(possibleAnswers);
//        //setQuestionView
//        // Clear the radio button checks just encase it was been set previously.
//        radioGroupQuestion.clearCheck();
//        textViewScore.setText("Score: " + Integer.toString(currentScore));
//        textViewQuestion.setText(fetchedQuestions.get(0).getQuestion());
//        radioButtonA.setText(possibleAnswers[0]);
//        radioButtonB.setText(possibleAnswers[1]);
//        radioButtonC.setText(possibleAnswers[2]);
//        radioButtonD.setText(possibleAnswers[3]);
//        textViewQuestionTitle.setText("Question #" + currentQuestionNumber);
//        return fetchedQuestions.get(0).getCorrectAnswer();
//    }


    private void setQuestionView(QuestionsResult quizQuestion) {
        if(quizQuestion == null) {
            Log.d("[DEBUG]", "quizQuestion is null in setQuestionView.");
            return;
        }
        // Clear the radio button checks just encase it was been set previously.
        radioGroupQuestion.clearCheck();
        textViewScore.setText("Score: " + Integer.toString(currentScore));
        textViewQuestion.setText(currentQuestion.getQuestion());
        radioButtonA.setText(currentQuestion.getIncorrect1());
        radioButtonB.setText(currentQuestion.getIncorrect2());
        radioButtonC.setText(currentQuestion.getIncorrect3());
        radioButtonD.setText(currentQuestion.getCorrectAnswer());
        textViewQuestionTitle.setText("Question #" + currentQuestionNumber);
    }

    private boolean validateAnswer(String corAnswer) {
        // Validate the current answer selected.
        int selectedButtonId = this.radioGroupQuestion.getCheckedRadioButtonId();
        currentQuestion = new QuestionsResult("","","","",corAnswer);
        if(selectedButtonId != -1) {
            String answerSelectedStr = ((RadioButton)findViewById(selectedButtonId)).getText().toString();

            if (currentQuestion.isCorrectAnswer(answerSelectedStr)) {
                // Answer is correct.
                Log.d("ANSWER: ", "Correct");
                currentScore++;
            }
            else {
                Log.d("ANSWER: ", "Incorrect");
            }
            return true; // Allow to continue to next question.
        }
        else {
            // No answer selected.
            Toast.makeText(getApplicationContext(), "Please Select An Answer",
                    Toast.LENGTH_SHORT).show();
            return false;
        }
    }
}