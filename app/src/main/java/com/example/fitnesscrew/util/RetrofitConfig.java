package com.example.fitnesscrew.util;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class RetrofitConfig {

    private final Retrofit retrofit;

    public RetrofitConfig() {
        this.retrofit = new Retrofit.Builder()
                .baseUrl("http://anguloconsulta.com.br:9090/")
                //.addConverterFactory(GsonConverterFactory.create()) //For Json
                .addConverterFactory(ScalarsConverterFactory.create()) //For Text/Plain
                .build();
    }


    public Test getTest() {
        return this.retrofit.create(Test.class);
    }

}
