package com.example.android.rodar.Utils;

import android.util.Log;

import com.example.android.rodar.activities.MainActivity;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    @Override
    public void onNewToken(String s) {
        // Aqui envia para o servidor o novo token
        super.onNewToken(s);
        Log.d("Notificacao", "Refreshed token: " + s);
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