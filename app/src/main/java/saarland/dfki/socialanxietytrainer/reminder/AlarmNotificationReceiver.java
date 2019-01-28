package saarland.dfki.socialanxietytrainer.reminder;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import saarland.dfki.socialanxietytrainer.MainActivity;
import saarland.dfki.socialanxietytrainer.R;

import static android.app.PendingIntent.FLAG_ONE_SHOT;

/**
 * Tutorial:
 * https://virtuooza.com/android-how-to-set-daily-notification-shows-up-even-after-phone-is-turned-off/
 */
public class AlarmNotificationReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);

        Intent myIntent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(
                context,
                0,
                myIntent,
                FLAG_ONE_SHOT);

        String mesage = "Work continuously on your progress to overcome your fears.\n" +
                "\"Do not anticipate trouble, or worry about what may never happen. Keep in the sunlight.\' - Benjamin Franklin";
        builder.setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_ALL)
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.drawable.ic_sentiment_satisfied_black)
                .setContentTitle("Accomplish your next task!")
                .setContentIntent(pendingIntent)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(mesage))
                .setContentText(mesage)
                .setDefaults(Notification.DEFAULT_LIGHTS | Notification.DEFAULT_SOUND)
                .setContentInfo("Info");

        NotificationManager notificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(1, builder.build());
    }
}
