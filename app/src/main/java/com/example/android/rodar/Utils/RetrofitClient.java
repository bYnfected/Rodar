package com.example.android.rodar.Utils;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    //private static final String BASE_URL = "http://192.168.100.54:50081/api/";
    private static final String BASE_URL = "http://192.168.107.7/Rodar/api/";
    private static Retrofit retrofit = null;


    public static Retrofit getClient(){
        if(retrofit == null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}