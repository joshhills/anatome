package io.wellbeings.anatome;

import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

import java.util.Timer;
import java.util.TimerTask;

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

    //overload that includes a delay before sending the notification
    public static void pushNotification(final Context context, final String title, final String content, int delay) {
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                pushNotification(context, title, content);
            }
        };

        //schedule the task after set delay
        timer.schedule(task, delay);
    }

    public static void NetworkErrorDialog(Context ctx) {
        new AlertDialog.Builder(ctx)
                .setTitle("Oops...")
                .setMessage("Looks like you're not connected to the internet. Check your settings and try again. Some features will not function without internet.")
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // continue with delete
                        dialog.cancel();
                    }
                })
                .show();
    }

}
