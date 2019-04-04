package com.example.android.rodar.services;

import com.example.android.rodar.PreferenceUtils;
import com.example.android.rodar.models.Usuario;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface UsuarioService {

    @POST("Usuario/Cadastrar")
    Call<Integer> createUser(@Body Usuario usuario);

    @POST("Usuario/Atualizar")
    Call<Integer> updateUser(@Header("Authorization") String token,  @Body Usuario usuario);

    @GET("Usuario/Buscar/{id}")
    Call<Usuario> getUser(@Header("Authorization") String token, @Path("id") int id);

    @FormUrlEncoded
    @POST("Login")
    Call<JsonObject>  loginUser(@Field ("username") String usuario, @Field ("password") String password, @Field("grant_type") String grant);
}

