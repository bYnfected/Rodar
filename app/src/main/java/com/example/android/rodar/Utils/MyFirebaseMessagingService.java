package com.example.android.rodar.Utils;

import android.util.Log;

import com.example.android.rodar.services.UsuarioService;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    @Override
    public void onNewToken(final String s) {
        // Aqui envia para o servidor o novo token
        super.onNewToken(s);
        // Se ja esta cadastrado, atualiza no servidor, se primeira abertura do software,salva em memoria
        if (SPUtil.getToken(getApplicationContext()) == null){
            SPUtil.saveTokenFirebase(getApplicationContext(),s);
        }
        else{
            UsuarioService service = RetrofitClient.getClient().create(UsuarioService.class);
            Call<ResponseBody> call = service.setTokenFirebase(SPUtil.getToken(getApplicationContext()),s);
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if (response.isSuccessful()){
                        Log.d("Notificacao", "Refreshed token: " + s);
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {

                }
            });
        }

    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        if(remoteMessage.getNotification() != null){
            String title = remoteMessage.getNotification().getTitle();
            String body = remoteMessage.getNotification().getBody();
            NotificationHelper.mostraNotificacao(getApplicationContext(),title,body);
        }
    }
}