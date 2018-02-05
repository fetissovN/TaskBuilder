package com.example.nick.taskbuilder.controller;


import android.content.Context;

import com.example.nick.taskbuilder.data.DataSourceInterface;
import com.example.nick.taskbuilder.entity.Task;
import com.example.nick.taskbuilder.entity.TaskDB;
import com.example.nick.taskbuilder.util.CalendarUtils;
import com.example.nick.taskbuilder.util.Utils;
import com.example.nick.taskbuilder.view.ViewCalendarInterface;

import java.util.List;

public class CalendarController {

    private ViewCalendarInterface view;

    private DataSourceInterface dataSource;


    public CalendarController(ViewCalendarInterface view, DataSourceInterface dataSource, Context context) {
        this.view = view;
        this.dataSource = dataSource;
        dataSource.open();
        setTasksToCalendar(context);
    }

    private void setTasksToCalendar(Context context) {
        List<TaskDB> dataSourceListOfTasks = dataSource.getListOfTasksDB();
        CalendarUtils calendarUtils = new CalendarUtils();
        view.setupCalendar(calendarUtils.convertTasksToDrawableMap(dataSourceListOfTasks, context));
    }


}
