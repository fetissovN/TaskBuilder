package com.example.nick.taskbuilder.view;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.nick.taskbuilder.R;
import com.example.nick.taskbuilder.view.fragments.TaskFragment;

public class AddTaskActivity extends AppCompatActivity{

    TaskFragment taskFragment;
    FragmentTransaction fTrans;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);
        taskFragment = new TaskFragment();
        long day = getIntent().getLongExtra("day",0);
        if (day > 0){
            Bundle bundle = new Bundle();
            bundle.putLong("day", day);
            taskFragment.setArguments(bundle);
        }

        fTrans = getSupportFragmentManager().beginTransaction();
        fTrans.add(R.id.activity_add_container, taskFragment);
        fTrans.commit();
    }


//    private static final int START_TIME_PICKER_ID = 1;
//    private static final int END_TIME_PICKER_ID = 2;
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
//    TextView tvChosenTimeStart, tvChosenTimeEnd, tvChosenDate;
//    EditText name, description;
//    Button setStartTime, setEndTime, setDate;
//    Button save;
//    RadioGroup radioGroup;
//    ProgressBar progressBar;
//
//    TaskDB newTask;
//    Controller controller;
//
//
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_add_task);
//        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
//        radioGroup.setOnCheckedChangeListener(this);
//
//        year = calendarStart.get(Calendar.YEAR);
//        months = calendarStart.get(Calendar.MONTH);
//        day = calendarStart.get(Calendar.DAY_OF_MONTH);
//
//        controller = new Controller(this, new DB(this));
//        newTask = new TaskDB();
//
//        progressBar = (ProgressBar) findViewById(R.id.progressBarAdd);
//        name = (EditText) findViewById(R.id.etName);
//        description = (EditText) findViewById(R.id.etDescription);
//        setStartTime = (Button) findViewById(R.id.btnSetStartTimeDate);
//        setEndTime = (Button) findViewById(R.id.btnSetSEndTimeDate);
//        setDate = (Button) findViewById(R.id.btnDate);
//        save = (Button) findViewById(R.id.btn_save_new_task);
//
//        tvChosenDate = (TextView) findViewById(R.id.tvDate);
//        tvChosenDate.setText("Today");
//        tvChosenTimeStart = (TextView) findViewById(R.id.tv_chosen_start_time);
//        tvChosenTimeEnd= (TextView) findViewById(R.id.tv_chosen_end_time);
//        setDate.setOnClickListener(this);
//        setStartTime.setOnClickListener(this);
//        setEndTime.setOnClickListener(this);
//        save.setOnClickListener(this);
//    }
//
//    @Override
//    public void onCheckedChanged(RadioGroup group, int checkedId) {
//        switch (checkedId){
//            case R.id.radio_very_high:
//                newTask.setPriority(Priority.VERY_HIGH.toString());
//                Toast.makeText(this, Priority.VERY_HIGH.toString(), Toast.LENGTH_SHORT).show();
//                break;
//            case R.id.radio_high:
//                newTask.setPriority(Priority.HIGH.toString());
//                Toast.makeText(this, "High", Toast.LENGTH_SHORT).show();
//                break;
//            case R.id.radio_medium:
//                newTask.setPriority(Priority.MEDIUM.toString());
//                Toast.makeText(this, "Medium", Toast.LENGTH_SHORT).show();
//                break;
//            case R.id.radio_low:
//                newTask.setPriority(Priority.LOW.toString());
//                Toast.makeText(this, "Low", Toast.LENGTH_SHORT).show();
//                break;
//        }
//    }
//
//    @Override
//    public void onClick(View v) {
//        switch (v.getId()){
//            case R.id.btnSetStartTimeDate:
//                TimePickerFragment timePickerStart= TimePickerFragment.newInstance(START_TIME_PICKER_ID);
//                timePickerStart.show(getSupportFragmentManager(), "timePicker");
//                break;
//            case R.id.btnSetSEndTimeDate:
//                TimePickerFragment timePickerEnd = TimePickerFragment.newInstance(END_TIME_PICKER_ID);
//                timePickerEnd.show(getSupportFragmentManager(), "timePicker");
//                break;
//            case R.id.btnDate:
//                DialogPickupDate dialogPickupDate = new DialogPickupDate();
//                dialogPickupDate.show(getFragmentManager(),"date");
//                break;
//            case R.id.btn_save_new_task:
//                //todo validate task
//                newTask.setName(name.getText().toString());
//                newTask.setDescription(description.getText().toString());
//                newTask.setStartes(calendarStart.getTimeInMillis());
//                newTask.setExpires(calendarEnd.getTimeInMillis());
//                ValidateNewTaskAcync taskAcync = new ValidateNewTaskAcync();
//                taskAcync.execute(newTask);
//                break;
//        }
//    }
//
//    class ValidateNewTaskAcync extends AsyncTask<TaskDB,Void,Void>{
//
//        boolean saved;
//        boolean infoValid;
//        boolean timeValid;
//
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//            progressBar.setVisibility(View.VISIBLE);
//        }
//
//        @Override
//        protected Void doInBackground(TaskDB... taskDBS) {
//            NewTaskValidator newTaskValidator = new NewTaskValidator();
//            TaskDB taskFailed = newTaskValidator.validateTime(taskDBS[0],controller.getListAllDB());
//            boolean infoValid = newTaskValidator.validateInfo(taskDBS[0]);
//            if (!infoValid){
//                this.infoValid = false;
//                saved = false;
//            }else {
//                if (taskFailed == null){
//                    controller.addNewTask();
//                    saved = true;
//                    Intent intent = new Intent(AddTaskActivity.this, MainActivity.class);
//                    startActivity(intent);
//                }
//                else {
//                    timeValid = false;
//                    saved = false;
//                }
//            }
//
//            return null;
//
//        }
//
//        @Override
//        protected void onPostExecute(Void aVoid) {
//            super.onPostExecute(aVoid);
//            progressBar.setVisibility(View.INVISIBLE);
//            if (saved){
//                makeToast(Strings.TASK_SAVED);
//            }else {
//                makeToast(Strings.TASK_NOT_SAVED);
//                if (!infoValid){
//                    makeToast(Strings.TASK_INFO_NOT_VALID);
//                }
//                if (!timeValid){
//                    makeToast(Strings.TASK_TIME_ERROR);
//                }
//            }
//
//
//        }
//    }
//
//    private void makeToast(String s) {
//        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
//    }
//
//    @Override
//    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
//        Calendar cal = new GregorianCalendar(year, month, dayOfMonth);
//        DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.MEDIUM);
//        this.year = year;
//        this.months = month;
//        this.day = dayOfMonth;
//        tvChosenDate.setText(dateFormat.format(cal.getTime()));
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
//
//
//    @Override
//    public TaskDB addNewTask() {
//        return newTask;
//    }
}
