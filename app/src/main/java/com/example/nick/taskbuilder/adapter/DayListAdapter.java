package com.example.nick.taskbuilder.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.nick.taskbuilder.R;
import com.example.nick.taskbuilder.entity.SheduledDayTask;
import com.example.nick.taskbuilder.entity.TaskDB;

import java.util.List;

public class DayListAdapter extends ArrayAdapter<TaskDB>{

    private List<SheduledDayTask> listOfTasks;
    final float scale;

    public DayListAdapter(@NonNull Context context, int resource, List<SheduledDayTask> shList) {
        super(context, resource);
        this.listOfTasks = shList;
        scale = context.getResources().getDisplayMetrics().density;
    }

    @Override
    public int getCount() {
        return listOfTasks.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = convertView;

        if(null == view) {
            LayoutInflater layoutInflater = (LayoutInflater) getContext()
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.item_task_list_day, null);
        }

        if(listOfTasks != null) {
            SheduledDayTask sheduledDayTask = listOfTasks.get(position);
            int dps = sheduledDayTask.getHeight();
            int pixels = (int) (dps * scale + 0.5f);
            ImageView priority = (ImageView) view.findViewById(R.id.imageView_dayList);
            priority.getLayoutParams().height = pixels;

            TextView taskName= (TextView) view.findViewById(R.id.tv_taskName_dayList);
            TextView timeFrom = (TextView) view.findViewById(R.id.tv_item_dayList_timeFrom);
            TextView timeTo = (TextView) view.findViewById(R.id.tv_item_dayList_timeTo);

            int minFromInt = sheduledDayTask.getMinuteFrom();
            String minuteFrom = "";
            if (minFromInt < 10){
                minuteFrom = "0" + minFromInt;
                timeFrom.setText(sheduledDayTask.getHourFrom() + ":" + minuteFrom);
            }else {
                timeFrom.setText(sheduledDayTask.getHourFrom() + ":" + sheduledDayTask.getMinuteFrom() );
            }

            int minToInt = sheduledDayTask.getMinuteTo();
            String minuteTo= "";
            if (minToInt < 10){
                minuteTo = "0" + minToInt;
                timeTo.setText(sheduledDayTask.getHourTo() + ":" + minuteTo);
            }else {
                timeTo.setText(sheduledDayTask.getHourTo() + ":" + sheduledDayTask.getMinuteTo());
            }

            taskName.setText(sheduledDayTask.getName());
            if (sheduledDayTask.getPriority() != null){
                switch (sheduledDayTask.getPriority()){
                    case "LOW":
                        priority.setBackgroundColor(getContext().getResources().getColor(R.color.GREEN));
                        break;
                    case "MEDIUM":
                        priority.setBackgroundColor(getContext().getResources().getColor(R.color.YELLOW));
                        break;
                    case "HIGH":
                        priority.setBackgroundColor(getContext().getResources().getColor(R.color.ORANGE));
                        break;
                    case "VERY_HIGH":
                        priority.setBackgroundColor(getContext().getResources().getColor(R.color.RED));
                        break;
                }
            }else {
                priority.setBackgroundColor(getContext().getResources().getColor(R.color.colorGrey));
            }

        }
        return view;
    }

}
