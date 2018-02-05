package com.example.nick.taskbuilder.service;


import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.nick.taskbuilder.data.DB;
import com.example.nick.taskbuilder.util.Strings;

public class CleanDBService extends IntentService {


    public CleanDBService() {
        super("DeleteOldTasksService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        long timeTo = intent.getLongExtra("timeTo", 0);
        DB db = new DB(getApplicationContext());
        db.open();
        int numberOfRows = db.deletePrevious(timeTo);
        Log.d(Strings.LOG_TAG,numberOfRows + " rows was deleted");

    }
}
