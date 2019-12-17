package com.example.debian.ert;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

public class AlertDetails extends Application {
    public static final String CHANNEL_ID = "channel_1";

    @Override
    public void onCreate() {
        super.onCreate();
//        setContentView(R.layout.activity_alert_class);
        createNotificationChannel();
    }
    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            CharSequence name = getString(R.string.channel_name);
            String description = getString(R.string.channel_description);
//            int importance = NotificationManager.IMPORTANCE_HIGH;

            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID,
                    "Description",
                    NotificationManager.IMPORTANCE_HIGH);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);

        }
    }

}
