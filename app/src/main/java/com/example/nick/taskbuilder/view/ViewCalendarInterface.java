package com.example.nick.taskbuilder.view;


import android.graphics.drawable.Drawable;

import java.util.Date;
import java.util.Map;

public interface ViewCalendarInterface {
    void setupCalendar(Map<Date, Drawable> drawableMap);
}
