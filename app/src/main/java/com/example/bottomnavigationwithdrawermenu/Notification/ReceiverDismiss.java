package com.example.bottomnavigationwithdrawermenu.Notification;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;
import android.util.Log;
import androidx.core.app.NotificationManagerCompat;

public class ReceiverDismiss extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationManagerCompat compat = NotificationManagerCompat.from(context);
        compat.cancel(2);

        String messageText = intent.getStringExtra("dtoast");
        Toast.makeText(context, messageText, Toast.LENGTH_SHORT).show();

        Log.d("WARNING","my program go to this part");
    }
}