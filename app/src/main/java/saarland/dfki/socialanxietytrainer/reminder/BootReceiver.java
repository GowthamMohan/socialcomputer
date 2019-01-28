package saarland.dfki.socialanxietytrainer.reminder;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Tutorial:
 * https://virtuooza.com/android-how-to-set-daily-notification-shows-up-even-after-phone-is-turned-off/
 */
public class BootReceiver extends BroadcastReceiver {
    public void onReceive(Context context, Intent intent) {
        Intent i = new Intent(context, NotificationService.class);
        context.startService(i);
    }
}
