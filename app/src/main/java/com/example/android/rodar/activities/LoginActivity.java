package com.example.android.rodar.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.example.android.rodar.FragmentInicial;
import com.example.android.rodar.PreferenceUtils;
import com.example.android.rodar.R;
import com.example.android.rodar.RetrofitClient;
import com.example.android.rodar.services.UsuarioService;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity implements ILoginActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getSupportFragmentManager().beginTransaction().replace(R.id.inicial_fragment_container, new FragmentInicial()).commit();
    }


    @Override
    public void loginUsuario() {
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
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "Usuário ou senha inválido", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Erro ao se conectar no servidor", Toast.LENGTH_LONG).show();
            }
        });

    }
}
