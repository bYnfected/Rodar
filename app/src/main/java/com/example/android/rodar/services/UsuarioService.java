package com.example.android.rodar.services;

import com.example.android.rodar.models.Usuario;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface UsuarioService {

    @GET("Usuario/Buscar/{id}")
    Call<Usuario> getUser(@Path("id") int id);

    @POST("Usuario/Atualizar")
    Call<Integer> updateUser(@Body Usuario usuario);

    @POST("Usuario/Cadastrar")
    Call<Integer> createUser(@Body Usuario usuario);

    @FormUrlEncoded
    @POST("Login")
    Call<JsonObject>  loginUser(@Field ("username") String usuario, @Field ("password") String password, @Field("grant_type") String grant);
}

