package com.example.android.rodar.services;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface FavoritoService {

    @POST ("EventoUsuarioFavorito/AdicionarFavorito")
    Call<ResponseBody> adicionarFavorito(@Header("Authorization") String token, @Query("idEvento") int idEvento);

    @DELETE ("EventoUsuarioFavorito/RemoverFavorito")
    Call<ResponseBody> removerFavorito(@Header("Authorization") String token, @Query("idEvento") int idEvento);
}
