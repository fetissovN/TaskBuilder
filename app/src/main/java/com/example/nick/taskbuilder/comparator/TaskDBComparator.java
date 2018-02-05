package com.example.nick.taskbuilder.comparator;

import com.example.nick.taskbuilder.entity.TaskDB;

import java.util.Comparator;


public class TaskDBComparator implements Comparator<TaskDB> {
    @Override
    public int compare(TaskDB o1, TaskDB o2) {
        if (o1.getStartes()>=o2.getStartes()){
            return 1;
        }else {
            return -1;
        }
    }
}
