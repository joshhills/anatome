package io.wellbeings.anatome;

import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Vibrator;

import java.util.Timer;
import java.util.TimerTask;

/**
 * A class to handle pushing notifications
 * from many areas of the app to the device.
 *
 * @author Team WellBeings - Calum, Josh
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
        ((Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE)).vibrate(200); //vibrate
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

        // Schedule the task after set delay
        timer.schedule(task, delay);
    }

    public static void NetworkErrorDialog(Context ctx) {
        new AlertDialog.Builder(ctx)
                .setTitle(
                        UtilityManager.getContentLoader(ctx).getButtonText("oops")
                )
                .setMessage(
                        UtilityManager.getContentLoader(ctx).getNotificationText("network-error")
                )
                .setPositiveButton(
                        UtilityManager.getContentLoader(ctx).getButtonText("ok"),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // continue with delete
                                dialog.cancel();
                            }
                        })
                .show();
    }

}
