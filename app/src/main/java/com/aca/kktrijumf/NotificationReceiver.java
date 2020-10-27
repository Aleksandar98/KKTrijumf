package com.aca.kktrijumf;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class NotificationReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        createNotificationChannel(context);
        Intent in = new Intent(context, SelectPlayerSmsActivity.class);
        PendingIntent pending = PendingIntent.getActivity(context, 0, in, 0);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "1234")
                .setSmallIcon(R.drawable.ic_book)
                .setContentTitle("Podsetnik za clanarine")
                .setContentText("Pregled svih igraca koji nisu platili")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pending);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);

        notificationManager.notify(1566, builder.build());

    }


    private void createNotificationChannel(Context context) {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Moj kanal";
            String description ="Desc";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("1234", name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}
