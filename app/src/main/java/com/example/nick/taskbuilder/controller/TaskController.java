package com.example.nick.taskbuilder.controller;


import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.example.nick.taskbuilder.data.DataSourceInterface;
import com.example.nick.taskbuilder.entity.Task;
import com.example.nick.taskbuilder.entity.TaskDB;
import com.example.nick.taskbuilder.util.TaskDBToTaskConverter;
import com.example.nick.taskbuilder.view.ViewTaskInterface;

import java.util.List;

public class TaskController {
    private ViewTaskInterface view;

    private DataSourceInterface dataSource;

    public TaskController(ViewTaskInterface view, DataSourceInterface dataSource) {
        this.view = view;
        this.dataSource = dataSource;
        dataSource.open();
    }

    public List<TaskDB> getListAllDB(){
        return dataSource.getListOfTasksDB();
    }

    public void addNewTask() {
        TaskDB task = view.addNewTask();
        dataSource.addRec(task);

    }

    public void deleteTask(TaskDB taskDB) {
        dataSource.deleteTask(TaskDBToTaskConverter.convertTask(taskDB));
    }

    public void updateTask(TaskDB taskDB){
        dataSource.updateTask(taskDB);
    }


}
