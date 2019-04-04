package com.example.android.rodar.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.widget.Toast;

import com.example.android.rodar.PreferenceUtils;
import com.example.android.rodar.RetrofitClient;
import com.example.android.rodar.services.UsuarioService;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        // Vai direto para a MainActivity se esta logado
        if (PreferenceUtils.getPassword(getApplicationContext()) != null) {
            UsuarioService usrService = RetrofitClient.getClient().create(UsuarioService.class);
            Call<JsonObject> call = usrService.loginUser(PreferenceUtils.getEmail(this), PreferenceUtils.getPassword(this), "password");
            call.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                    if (response.isSuccessful()) {
                        JsonObject resposta = response.body();
                        if (resposta.has("access_token")) {
                            PreferenceUtils.saveToken(resposta.get("access_token").toString(), getApplicationContext());

                            Intent activityIntent = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(activityIntent);
                            finish();
                        }
                    } else {
                        abrirLogin();
                    }
                }

                @Override
                public void onFailure(Call<JsonObject> call, Throwable t) {
                    //Toast.makeText(getApplicationContext(), "Erro ao se conectar no servidor", Toast.LENGTH_LONG).show();
                    abrirLogin();
                }
            });
        } else {
            abrirLogin();
        }
    }

    private void abrirLogin(){
        Intent activityIntent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(activityIntent);
        finish();
    }
}
