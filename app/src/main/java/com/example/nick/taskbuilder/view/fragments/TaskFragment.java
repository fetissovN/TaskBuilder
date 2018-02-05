package com.example.nick.taskbuilder.view.fragments;


import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nick.taskbuilder.R;
import com.example.nick.taskbuilder.controller.TaskController;
import com.example.nick.taskbuilder.data.DB;
import com.example.nick.taskbuilder.entity.Priority;
import com.example.nick.taskbuilder.entity.Task;
import com.example.nick.taskbuilder.entity.TaskDB;
import com.example.nick.taskbuilder.util.NewTaskValidator;
import com.example.nick.taskbuilder.util.Strings;
import com.example.nick.taskbuilder.util.TaskAlarmReceiver;
import com.example.nick.taskbuilder.util.TaskToTaskDBConverter;
import com.example.nick.taskbuilder.view.MainActivity;
import com.example.nick.taskbuilder.view.ViewTaskInterface;
import com.example.nick.taskbuilder.view.dialog.DialogPickupDate;
import com.example.nick.taskbuilder.view.dialog.TimePickerFragment;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

public class TaskFragment extends Fragment implements RadioGroup.OnCheckedChangeListener
        , ViewTaskInterface
        , View.OnClickListener
        , DatePickerDialog.OnDateSetListener
        , TimePickerFragment.TimePickedListener{


    private static final int START_TIME_PICKER_ID = 1;
    private static final int END_TIME_PICKER_ID = 2;
    private static final int OPERATION_ADD = 1;
    private static final int OPERATION_UPDATE = 2;
    private int year = -1;
    private int months = -1;
    private int day = -1;
    private int hourStart = -1;
    private int minuteStart = -1;
    private int hourEnd = -1;
    private int minuteEnd = -1;

    private long timePassedFromCalendar;

    private Calendar calendarStart = Calendar.getInstance();
    private Calendar calendarEnd = Calendar.getInstance();

    AlarmManager alarmManager;

    TextView tvChosenTimeStart, tvChosenTimeEnd, tvChosenDate;
    EditText name, description;
    Button setStartTime, setEndTime, setDate;
    Button save, update, delete;

    RadioGroup radioGroup;
    ProgressBar progressBar;

    Task taskToUpdate;
    TaskDB newTask;
    TaskController controller;


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @SuppressLint("SetTextI18n")
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_add_task,null);

        controller = new TaskController(this, new DB(getActivity()));
        newTask = new TaskDB();

        radioGroup = (RadioGroup) v.findViewById(R.id.radioGroup);
        radioGroup.setOnCheckedChangeListener(this);

        progressBar = (ProgressBar) v.findViewById(R.id.progressBarAdd);
        name = (EditText) v.findViewById(R.id.etName);
        description = (EditText) v.findViewById(R.id.etDescription);
        setStartTime = (Button) v.findViewById(R.id.btnSetStartTimeDate);
        setEndTime = (Button) v.findViewById(R.id.btnSetSEndTimeDate);
        setDate = (Button) v.findViewById(R.id.btnDate);

        save = (Button) v.findViewById(R.id.btn_save_new_task);
        update = (Button) v.findViewById(R.id.btnUpdateTask);
        delete = (Button) v.findViewById(R.id.btnDeleteTask);

        tvChosenDate = (TextView) v.findViewById(R.id.tvDate);
        tvChosenTimeStart = (TextView) v.findViewById(R.id.tv_chosen_start_time);
        tvChosenTimeEnd= (TextView) v.findViewById(R.id.tv_chosen_end_time);
        setDate.setOnClickListener(this);
        setStartTime.setOnClickListener(this);
        setEndTime.setOnClickListener(this);
        save.setOnClickListener(this);
        update.setOnClickListener(this);
        delete.setOnClickListener(this);

        //Check weather it is task in bundle was set by activity
        Bundle b = getArguments();
        if (b != null){
            taskToUpdate = (Task) b.getSerializable("task");
            timePassedFromCalendar = b.getLong("day", 0);
            if (timePassedFromCalendar>0){
                Calendar calendar = Calendar.getInstance();
                calendar.clear();
                calendar.setTimeInMillis(timePassedFromCalendar);
                onDateSet(new DatePicker(getActivity())
                        ,calendar.get(Calendar.YEAR)
                        ,calendar.get(Calendar.MONTH)
                        ,calendar.get(Calendar.DAY_OF_MONTH));
            }
        }
        if (taskToUpdate == null){
//            tvChosenDate.setText("Today");
            update.setVisibility(View.INVISIBLE);
            delete.setVisibility(View.INVISIBLE);
        }else {
            SimpleDateFormat format = new SimpleDateFormat(Strings.DATE_TIME_FORMAT);
            newTask = TaskToTaskDBConverter.convertTask(taskToUpdate);
            TextView textView = (TextView) v.findViewById(R.id.tv_add_new_task);
            textView.setText("Modify task");
            try {
                save.setVisibility(View.INVISIBLE);
                name.setText(taskToUpdate.getName());
                description.setText(taskToUpdate.getDescription());

                RadioButton radioButton;
                switch (taskToUpdate.getPriority()){
                    case "LOW":
                        radioButton = (RadioButton) v.findViewById(R.id.radio_low);
                        radioButton.setChecked(true);
                        break;
                    case "MEDIUM":
                        radioButton = (RadioButton) v.findViewById(R.id.radio_medium);
                        radioButton.setChecked(true);
                        break;
                    case "HIGH":
                        radioButton = (RadioButton) v.findViewById(R.id.radio_high);
                        radioButton.setChecked(true);
                        break;
                    case "VERY_HIGH":
                        radioButton = (RadioButton) v.findViewById(R.id.radio_very_high);
                        radioButton.setChecked(true);
                        break;
                }

                Date dateStart = format.parse(taskToUpdate.getStartes());
                Date dateEnd = format.parse(taskToUpdate.getExpires());
                calendarStart.setTimeInMillis(dateStart.getTime());
                calendarEnd.setTimeInMillis(dateEnd.getTime());

                DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.MEDIUM);
                tvChosenDate.setText(dateFormat.format(calendarStart.getTime()));

                hourStart = calendarStart.get(Calendar.HOUR_OF_DAY);
                minuteStart = calendarStart.get(Calendar.MINUTE);
                tvChosenTimeStart.setText("Hours: " + hourStart + " Minutes: " + minuteStart);

                hourEnd = calendarEnd.get(Calendar.HOUR_OF_DAY);
                minuteEnd = calendarEnd.get(Calendar.MINUTE);
                tvChosenTimeEnd.setText("Hours: " + hourEnd + " Minutes: " + minuteEnd);
            } catch (ParseException e) {
                Toast.makeText(getActivity(), "Parse exception", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        }
        if (timePassedFromCalendar == 0){
            year = calendarStart.get(Calendar.YEAR);
            months = calendarStart.get(Calendar.MONTH);
            day = calendarStart.get(Calendar.DAY_OF_MONTH);
        }
        return v;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId){
            case R.id.radio_very_high:
                newTask.setPriority(Priority.VERY_HIGH.toString());
                Toast.makeText(getActivity(), Priority.VERY_HIGH.toString(), Toast.LENGTH_SHORT).show();
                break;
            case R.id.radio_high:
                newTask.setPriority(Priority.HIGH.toString());
                Toast.makeText(getActivity(), "High", Toast.LENGTH_SHORT).show();
                break;
            case R.id.radio_medium:
                newTask.setPriority(Priority.MEDIUM.toString());
                Toast.makeText(getActivity(), "Medium", Toast.LENGTH_SHORT).show();
                break;
            case R.id.radio_low:
                newTask.setPriority(Priority.LOW.toString());
                Toast.makeText(getActivity(), "Low", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnSetStartTimeDate:
                TimePickerFragment timePickerStart= new TimePickerFragment(START_TIME_PICKER_ID, this);
                timePickerStart.show(getActivity().getSupportFragmentManager(), "timePicker");
                break;
            case R.id.btnSetSEndTimeDate:
                TimePickerFragment timePickerEnd = new TimePickerFragment(END_TIME_PICKER_ID,this);
                timePickerEnd.show(getActivity().getSupportFragmentManager(), "timePicker");
                break;
            case R.id.btnDate:
                DialogPickupDate dialogPickupDate = new DialogPickupDate(this);
                dialogPickupDate.show(getActivity().getFragmentManager(),"date");
                break;
            case R.id.btn_save_new_task:
                collectDataAndValidate(OPERATION_ADD);
                break;
            case R.id.btnUpdateTask:
                collectDataAndValidate(OPERATION_UPDATE);
                break;
            case R.id.btnDeleteTask:
                controller.deleteTask(newTask);
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
                break;
        }
    }

    private void collectDataAndValidate(Integer operation){
        newTask.setName(name.getText().toString());
        newTask.setDescription(description.getText().toString());
        newTask.setStartes(calendarStart.getTimeInMillis());
        newTask.setExpires(calendarEnd.getTimeInMillis());
        Map<TaskDB,Integer> integerMap = new HashMap<>();
        integerMap.put(newTask,operation);
        TaskFragment.ValidateNewTaskAcync taskAcync = new ValidateNewTaskAcync();
        taskAcync.execute(integerMap);
    }

    private void makeToast(String s) {
        Toast.makeText(getActivity(), s, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar cal = new GregorianCalendar(year, month, dayOfMonth);
        DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.MEDIUM);
        this.year = year;
        this.months = month;
        this.day = dayOfMonth;
        tvChosenDate.setText(dateFormat.format(cal.getTime()));
    }

    @Override
    public void onTimePicked(Calendar time, int id) {
        switch (id){
            case 1:
                hourStart = time.get(Calendar.HOUR_OF_DAY);
                minuteStart = time.get(Calendar.MINUTE);
                calendarStart.set(year,months,day,hourStart,minuteStart);
                tvChosenTimeStart.setText("Hours: " + hourStart + " Minutes: " + minuteStart);
                break;
            case 2:
                hourEnd = time.get(Calendar.HOUR_OF_DAY);
                minuteEnd = time.get(Calendar.MINUTE);
                calendarEnd.set(year,months,day,hourEnd,minuteEnd);
                if(hourStart != -1){
                    if (calendarStart.after(calendarEnd)){
                        TimePickerFragment timePickerEnd = new TimePickerFragment(END_TIME_PICKER_ID, this);
                        timePickerEnd.show(getActivity().getSupportFragmentManager(), "timePicker");
                        Toast.makeText(getActivity(), "Start date is after End date", Toast.LENGTH_SHORT).show();
                    }
                }
                tvChosenTimeEnd.setText("Hours: " + hourEnd + " Minutes: " + minuteEnd);
                break;
        }
    }

    @Override
    public TaskDB addNewTask() {
        return newTask;
    }

    @Override
    public TaskDB updateTask() {
        return newTask;
    }

    @Override
    public TaskDB deleteTask() {
        return newTask;
    }

    class ValidateNewTaskAcync extends AsyncTask<Map<TaskDB, Integer>,Void,Map<Boolean,String>> {

        boolean saved;
        boolean infoValid;
        boolean timeValid;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected Map<Boolean, String> doInBackground(Map<TaskDB, Integer>... taskDBS) {
            Map<TaskDB, Integer> taskDBMap = taskDBS[0];
            TaskDB taskDB = (TaskDB) taskDBMap.keySet().toArray()[0];
            Integer operation = (Integer) taskDBMap.values().toArray()[0];

            NewTaskValidator newTaskValidator = new NewTaskValidator();
            if (operation == OPERATION_UPDATE){
                controller.deleteTask(taskDB);
            }
            //todo emergency quit check deleted task
            TaskDB taskFailed = newTaskValidator.validateTime(taskDB,controller.getListAllDB());
            boolean infoValid = newTaskValidator.validateInfo(taskDB);
            if (!infoValid){
                this.infoValid = false;
                saved = false;
            }else {
                if (taskFailed == null){
                    if (operation == OPERATION_ADD){
                        controller.addNewTask();
                        saved = true;
                        setAlarm(newTask);
                    }
                    if (operation == OPERATION_UPDATE){
                        controller.addNewTask();
                        saved = true;
                        dismissAlarm(newTask);
                        setAlarm(newTask);
                    }
                    Intent intent = new Intent(getActivity(), MainActivity.class);
                    startActivity(intent);
                }
                else {
                    timeValid = false;
                    saved = false;
                }
            }
            return null;

        }

        @Override
        protected void onPostExecute(Map<Boolean,String> stringMap) {
            super.onPostExecute(stringMap);
            progressBar.setVisibility(View.INVISIBLE);
            if (saved){
                makeToast(Strings.TASK_SAVED);
            }else {
                makeToast(Strings.TASK_NOT_SAVED);
                if (!infoValid){
                    makeToast(Strings.TASK_INFO_NOT_VALID);
                }
                if (!timeValid){
                    makeToast(Strings.TASK_TIME_ERROR);
                }
            }


        }
    }

    public void setAlarm(TaskDB taskDB){
        alarmManager = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(getActivity(), TaskAlarmReceiver.class);
        Bundle args = new Bundle();
        args.putSerializable("task",(Serializable) taskDB);
        intent.putExtra("data", args);
        PendingIntent alarmPendingIntent = PendingIntent.getBroadcast(getActivity(), (int) taskDB.getId(), intent, Intent.FILL_IN_DATA);
        alarmManager.set(AlarmManager.RTC_WAKEUP,taskDB.getStartes(), alarmPendingIntent);
    }

    public void dismissAlarm(TaskDB taskDB){
        alarmManager = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(getActivity(), TaskAlarmReceiver.class);
        intent.putExtra("task",taskDB);
        PendingIntent alarmPendingIntent = PendingIntent.getBroadcast(getActivity(), (int) taskDB.getId(), intent, 0);
        alarmManager.cancel(alarmPendingIntent);
    }

}