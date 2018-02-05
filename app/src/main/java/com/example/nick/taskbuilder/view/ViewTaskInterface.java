package com.example.nick.taskbuilder.view;


import com.example.nick.taskbuilder.entity.TaskDB;

public interface ViewTaskInterface {

    TaskDB addNewTask();

    TaskDB updateTask();

    TaskDB deleteTask();
}
