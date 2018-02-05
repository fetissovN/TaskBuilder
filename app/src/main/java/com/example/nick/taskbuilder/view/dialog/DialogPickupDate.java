package com.example.nick.taskbuilder.view.dialog;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.os.Bundle;

import com.example.nick.taskbuilder.view.fragments.TaskFragment;

import java.util.Calendar;

@SuppressLint("ValidFragment")
public class DialogPickupDate extends DialogFragment {

    private TaskFragment fragment;

    @SuppressLint("ValidFragment")
    public DialogPickupDate(TaskFragment callback) {
        this.fragment = callback;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        return new DatePickerDialog(getActivity(),
                (DatePickerDialog.OnDateSetListener) fragment, year, month, day);
    }
}
