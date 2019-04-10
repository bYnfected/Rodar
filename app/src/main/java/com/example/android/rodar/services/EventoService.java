package com.example.android.rodar.services;

import com.example.android.rodar.models.Evento;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface EventoService {

    @POST ("Evento/Cadastrar")
    Call<ResponseBody> createEvento(@Body Evento evento);

}
