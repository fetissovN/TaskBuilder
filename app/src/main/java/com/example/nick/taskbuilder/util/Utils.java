package com.example.nick.taskbuilder.util;

import com.example.nick.taskbuilder.entity.SheduledDayTask;
import com.example.nick.taskbuilder.entity.TaskDB;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class Utils {

    public static List<SheduledDayTask> getDayShedule(List<TaskDB> taskDBS){
        List<SheduledDayTask> list = new ArrayList<>();
        int latestTaskInMinutes = 0;
        int maxTimeInDayInMinutes = 1440;
        int count = 0;
        int amountOfTasks = taskDBS.size();
        for (TaskDB taskDB: taskDBS) {
            count++;

            long st = taskDB.getStartes();
            long en = taskDB.getExpires();

            Calendar calendarStart = Calendar.getInstance();
            calendarStart.setTimeInMillis(st);
            int hourS = calendarStart.get(Calendar.HOUR_OF_DAY);
            int minS = calendarStart.get(Calendar.MINUTE);
            int timeInMinutesStart = hourS*60+minS;

            Calendar calendarEnd = Calendar.getInstance();
            calendarEnd.setTimeInMillis(en);
            int hourE = calendarEnd.get(Calendar.HOUR_OF_DAY);
            int minE = calendarEnd.get(Calendar.MINUTE);
            int timeInMinutesEnd = hourE*60+minE;

            if (timeInMinutesStart == latestTaskInMinutes){
                SheduledDayTask sheduledDayTask = createNotEmpty(hourS,hourE,minS,minE
                        ,taskDB.getName(),taskDB.getId(),taskDB.getPriority());
                list.add(sheduledDayTask);
                latestTaskInMinutes = timeInMinutesEnd;
            }
            if (timeInMinutesStart > latestTaskInMinutes){
                int hourLatest = latestTaskInMinutes/60;
                int minuteLatest = latestTaskInMinutes%60;
                SheduledDayTask sheduledDayTaskEmpty = createEmpty(hourLatest,hourS,minuteLatest,minS);
                list.add(sheduledDayTaskEmpty);
                SheduledDayTask sheduledDayTask = createNotEmpty(hourS,hourE,minS,minE
                        ,taskDB.getName(),taskDB.getId(),taskDB.getPriority());
                list.add(sheduledDayTask);
                latestTaskInMinutes = timeInMinutesEnd;
            }
            if (count == amountOfTasks){
                int hourLatest = latestTaskInMinutes/60;
                int minuteLatest = latestTaskInMinutes%60;
                int hourEndDay = maxTimeInDayInMinutes/60;
                int minuteEndDay = maxTimeInDayInMinutes%60;
                SheduledDayTask sheduledDayTaskEmpty = createEmpty(hourLatest,hourEndDay,minuteLatest,minuteEndDay);
                list.add(sheduledDayTaskEmpty);
            }

        }
        return list;
    }

    private static SheduledDayTask createEmpty(int hourStart, int hourEnd, int minStart, int minEnd){
        SheduledDayTask sheduledDayTask = new SheduledDayTask();
        sheduledDayTask.setId(-1);
        sheduledDayTask.setHeight(120);
        sheduledDayTask.setName("none");
        sheduledDayTask.setHourFrom(hourStart);
        sheduledDayTask.setHourTo(hourEnd);
        sheduledDayTask.setMinuteFrom(minStart);
        sheduledDayTask.setMinuteTo(minEnd);
        return sheduledDayTask;
    }

    private static SheduledDayTask createNotEmpty(int hourFrom, int hourTo
            , int minuteFrom, int minuteTo
            , String name, long id, String priority){
        SheduledDayTask sheduledDayTask = new SheduledDayTask();
        sheduledDayTask.setHourFrom(hourFrom);
        sheduledDayTask.setHourTo(hourTo);
        sheduledDayTask.setMinuteFrom(minuteFrom);
        sheduledDayTask.setMinuteTo(minuteTo);
        sheduledDayTask.setId(id);
        int spred = (hourTo*60 + minuteTo) - (hourFrom*60 + minuteFrom);
        sheduledDayTask.setHeight(spred*4);
        sheduledDayTask.setPriority(priority);
        sheduledDayTask.setName(name);
        return sheduledDayTask;
    }

    public static long getEndOfDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        return calendar.getTimeInMillis();
    }

    public static long getStartOfDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return calendar.getTimeInMillis();
    }
}
