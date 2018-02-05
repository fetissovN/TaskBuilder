package com.example.nick.taskbuilder.util;


import com.example.nick.taskbuilder.entity.Task;
import com.example.nick.taskbuilder.entity.TaskDB;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class TaskToTaskDBConverter {

    public static TaskDB convertTask(Task task){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Strings.DATE_TIME_FORMAT);
        TaskDB taskDB = new TaskDB();
        try {
            Date dateStart = simpleDateFormat.parse(task.getStartes());
            Date dateEnd = simpleDateFormat.parse(task.getExpires());
            taskDB.setId(task.getId());
            taskDB.setName(task.getName());
            taskDB.setDescription(task.getDescription());
            taskDB.setStartes(dateStart.getTime());
            taskDB.setExpires(dateEnd.getTime());
            taskDB.setPriority(task.getPriority());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return taskDB;
    }
}
