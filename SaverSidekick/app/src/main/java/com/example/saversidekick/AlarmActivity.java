package com.example.saversidekick;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import java.util.Calendar;


// in this activity a user can set three alarms to personal high-spend times of the day as a reminder not to spend
public class AlarmActivity extends AppCompatActivity {

    private static final String CHANNEL_ID = "channel_id";  // notification channel id
    private static final int NOTIFICATION_ID_1 = 1;     // notification id 1 for first set reminder
    private static final int NOTIFICATION_ID_2 = 2;     // notification id 2 for second set reminder
    private static final int NOTIFICATION_ID_3 = 3;     // notification id 3 for third set reminder

    // buttons and edit text objects for all three reminders
    private Button alertBtn1;
    private Button alertBtn2;
    private Button alertBtn3;
    private Button homeButton;
    private EditText hourInput1;
    private EditText minuteInput1;
    private EditText secondInput1;
    private EditText hourInput2;
    private EditText minuteInput2;
    private EditText secondInput2;
    private EditText hourInput3;
    private EditText minuteInput3;
    private EditText secondInput3;
    private EditText msgInput1;
    private EditText msgInput2;
    private EditText msgInput3;

    // when activity is created
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);        // set layout view to activity_alarm.xml

        //initialise front-end view edit texts, set reminder buttons and home button
        alertBtn1 = findViewById(R.id.button_1);                    // initialise first button
        alertBtn2 = findViewById(R.id.button_2);                    // initialise second button
        alertBtn3 = findViewById(R.id.button_3);                    // initialise third button
        hourInput1 = findViewById(R.id.hours_edit_text_1);          // initialise user input for the hour for reminder 1
        minuteInput1 = findViewById(R.id.minutes_edit_text_1);      // initialise user input for the minute for reminder 1
        secondInput1 = findViewById(R.id.seconds_edit_text_1);      // initialise user input for the second for reminder 1
        hourInput2 = findViewById(R.id.hours_edit_text_2);          // initialise user input for the hour for reminder 2
        minuteInput2 = findViewById(R.id.minutes_edit_text_2);      // initialise user input for the minute for reminder 2
        secondInput2 = findViewById(R.id.seconds_edit_text_2);      // initialise user input for the second for reminder 2
        hourInput3 = findViewById(R.id.hours_edit_text_3);          // initialise user input for the hour for reminder 3
        minuteInput3 = findViewById(R.id.minutes_edit_text_3);      // initialise user input for the minute for reminder 3
        secondInput3 = findViewById(R.id.seconds_edit_text_3);      // initialise user input for the second for reminder 3
        msgInput1 = findViewById(R.id.message_edit_text_1);         // initialise user input for the message for reminder 1
        msgInput2 = findViewById(R.id.message_edit_text_2);         // initialise user input for the message for reminder 2
        msgInput3 = findViewById(R.id.message_edit_text_3);         // initialise user input for the message for reminder 3
        homeButton = findViewById(R.id.homebutton);                 // initialise the return to main/home button

        // message when open to remind user to set notficiations for the application on this device
        Toast.makeText(getApplicationContext(), "Notifications must be turned on in device settings for Saver Sidekick", Toast.LENGTH_LONG).show();

        //function for the home button to take the user back to the main page
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        // function to create reminder 1 by calling createNotification on button click
        alertBtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideKeyboard(v);
                createNotification(NOTIFICATION_ID_1, hourInput1, minuteInput1, secondInput1, msgInput1);
            }
        });

        // function to create reminder 2 by calling createNotification on button click
        alertBtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideKeyboard(v);
                createNotification(NOTIFICATION_ID_2, hourInput2, minuteInput2, secondInput2, msgInput2);
            }
        });

        // function to create reminder 3 by calling createNotification on button click
        alertBtn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideKeyboard(v);
                createNotification(NOTIFICATION_ID_3, hourInput3, minuteInput3, secondInput3, msgInput3);
            }
        });

        // create a notification channel, necessary for new android devices to show notifications
        createNotificationChannel();
    }

    //function to create a notification channel, update channel settings on device to make sure notifications are visible
    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Spending Alert Channel";
            String description = "Channel shows alerts set by users for high-spend times of the day";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;

            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    //function to create and show an notification on the specified time with the specified message
    private void createNotification(final int notificationId, EditText hoursEditText, EditText minutesEditText, EditText secondsEditText, EditText messageEditText) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.VIBRATE) == PackageManager.PERMISSION_GRANTED) {
            int hourOfDay = Integer.parseInt(hoursEditText.getText().toString());
            int minutes = Integer.parseInt(minutesEditText.getText().toString());
            int seconds = Integer.parseInt(secondsEditText.getText().toString());

            // Calculate the current time in milliseconds
            long currentTimeMillis = System.currentTimeMillis();

            // Calendar instance is set with the current time in millis
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(currentTimeMillis);

            // set the hour, minute and second in the calendar instance
            calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);      // set the hours on the calender instance
            calendar.set(Calendar.MINUTE, minutes);             // set the minutes on the calender instance
            calendar.set(Calendar.SECOND, seconds);             // set the seconds on the calender instance

            // Check if the time the user set has already passed for today, if yes, add one day
            if (calendar.getTimeInMillis() < currentTimeMillis) {
                calendar.add(Calendar.DAY_OF_MONTH, 1);
            }

            // Calculate how long the difference is between set and current time in milliseconds
            long delayMillis = calendar.getTimeInMillis() - currentTimeMillis;

            // Change button text to show user how long until notification will go off
            switch (notificationId) {
                case NOTIFICATION_ID_1:
                    alertBtn1.setText("Spending Alert 1 set for " + delayMillis / 1000 + " seconds");
                    break;
                case NOTIFICATION_ID_2:
                    alertBtn2.setText("Spending Alert 2 set for " + delayMillis / 1000 + " seconds");
                    break;
                case NOTIFICATION_ID_3:
                    alertBtn3.setText("Spending Alert 3 set for " + delayMillis / 1000 + " seconds");
                    break;
            }

            // Create a handler to set the delay
            new Handler().postDelayed(new Runnable() {
                @SuppressLint("MissingPermission")
                @Override
                public void run() {
                    // Create the notification after the delay
                    String message = messageEditText.getText().toString();

                    NotificationCompat.Builder builder = new NotificationCompat.Builder(AlarmActivity.this, CHANNEL_ID)
                            .setSmallIcon(R.drawable.ic_notification)
                            .setContentTitle("Spending Reminder")
                            .setContentText(message)
                            .setPriority(NotificationCompat.PRIORITY_HIGH) // Set priority to high
                            .setDefaults(Notification.DEFAULT_ALL); // Enable all notification behaviors

                    NotificationManagerCompat notificationManager = NotificationManagerCompat.from(AlarmActivity.this);
                    notificationManager.notify(notificationId, builder.build());

                    // Reset button to its original text
                    switch (notificationId) {
                        case NOTIFICATION_ID_1:
                            alertBtn1.setText("Create Spending Reminder 1");
                            break;
                        case NOTIFICATION_ID_2:
                            alertBtn2.setText("Create Spending Reminder 2");
                            break;
                        case NOTIFICATION_ID_3:
                            alertBtn3.setText("Create Spending Reminder 3");
                            break;
                    }
                }
            }, delayMillis); //delay in milliseconds
        }
    }

    // function to hide the keyboard when a button is clicked
    private void hideKeyboard(View view) {
        InputMethodManager hideKeyboard = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        hideKeyboard.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
