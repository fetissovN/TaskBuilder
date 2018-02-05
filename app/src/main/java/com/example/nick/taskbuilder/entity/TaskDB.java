package com.example.nick.taskbuilder.entity;


import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.Date;

public class TaskDB implements Serializable{

    private long id;

    private String name;

    private String description;

    private long startes;

    private long expires;

    private String priority;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getExpires() {
        return expires;
    }

    public void setExpires(long expires) {
        this.expires = expires;
    }

    public long getStartes() {
        return startes;
    }

    public void setStartes(long startes) {
        this.startes = startes;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

//    public TaskDB (Parcel in){
//        String[] data = new String[6];
//
//        in.readStringArray(data);
//        // the order needs to be the same as in writeToParcel() method
//        this.id = Long.parseLong(data[0]);
//        this.name = data[1];
//        this.grade = data[2];
//    }
//
//    @Override
//    public int describeContents() {
//        return 0;
//    }
//
//    @Override
//    public void writeToParcel(Parcel dest, int flags) {
//
//    }
}
