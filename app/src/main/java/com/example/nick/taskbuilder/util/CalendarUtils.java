package com.example.nick.taskbuilder.util;


import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;

import com.example.nick.taskbuilder.R;
import com.example.nick.taskbuilder.entity.Task;
import com.example.nick.taskbuilder.entity.TaskDB;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class CalendarUtils {

    public Map<Date, Drawable> convertTasksToDrawableMap(List<TaskDB> taskList, Context context) {
        Map<Date, Drawable> dateDrawableMap = new HashMap<>();
        Set<Calendar> dates = new HashSet<>();
        for (TaskDB t : taskList) {
            Calendar taskTimeStart = Calendar.getInstance();
            taskTimeStart.setTimeInMillis(t.getStartes());

            Calendar taskDay = Calendar.getInstance();
            taskDay.clear();
            taskDay.set(taskTimeStart.get(Calendar.YEAR)
                    , taskTimeStart.get(Calendar.MONTH)
                    , taskTimeStart.get(Calendar.DAY_OF_MONTH));
            dates.add(taskDay);
        }
        for (Calendar d : dates) {
            int priority = 0;
            Calendar calendarEndOfDay = Calendar.getInstance();
            calendarEndOfDay.setTimeInMillis(d.getTimeInMillis());
            calendarEndOfDay.add(Calendar.DAY_OF_MONTH, 1);
            long timeEndDay = calendarEndOfDay.getTimeInMillis();
            long timeStartDay = d.getTimeInMillis();
            for (TaskDB t : taskList) {
                if (t.getStartes() > timeStartDay && t.getStartes() < timeEndDay) {
                    switch (t.getPriority()) {
                        case "LOW":
                            if (1 > priority) {
                                priority = 1;
                            }
                            break;
                        case "MEDIUM":
                            if (2 > priority) {
                                priority = 2;
                            }
                            break;
                        case "HIGH":
                            if (3 > priority) {
                                priority = 3;
                            }
                            break;
                        case "VERY_HIGH":
                            if (4 > priority) {
                                priority = 4;
                            }
                            break;
                    }
                }
            }
            switch (priority) {
                case 1:
                    dateDrawableMap.put(d.getTime(), context.getDrawable(R.drawable.grey_drawable));
                    break;
                case 2:
                    dateDrawableMap.put(d.getTime(), context.getDrawable(R.drawable.yellow_drawable));
                    break;
                case 3:
                    dateDrawableMap.put(d.getTime(), context.getDrawable(R.drawable.orange_drawable));
                    break;
                case 4:
                    dateDrawableMap.put(d.getTime(), context.getDrawable(R.drawable.red_drawable));
                    break;
            }

        }
        return dateDrawableMap;
    }
}