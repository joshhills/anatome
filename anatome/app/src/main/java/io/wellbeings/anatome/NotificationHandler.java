package io.wellbeings.anatome;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

/**
 * Created by Calum on 02/04/2016.
 * Purpose: A class to handle push notifications from all areas of the app
 */
public class NotificationHandler {
    //method for sending a push notification to the user
    public static void pushNotification(Context context, String title, String content) {
        //create a notification manager for handling push notifications
        NotificationManager NM;
        NM =(NotificationManager)context.getSystemService(context.NOTIFICATION_SERVICE);

        /*create a new notification builder for the push notification,
          and assign it content, a title and an icon*/
        Notification notification = new Notification.Builder(context)
                .setContentTitle(title)
                .setContentText(content)
                .setSmallIcon(R.mipmap.ic_launcher)
                .build();

        NM.notify(0, notification); //fire the actual notification
    }
}
