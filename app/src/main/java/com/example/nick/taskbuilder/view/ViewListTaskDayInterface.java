package com.example.nick.taskbuilder.view;


import com.example.nick.taskbuilder.entity.Task;
import com.example.nick.taskbuilder.entity.TaskDB;

import java.util.List;

public interface ViewListTaskDayInterface {
//    void setListOfTasksFormDB(List<Task> listOfTasksFormDB);

    void setListOfTasksForDay(List<TaskDB> dayTasksSheduled);
}
