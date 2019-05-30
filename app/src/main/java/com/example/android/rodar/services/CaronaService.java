package com.example.android.rodar.services;

import com.example.android.rodar.models.AvaliacaoCarona;
import com.example.android.rodar.models.Carona;

import java.util.List;

import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface CaronaService {

    @POST ("EventoCarona/Cadastrar")
    Call<ResponseBody> createCarona(@Header("Authorization") String token, @Body Carona carona);

    @GET ("EventoCarona/BuscarTodos")
    Call<List<Carona>> getCaronas(@Header("Authorization") String token);

    @GET ("EventoCarona/BuscarPorEvento")
    Call<List<Carona>> getCaronasEvento(@Header("Authorization") String token, @Query("idEvento") int idEvento);

    @DELETE ("EventoCarona/Excluir")
    Call <ResponseBody> deleteCarona(@Header("Authorization") String token, @Query("idEventoCarona") int idEventoCarona);

    @POST ("EventoCarona/AdicionarParticipacaoCarona")
    Call <ResponseBody> participarCarona(@Header("Authorization") String token, @Query("idEventoCarona") int idEventoCarona);

    @DELETE ("EventoCarona/RemoverParticipacaoCarona")
    Call <ResponseBody> sairCarona(@Header("Authorization") String token, @Query("idEventoCarona") int idEventoCarona);

    @GET ("EventoCarona/BuscarAtivos")
    Call <List<Carona>> getAtivos(@Header("Authorization") String token);

    @GET ("EventoCarona/BuscarHistorico")
    Call <List<Carona>> getHistorico(@Header("Authorization") String token);

    @POST ("EventoCarona/AvaliarCarona")
    Call <ResponseBody> avaliarCarona(@Header("Authorization") String token, AvaliacaoCarona avaliacao);
}