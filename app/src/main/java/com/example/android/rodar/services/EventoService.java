package com.example.android.rodar.services;

import com.example.android.rodar.models.Evento;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface EventoService {

    @POST ("Evento/Cadastrar")
    Call<ResponseBody> createEvento(@Header("Authorization") String token, @Body Evento evento);

    @GET("Evento/BuscarTodos")
    Call<List<Evento>> getEventos(@Header("Authorization") String token, @Query("somenteMeusEventos") boolean somenteMeusEventos,  @Query("somenteMeusFavoritos") boolean somenteMeusFavoritos);

    @GET("Evento/Buscar")
    Call <Evento> getEvento(@Header("Authorization") String token, @Query("idEvento") int idEvento);

    @DELETE("Evento/Excluir")
    Call <ResponseBody> deleteEvento(@Header("Authorization") String token, @Query("idEvento") int idEvento);
}
