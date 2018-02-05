package com.example.nick.taskbuilder.view.fragments;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;

import com.example.nick.taskbuilder.R;
import com.example.nick.taskbuilder.controller.CalendarController;
import com.example.nick.taskbuilder.controller.Controller;
import com.example.nick.taskbuilder.data.DB;
import com.example.nick.taskbuilder.view.CalendarActivity;
import com.example.nick.taskbuilder.view.TaskActivity;
import com.example.nick.taskbuilder.view.ViewCalendarInterface;
import com.roomorama.caldroid.CaldroidFragment;
import com.roomorama.caldroid.CaldroidListener;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;

public class CalendarFragment extends Fragment implements CalendarView.OnDateChangeListener, ViewCalendarInterface {

    CaldroidFragment mCaldroidFragment;
    CalendarController controller;

    public CalendarFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_calendar, container, false);
        mCaldroidFragment = new CaldroidFragment();
        final Bundle args = new Bundle();
        args.putInt( CaldroidFragment.START_DAY_OF_WEEK, CaldroidFragment.MONDAY );
        args.putInt( CaldroidFragment.THEME_RESOURCE, R.style.CaldroidDefaultDark);
        args.putBoolean( CaldroidFragment.ENABLE_SWIPE, true);

        mCaldroidFragment.setArguments( args );
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace( R.id.calendar_container , mCaldroidFragment ).commit();
        mCaldroidFragment.setCaldroidListener(new CaldroidListener() {
            @Override
            public void onSelectDate(Date date, View view) {
                Intent intent = new Intent(getActivity(), CalendarActivity.class);
                intent.putExtra("timeFrom",date.getTime());
                Calendar calendarTo = Calendar.getInstance();
                calendarTo.clear();
                calendarTo.setTimeInMillis(date.getTime());
                calendarTo.add(Calendar.DAY_OF_MONTH, 1);
                intent.putExtra("timeTo",calendarTo.getTimeInMillis());
                startActivity(intent);
            }
        });

        controller = new CalendarController(this,new DB(getContext()), getContext());

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
        Intent intent = new Intent(getContext(),TaskActivity.class);
        intent.putExtra("day",dayOfMonth);
        intent.putExtra("month",month);
        intent.putExtra("year",year);
        startActivity(intent);
    }

    @Override
    public void setupCalendar(Map<Date, Drawable> drawableMap) {
        mCaldroidFragment.setBackgroundDrawableForDates(drawableMap);
        mCaldroidFragment.refreshView();
    }
}
