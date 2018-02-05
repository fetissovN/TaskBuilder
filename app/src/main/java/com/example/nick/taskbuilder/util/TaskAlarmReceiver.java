package com.example.nick.taskbuilder.util;


import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

import com.example.nick.taskbuilder.R;
import com.example.nick.taskbuilder.entity.TaskDB;
import com.example.nick.taskbuilder.view.MainActivity;
import com.example.nick.taskbuilder.view.TaskActivity;

import static android.content.Context.NOTIFICATION_SERVICE;

public class TaskAlarmReceiver extends BroadcastReceiver{

    @Override
    public void onReceive(Context context, Intent intent) {
        //get task object
        Bundle args = intent.getBundleExtra("data");
        TaskDB taskDB = (TaskDB) args.getSerializable("task");

        if (taskDB == null){
            Toast.makeText(context, "dismissed", Toast.LENGTH_SHORT).show();
        }else {
            Vibrator v = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
            // Vibrate for 500 milliseconds
            v.vibrate(500);
            // Create PendingIntent
            Intent resultIntent = new Intent(context, TaskActivity.class);
//            Bundle bundle = new Bundle();
//            bundle.putSerializable("task", taskDB);
            resultIntent.putExtra("task", TaskDBToTaskConverter.convertTask(taskDB));
            PendingIntent resultPendingIntent = PendingIntent.getActivity(context, 0, resultIntent,
                    PendingIntent.FLAG_UPDATE_CURRENT);

            // Create Notification
            NotificationCompat.Builder builder =
                    new NotificationCompat.Builder(context)
                            .setSmallIcon(R.mipmap.ic_launcher)
                            .setContentTitle("Task Builder")
                            .setContentText("Task " + taskDB.getName() + " is started")
                            .setContentIntent(resultPendingIntent);

            Notification notification = builder.build();

            // Show Notification
            NotificationManager notificationManager =
                    (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
            notificationManager.notify(1, notification);
        }
    }
}
