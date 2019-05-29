package com.example.android.rodar.Utils;

import android.content.Context;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;

import com.example.android.rodar.R;
import com.example.android.rodar.activities.MainActivity;

public class NotificationHelper {

    public static void mostraNotificacao(Context context, String titulo, String corpo){
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(context, MainActivity.CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_directions_bus_black_24dp)
                .setContentTitle(titulo)
                .setContentInfo(corpo)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat mNotificationMgr = NotificationManagerCompat.from(context);
        mNotificationMgr.notify(1,mBuilder.build());
    }
}
