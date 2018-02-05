package com.example.nick.taskbuilder.util;


import com.example.nick.taskbuilder.entity.Task;
import com.example.nick.taskbuilder.entity.TaskDB;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class TaskDBToTaskConverter {

    public static List<Task> convertList(List<TaskDB> taskDB){
        List<Task> tasks = new ArrayList<>();
        SimpleDateFormat format = new SimpleDateFormat(Strings.DATE_TIME_FORMAT);
        for (TaskDB t:taskDB){
            Task task = convertTask(t);
            tasks.add(task);
        }
        return tasks;
    }

    public static Task convertTask(TaskDB taskDB){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Strings.DATE_TIME_FORMAT);
        Task task = new Task();
        task.setId(taskDB.getId());
        task.setName(taskDB.getName());
        task.setDescription(taskDB.getDescription());
        Calendar calendarStart = Calendar.getInstance();
        calendarStart.setTimeInMillis(taskDB.getStartes());
        Calendar calendarEnd = Calendar.getInstance();
        calendarEnd.setTimeInMillis(taskDB.getExpires());
        task.setStartes(simpleDateFormat.format(calendarStart.getTime()));
        task.setExpires(simpleDateFormat.format(calendarEnd.getTime()));
        task.setPriority(taskDB.getPriority());
        return task;
    }
}
