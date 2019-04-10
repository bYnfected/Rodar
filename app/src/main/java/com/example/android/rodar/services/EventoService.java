package com.example.android.rodar.services;

import com.example.android.rodar.models.Evento;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface EventoService {

    @POST ("Evento/Cadastrar")
    Call<ResponseBody> createEvento(@Body Evento evento);

    @GET("Evento/BuscarTodos")
    Call<List<Evento>> getEventos(@Header("Authorization") String token);
}
