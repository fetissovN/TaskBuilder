package com.example.nick.taskbuilder.controller;

import android.view.View;

import com.example.nick.taskbuilder.data.DataSourceInterface;
import com.example.nick.taskbuilder.entity.Task;
import com.example.nick.taskbuilder.entity.TaskDB;
import com.example.nick.taskbuilder.view.ViewAddInterface;
import com.example.nick.taskbuilder.view.ViewListTaskDayInterface;
import com.example.nick.taskbuilder.view.ViewListTaskInterface;

import java.util.Calendar;
import java.util.List;

public class Controller {

    private ViewListTaskInterface view;

    private ViewListTaskDayInterface dayView;

    private ViewAddInterface addView;

    private DataSourceInterface dataSource;

    public Controller(ViewListTaskInterface view, DataSourceInterface dataSource) {
        this.view = view;
        this.dataSource = dataSource;
        dataSource.open();
        getListActualFromDataSource();
    }

    public Controller(ViewListTaskDayInterface view, DataSourceInterface dataSource) {
        this.dayView = view;
        this.dataSource = dataSource;
        dataSource.open();
//        setUpListForDayView(view);
    }

    public Controller(ViewAddInterface view, DataSourceInterface dataSource) {
        this.addView = view;
        this.dataSource = dataSource;
        dataSource.open();
    }

    public Controller() {
    }

    public void getDayTasksSheduled(long timeFrom, long timeTo){
        List<TaskDB> taskDBList = dataSource.getDayTasksSheduled(timeFrom, timeTo);
        dayView.setListOfTasksForDay(taskDBList);
    }

    public void getListAllFromDataSource(){
        view.setUpAdapterAndView(
                dataSource.getListOfTasks()
        );
    }

    public void getListActualFromDataSource(){
        Calendar calendar = Calendar.getInstance();
        long time = calendar.getTimeInMillis();

        view.setUpAdapterAndView(
                dataSource.getListOfTasksActual(time)
        );
    }

    public List<TaskDB> getListAllDB(){
        return dataSource.getListOfTasksDB();
    }

    public void onListItemSwiped(int position, Task task) {
        dataSource.deleteTask(task);
        view.deleteListItemAtPosition(position);
    }

    public void onListItemClick(Task task) {
        view.setUpAndOpenTaskActivity(task);
    }

    public void addNewTask() {
        TaskDB task = addView.addNewTask();
        dataSource.addRec(task);
    }
}
