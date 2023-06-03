package com.example.saversidekick;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class PaymentReminderReceiver extends BroadcastReceiver {
        private static final String CHANNEL_ID = "PAYMENT_REMINDER_CHANNEL";
        private static final int NOTIFICATION_ID = 1;
        public static final String REMINDER_EXTRA = "REMINDER_EXTRA";

        @Override
        public void onReceive(Context context, Intent intent) {
            // Retrieve the reminder data from the intent
            String reminderText = intent.getStringExtra(REMINDER_EXTRA);

            // Create a notification channel for Android 8.0 and higher
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                NotificationChannel channel = new NotificationChannel(CHANNEL_ID, "Payment Reminder", NotificationManager.IMPORTANCE_DEFAULT);
                NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
                notificationManager.createNotificationChannel(channel);
            }

            // Create the notification with the reminder data
            NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                    .setSmallIcon(R.drawable.saversidekick3)
                    .setContentTitle("Saver Sidekick Reminder")
                    .setContentText(reminderText)
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    .setAutoCancel(true);

            // Show the notification
            NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);
            notificationManagerCompat.notify(NOTIFICATION_ID, builder.build());
        }
}
