package com.KD.Game;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

// The class has to extend the BroadcastReceiver to get the notification from the system
public class KineticNotification extends BroadcastReceiver {

    @SuppressWarnings("deprecation")
    @Override
    public void onReceive(Context context, Intent paramIntent) {

        // Request the notification manager
        NotificationManager notificationManager = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);

        // Create a new intent which will be fired if you click on the
        // notification
        //Intent intent = new Intent("android.intent.action.VIEW");
        Intent intent = new Intent(context, KineticDefender.class);
        //intent.setData(Uri.parse("http://www.liha.com.ar"));

        // Attach the intent to a pending intent
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0,
                intent, PendingIntent.FLAG_UPDATE_CURRENT);

        // Create the notification
        Notification notification = new Notification(R.drawable.icon,
                "The planet needs you!", System.currentTimeMillis());
        notification.setLatestEventInfo(context, "The planet needs you!",
                "The Earth is under attack, please help us!", pendingIntent);

        // Fire the notification
        notificationManager.notify(1, notification);
    }

}

