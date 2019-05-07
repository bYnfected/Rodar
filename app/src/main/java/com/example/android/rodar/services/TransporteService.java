package com.example.android.rodar.services;

import com.example.android.rodar.models.Transporte;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface TransporteService {

    @POST ("EventoTransporte/Cadastrar")
    Call<ResponseBody> createTransporte(@Header("Authorization") String token, @Body Transporte transporte);

    @GET ("EventoTransporte/BuscarTodos")
    Call<List<Transporte>> getTransporte(@Header("Authorization") String token);

    @GET ("EventoTransporte/BuscarPorEvento")
    Call<List<Transporte>> getTransportesEvento(@Header("Authorization") String token, @Query("idEvento") int idEvento);
}
