package com.example.nick.taskbuilder.data;


import com.example.nick.taskbuilder.entity.Task;
import com.example.nick.taskbuilder.entity.TaskDB;

import java.util.List;


public interface DataSourceInterface {

    List<Task> getListOfTasks();

    List<TaskDB> getListOfTasksDB();

    List<Task> getWeekListOfTasks();

    List<TaskDB> getDayTasksSheduled(long startDay, long endDay);

    void deleteTask(Task listItem);

    int deletePrevious(long timeTo);

    void addRec(TaskDB listItem);

    void open();

    List<Task> getListOfTasksActual(long timeFrom);

    void updateTask(TaskDB taskDB);
}
