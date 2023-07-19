package com.example.bottomnavigationwithdrawermenu.Notification;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import androidx.core.app.NotificationManagerCompat;



public class Receiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String messageText = intent.getStringExtra("toast");
        //Toast.makeText(context, messageText, Toast.LENGTH_LONG).show();
        //Toast.makeText(context, "RECIBIER NOTIFICATION", Toast.LENGTH_LONG).show();

        show(context,"Registro");

        // Cancelar la notificación
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.cancel(1);
    }
    private void show(Context context,String txt){
        Toast.makeText(context,txt, Toast.LENGTH_LONG).show();
    }
}

