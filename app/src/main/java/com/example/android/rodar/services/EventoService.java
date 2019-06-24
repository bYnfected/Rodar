package com.example.android.rodar.services;

import com.example.android.rodar.models.Evento;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface EventoService {

    @POST ("Evento/Cadastrar")
    Call<Integer> createEvento(@Header("Authorization") String token, @Body Evento evento);

    @GET("Evento/BuscarTodos")
    Call<List<Evento>> getEventos(@Header("Authorization") String token,
                                  @Query("somenteMeusEventos") boolean somenteMeusEventos,
                                  @Query("somenteMeusFavoritos") boolean somenteMeusFavoritos,
                                  @Query ("nomeEvento") String nomeEvento,
                                  @Query ("cidadeUfEvento") String cidadeEvento,
                                  @Query ("dataEvento") String dataIni);

    @GET("Evento/Buscar")
    Call <Evento> getEvento(@Header("Authorization") String token, @Query("idEvento") int idEvento);

    @DELETE("Evento/Excluir")
    Call <ResponseBody> deleteEvento(@Header("Authorization") String token, @Query("idEvento") int idEvento);

    @GET ("Evento/BuscarListaCidadeUfsExistentesEmEventos")
    Call <List<String>> getCidadesEventos(@Header("Authorization") String token);

    @Multipart
    @POST("Evento/EnviarFoto")
    Call<ResponseBody> enviarFoto(@Header("Authorization") String token,
                                  @Query("idEvento") int idEvento,
                                  @Part MultipartBody.Part foto);
}
