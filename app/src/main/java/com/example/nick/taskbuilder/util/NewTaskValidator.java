package com.example.nick.taskbuilder.util;

import com.example.nick.taskbuilder.entity.TaskDB;

import java.util.List;

public class NewTaskValidator {

    public TaskDB validateTime(TaskDB task, List<TaskDB> taskList){
        TaskDB failedTask = null;

        long newTaskS = task.getStartes();
        long newTaskE = task.getExpires();
        for (TaskDB t: taskList) {
//            Calendar newTaskTimeStart = Calendar.getInstance();
//            newTaskTimeStart.setTimeInMillis(task.getStartes());
//            Calendar newTaskTimeEnd = Calendar.getInstance();
//            newTaskTimeEnd.setTimeInMillis(task.getStartes());
            long dbTaskS = t.getStartes();
            long dbTaskE = t.getExpires();
            if ((newTaskS > dbTaskS && newTaskS < dbTaskE)
                    || (newTaskE > dbTaskS && newTaskE < dbTaskE)){
                failedTask = t;
                break;
            }
        }
        if (failedTask != null){
            return failedTask;
        }else {
            return null;
        }
    }

    public boolean validateInfo(TaskDB taskDB) {
        if (StringUtils.isNotBlank(taskDB.getName())){
                return taskDB.getName().length() > 4;
        }else {
            return false;
        }
    }
}
