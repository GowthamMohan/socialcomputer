package saarland.dfki.socialanxietytrainer.reminder;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;

import java.util.Calendar;
import java.util.Date;

public class NotificationService extends Service {

    // Scheduled time
    public static final int HOUR = 12;
    public static final int MIN = 0;

    public NotificationService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        setupNotifications();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_NOT_STICKY;
    }

    private void setupNotifications() {
        AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        Intent myIntent = new Intent(this,AlarmNotificationReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this,0,myIntent,0);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.HOUR_OF_DAY, NotificationService.HOUR);
            calendar.set(Calendar.MINUTE, NotificationService.MIN);
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,
                    calendar.getTimeInMillis(),
                    AlarmManager.INTERVAL_DAY,
                    pendingIntent);
        } else {
            Date date = new Date();
            date.setHours(NotificationService.HOUR);
            date.setMinutes(NotificationService.MIN);
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,
                    date.getTime(),
                    AlarmManager.INTERVAL_DAY,
                    pendingIntent);
        }
    }
}
