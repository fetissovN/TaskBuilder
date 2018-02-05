package com.example.nick.taskbuilder.view;

import com.example.nick.taskbuilder.entity.Task;
import com.example.nick.taskbuilder.entity.TaskDB;

import java.util.List;

public interface ViewListTaskInterface {

    void setUpAdapterAndView(List<Task> listOfTasks);

    void deleteListItemAtPosition(int position);

    void setUpAndOpenTaskActivity(Task task);
}
