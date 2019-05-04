package com.example.android.rodar.services;

import com.example.android.rodar.models.Carona;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface CaronaService {

    @POST ("EventoCarona/Cadastrar")
    Call<ResponseBody> createCarona(@Header("Authorization") String token, @Body Carona carona);

    @GET ("EventoCarona/BuscarTodos")
    Call<List<Carona>> getCaronas(@Header("Authorization") String token);
}