package com.example.android.rodar.services;

import com.example.android.rodar.TesteUser;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface UsuarioService {

    @GET("users")
    Call<List<TesteUser>> getUsers();
}
