package com.example.nick.taskbuilder.view;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.nick.taskbuilder.R;
import com.example.nick.taskbuilder.entity.Task;
import com.example.nick.taskbuilder.util.Strings;
import com.example.nick.taskbuilder.view.fragments.TaskFragment;

public class TaskActivity extends AppCompatActivity {

    TaskFragment taskFragment;
    FragmentTransaction fTrans;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);

        taskFragment = new TaskFragment();
        setContentView(R.layout.activity_task);
        Intent intent = getIntent();
        Task task = (Task) intent.getSerializableExtra("task");
        if (task==null){
//            Toast.makeText(this, Strings.TASK_ACTIVITY_FAIL, Toast.LENGTH_SHORT).show();
        }else {
            Bundle bundle = new Bundle();
            bundle.putSerializable("task", task);
            taskFragment.setArguments(bundle);
        }

        fTrans = getSupportFragmentManager().beginTransaction();
        fTrans.add(R.id.activity_task_container, taskFragment);
        fTrans.commit();
    }


//    private static final int START_TIME_PICKER_ID = 1;
//    private static final int END_TIME_PICKER_ID = 2;
//
//    private int year = -1;
//    private int months = -1;
//    private int day = -1;
//    private int hourStart = -1;
//    private int minuteStart = -1;
//    private int hourEnd = -1;
//    private int minuteEnd = -1;
//
//    private Calendar calendarStart = Calendar.getInstance();
//    private Calendar calendarEnd = Calendar.getInstance();
//
//    EditText tvName,tvDescription;
//    TextView tvStartTime, tvEndTime, id;
//    CircleImageView imageView;
//
//    TaskController controller;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_task);
//        Intent intent = getIntent();
//        Task task = (Task) intent.getSerializableExtra("task");
//        if (task==null){
//            Toast.makeText(this, Strings.TASK_ACTIVITY_FAIL, Toast.LENGTH_SHORT).show();
//        }else {
//            controller = new TaskController(this,new DB(this));
//            tvName = (EditText) findViewById(R.id.tv_item_name);
//            tvDescription = (EditText) findViewById(R.id.tv_item_description);
//            tvStartTime = (TextView) findViewById(R.id.tv_time_start);
//            tvStartTime.setOnClickListener(this);
//            tvEndTime = (TextView) findViewById(R.id.tv_time_expires);
//            tvEndTime.setOnClickListener(this);
//            imageView = (CircleImageView) findViewById(R.id.circleItem);
//            id = (TextView) findViewById(R.id.task_id);
//
//
//            tvName.setText(task.getName());
//            tvDescription.setText(task.getDescription());
//            tvStartTime.setText(task.getStartes());
//            tvEndTime.setText(task.getExpires());
//            id.setText(String.valueOf(task.getId()));
//
//            switch (task.getPriority()){
//                case "LOW":
//                    imageView.setImageResource(R.drawable.green_drawable);
//                    break;
//                case "MEDIUM":
//                    imageView.setImageResource(R.drawable.yellow_drawable);
//                    break;
//                case "HIGH":
//                    imageView.setImageResource(R.drawable.orange_drawable);
//                    break;
//                case "VERY_HIGH":
//                    imageView.setImageResource(R.drawable.red_drawable);
//                    break;
//            }
//        }
//    }
//
//    @Override
//    public void onClick(View v) {
//        switch (v.getId()){
//            case R.id.tv_time_start:
//                TimePickerFragment timePickerStart= TimePickerFragment.newInstance(START_TIME_PICKER_ID);
//                timePickerStart.show(getSupportFragmentManager(), "timePicker");
//                break;
//            case R.id.tv_time_expires:
//                TimePickerFragment timePickerEnd = TimePickerFragment.newInstance(END_TIME_PICKER_ID);
//                timePickerEnd.show(getSupportFragmentManager(), "timePicker");
//                break;
//        }
//    }
//
//    @Override
//    public void onTimePicked(Calendar time, int id) {
//        switch (id){
//            case 1:
//                hourStart = time.get(Calendar.HOUR_OF_DAY);
//                minuteStart = time.get(Calendar.MINUTE);
//                calendarStart.set(year,months,day,hourStart,minuteStart);
//                tvChosenTimeStart.setText("Hours: " + hourStart + " Minutes: " + minuteStart);
//                break;
//            case 2:
//                hourEnd = time.get(Calendar.HOUR_OF_DAY);
//                minuteEnd = time.get(Calendar.MINUTE);
//                calendarEnd.set(year,months,day,hourEnd,minuteEnd);
//                if(hourStart != -1){
//                    if (calendarStart.after(calendarEnd)){
//                        TimePickerFragment timePickerEnd = TimePickerFragment.newInstance(END_TIME_PICKER_ID);
//                        timePickerEnd.show(getSupportFragmentManager(), "timePicker");
//                        Toast.makeText(this, "Start date is after End date", Toast.LENGTH_SHORT).show();
//                    }
//                }
//                tvChosenTimeEnd.setText("Hours: " + hourEnd + " Minutes: " + minuteEnd);
//                break;
//        }
//    }
}
