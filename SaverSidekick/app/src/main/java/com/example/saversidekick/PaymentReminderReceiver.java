package com.example.saversidekick;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class PaymentReminderReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        // Handle the payment reminder here
        // This method will be called when the broadcast is received
        // You can perform any necessary actions, such as displaying a notification or triggering an event
        // For example:
        Toast.makeText(context, "Payment reminder received!", Toast.LENGTH_SHORT).show();
    }
}
