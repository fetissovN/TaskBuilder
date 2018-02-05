package com.example.nick.taskbuilder.view.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.nick.taskbuilder.R;
import com.example.nick.taskbuilder.adapter.DayListAdapter;
import com.example.nick.taskbuilder.controller.Controller;
import com.example.nick.taskbuilder.data.DB;
import com.example.nick.taskbuilder.entity.SheduledDayTask;
import com.example.nick.taskbuilder.entity.Task;
import com.example.nick.taskbuilder.entity.TaskDB;
import com.example.nick.taskbuilder.util.TaskDBToTaskConverter;
import com.example.nick.taskbuilder.util.Utils;
import com.example.nick.taskbuilder.view.AddTaskActivity;
import com.example.nick.taskbuilder.view.TaskActivity;
import com.example.nick.taskbuilder.view.ViewListTaskDayInterface;

import java.util.Calendar;
import java.util.List;

public class DayScheduleFragment extends ListFragment implements ViewListTaskDayInterface {

    private DayListAdapter dayListAdapter;
    private Controller controller;
    private List<TaskDB> listOfTasks;
    long timeFrom;
    long timeTo;
    List<SheduledDayTask> shList;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        controller = new Controller(this, new DB(getContext()));
        Bundle bundle = getArguments();
        if (bundle != null){
            timeFrom = bundle.getLong("timeFrom");
            timeTo = bundle.getLong("timeTo");
        }else {
            Calendar calendar = Calendar.getInstance();
            timeFrom = Utils.getStartOfDay(calendar.getTime());
            timeTo = Utils.getEndOfDay(calendar.getTime());
        }
        controller.getDayTasksSheduled(timeFrom,timeTo);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        shList = Utils.getDayShedule(listOfTasks);
        dayListAdapter = new DayListAdapter(getContext(),R.layout.item_task_list_day, shList);
        setListAdapter(dayListAdapter);
        return inflater.inflate(R.layout.fragment_day_schedule,container,false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        FloatingActionButton fab = (FloatingActionButton) getActivity().findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), AddTaskActivity.class);
                if (timeFrom > 0 && timeTo > 0){
                    intent.putExtra("day", timeFrom);
                }
                startActivity(intent);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onListItemClick(ListView l, final View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        SheduledDayTask sheduledDayTask = shList.get(position);
        Task clickedTask = null;
        for (TaskDB t: listOfTasks) {
            if (t.getId() == sheduledDayTask.getId()){
                clickedTask = TaskDBToTaskConverter.convertTask(t);
            }
        }
        Intent intent = new Intent(getActivity(),TaskActivity.class);
        intent.putExtra("task", clickedTask);
        startActivity(intent);

    }

//    @Override
//    public void setListOfTasksFormDB(List<Task> listOfTasksFormDB) {
//        this.listOfTasks = listOfTasksFormDB;
//    }

    @Override
    public void setListOfTasksForDay(List<TaskDB> dayTasksSheduled) {
        this.listOfTasks = dayTasksSheduled;
    }
}
