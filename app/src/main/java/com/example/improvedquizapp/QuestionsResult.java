package com.example.improvedquizapp;

import com.google.gson.annotations.SerializedName;

public class QuestionsResult {
    @SerializedName("question")
    private String question;
    public String getQuestion() {
        return question;
    }
    public void setQuestion(String question){
        this.question = question;
    }

    @SerializedName("incorrectAnswers")
    private String[] incorrectAnswers;
    public String[] getIncorrectAnswers(){ return incorrectAnswers; }
    public void setIncorrectAnswers(String[] incorrectAnswers) {this.incorrectAnswers = incorrectAnswers; }

    @SerializedName("incorrect1")
    private String incorrect1;
    public String getIncorrect1() {
        return incorrect1;
    }
    public void setIncorrect1(String incorrect1){
        this.incorrect1 = incorrect1;
    }

    @SerializedName("incorrect2")
    private String incorrect2;
    public String getIncorrect2() {
        return incorrect2;
    }
    public void setIncorrect2(String incorrect2){
        this.incorrect2 = incorrect2;
    }

    @SerializedName("incorrect3")
    private String incorrect3;
    public String getIncorrect3() {
        return incorrect3;
    }
    public void setIncorrect3(String incorrect3){
        this.incorrect3 = incorrect3;
    }

    @SerializedName("correctAnswer")
    private String correctAnswer;
    public String getCorrectAnswer() {
        return correctAnswer;
    }
    public void setCorrectAnswer(String correctAnswer){
        this.correctAnswer = correctAnswer;
    }

    public QuestionsResult(String question, String incorrect1, String incorrect2, String incorrect3, String correctAnswer) {
        this.question = question;
        this.incorrect1 = incorrect1;
        this.incorrect2 = incorrect2;
        this.incorrect3 = incorrect3;
        this.correctAnswer = correctAnswer;
    }


    protected boolean isCorrectAnswer(String answer) {
        if (this.correctAnswer == null) {
            return false;
        }
        if (getCorrectAnswer().equals(answer)){
            return true;
        }
        return false;
    }

}

