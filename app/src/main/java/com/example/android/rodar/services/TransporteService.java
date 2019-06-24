package com.example.android.rodar.services;

import com.example.android.rodar.models.AvaliacaoTransporte;
import com.example.android.rodar.models.MensagemTransporte;
import com.example.android.rodar.models.Transporte;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
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

    @DELETE("EventoTransporte/Excluir")
    Call <ResponseBody> deleteTransporte(@Header("Authorization") String token, @Query("idEventoTransporte") int idEventoTransporte);

    @POST ("EventoTransporte/AdicionarParticipacaoTransporte")
    Call <ResponseBody> participarTransporte(@Header("Authorization") String token, @Query("idEventoTransporte") int idEventoTransporte);

    @DELETE ("EventoTransporte/RemoverParticipacaoTransporte")
    Call <ResponseBody> sairTransporte(@Header("Authorization") String token, @Query("idEventoTransporte") int idEventoTransporte);

    @GET ("EventoTransporte/BuscarAtivos")
    Call <List<Transporte>> getAtivos(@Header("Authorization") String token);

    @GET ("EventoTransporte/BuscarHistorico")
    Call <List<Transporte>> getHistorico(@Header("Authorization") String token);

    @POST ("EventoTransporte/AvaliarTransporte")
    Call <ResponseBody> avaliarTransporte(@Header("Authorization") String token, @Body AvaliacaoTransporte avaliacao);

    @GET ("EventoTransporte/BuscarCabecalhoMensagensUsuario")
    Call <List<MensagemTransporte>> getCabecalhoMensagensUsuario(@Header("Authorization") String token);

    @GET ("EventoTransporte/BuscarMensagensUsuario")
    Call <List<MensagemTransporte>> getMensagensUsuario(@Header("Authorization") String token, @Query("idEventoTransporte") int idEventoTransporte);

    @POST ("EventoTransporte/EnviarMensagem")
    Call <ResponseBody> enviarMensagem(@Header("Authorization") String token, @Body MensagemTransporte mensagemTransporte);
}