package com.example.nick.taskbuilder.view.dialog;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.format.DateFormat;
import android.widget.TimePicker;

import com.example.nick.taskbuilder.view.fragments.TaskFragment;

import java.util.Calendar;

public class TimePickerFragment extends DialogFragment implements android.app.TimePickerDialog.OnTimeSetListener {


    private int mId;
    private TimePickedListener mListener;
//    private TaskFragment fragmentCallback;

    @SuppressLint("ValidFragment")
    public TimePickerFragment(int id, TaskFragment fragment) {
//        this.fragmentCallback = fragment;
        this.mListener = fragment;
        this.mId = id;
    }

    public TimePickerFragment() {
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        // when the time is selected, send it to the activity via its callback
        // interface method
        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, hourOfDay);
        c.set(Calendar.MINUTE, minute);

        if(mListener != null)
            mListener.onTimePicked(c, mId);
    }

//    public interface TimePickerDialogListener {
//        void onTimeSet(int id, TimePicker view, int hourOfDay, int minute);
//    }

    public interface TimePickedListener {
        void onTimePicked(Calendar time, int id);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        //Use the current time as the default values for the time picker
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);
        //Create and return a new instance of TimePickerFragment
        return new TimePickerDialog(getActivity(),this, hour, minute,
                DateFormat.is24HourFormat(getActivity()));
    }

    //    public TimePickerFragment newInstance(int id, TaskFragment fragmentCallback) {
//        this.fragmentCallback = fragmentCallback;
//        Bundle args = new Bundle();
//        args.putInt("picker_id", id);
//        TimePickerFragment fragment = new TimePickerFragment();
//        fragment.setArguments(args);
//        return fragment;
//    }

}
