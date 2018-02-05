package com.example.nick.taskbuilder.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.example.nick.taskbuilder.R;
import com.example.nick.taskbuilder.view.fragments.CalendarFragment;
import com.example.nick.taskbuilder.view.fragments.DayScheduleFragment;

public class CalendarActivity extends AppCompatActivity {
    DayScheduleFragment dayScheduleFragment;
    FragmentTransaction fTrans;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        dayScheduleFragment = new DayScheduleFragment();
        Intent intent = getIntent();
        long timeFrom = intent.getLongExtra("timeFrom",0);
        long timeTo = intent.getLongExtra("timeTo",0);

        if (timeFrom > 0 && timeTo > 0){
            Bundle bundle = new Bundle();
            bundle.putLong("timeFrom", timeFrom);
            bundle.putLong("timeTo", timeTo);
            dayScheduleFragment.setArguments(bundle);
        }else {
            Toast.makeText(this, "Failed to start day schedule", Toast.LENGTH_SHORT).show();
        }

        fTrans = getSupportFragmentManager().beginTransaction();
        fTrans.add(R.id.activity_calendar_container, dayScheduleFragment);
        fTrans.commit();
    }

}
