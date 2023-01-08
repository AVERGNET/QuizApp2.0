package com.example.improvedquizapp;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface Api {

    String BASE_URL = "https://the-trivia-api.com/api/";
    @GET("questions?limit=1")
    Call<List<QuestionsResult>> fetchQuestion();
}
