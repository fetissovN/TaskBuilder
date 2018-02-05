package com.example.nick.taskbuilder.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.nick.taskbuilder.comparator.TaskDBComparator;
import com.example.nick.taskbuilder.entity.Task;
import com.example.nick.taskbuilder.entity.TaskDB;
import com.example.nick.taskbuilder.util.TaskDBToTaskConverter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DB implements DataSourceInterface{

    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_DESCRIPTION= "description";
    private static final String COLUMN_START = "starts";
    private static final String COLUMN_END = "expires";
    private static final String COLUMN_PRIORITY = "priority";

    private final Context mCtx;

    private DBHelper mDBHelper;
    private SQLiteDatabase mDB;

    private List<TaskDB> listOfData = new ArrayList<>();

    public DB(Context ctx) {
        mCtx = ctx;
    }

    public void open() {
        if (mDB == null){
            mDBHelper = new DBHelper(mCtx);
            mDB = mDBHelper.getWritableDatabase();
        }
    }

    public void close() {
        if (mDBHelper!=null) mDBHelper.close();
    }

    public List<TaskDB> getDayTasksSheduled(long startDay, long endDay){
        Cursor c = mDB.rawQuery("SELECT * FROM "
                +DBHelper.DB_TABLE_NAME + " WHERE starts BETWEEN '"
                + startDay + "' AND '" + endDay + "'", null);
        if (c == null){
            return null;
        }
        if (c.moveToFirst()) {
            logTableAndAddToList(c);
        } else{
            Log.d("test", "0 rows");
        }
        Collections.sort(listOfData, new TaskDBComparator());
        return listOfData;
    }

    @Override
    public List<Task> getListOfTasks() {
        getListOfTasksDB();
        List<Task> list = TaskDBToTaskConverter.convertList(listOfData);
        listOfData = null;
        return list;
    }

    @Override
    public List<TaskDB> getListOfTasksDB() {
        Cursor c = mDB.query(DBHelper.DB_TABLE_NAME
                , null, null, null
                , null, null, null);
        if (c == null){
            return null;
        }
        if (c.moveToFirst()) {
            logTableAndAddToList(c);
        } else{
            Log.d("test", "0 rows");
        }
        Collections.sort(listOfData,new TaskDBComparator());
        return listOfData;
    }

    @Override
    public List<Task> getListOfTasksActual(long timeFrom) {
        List<TaskDB> actualList = new ArrayList<>();
        for (TaskDB t: getListOfTasksDB()) {
            if (t.getStartes()>timeFrom){
                actualList.add(t);
            }
        }
        Collections.sort(actualList, new TaskDBComparator());
        return TaskDBToTaskConverter.convertList(actualList);
    }

    @Override
    public void updateTask(TaskDB taskDB) {

    }

    private void logTableAndAddToList(Cursor c) {
        int idColIndex = c.getColumnIndex("_id");
        int nameColIndex = c.getColumnIndex("name");
        int descriptionColIndex = c.getColumnIndex("description");
        int startColIndex = c.getColumnIndex("starts");
        int expiresColIndex = c.getColumnIndex("expires");
        int priorityColIndex = c.getColumnIndex("priority");
        do {
            TaskDB listTask = new TaskDB();
            listTask.setId(c.getLong(idColIndex));
            listTask.setName(c.getString(nameColIndex));
            listTask.setDescription(c.getString(descriptionColIndex));
            listTask.setStartes(c.getLong(startColIndex));
            listTask.setExpires(c.getLong(expiresColIndex));
            listTask.setPriority(c.getString(priorityColIndex));
            listOfData.add(listTask);
            Log.d("test",
                    "ID = " + c.getInt(idColIndex) +
                            ", name = " + c.getString(nameColIndex) +
                            ", description = " + c.getString(descriptionColIndex) +
                            ", start = " + c.getString(startColIndex) +
                            ", end = " + c.getString(expiresColIndex) +
                            ", priority = " + c.getString(priorityColIndex));
        } while (c.moveToNext());
    }

    @Override
    public List<Task> getWeekListOfTasks() {
        return null;
    }

    @Override
    public void deleteTask(Task listTask) {
        long id = listTask.getId();
        mDB.delete(DBHelper.DB_TABLE_NAME, COLUMN_ID + " = " + id, null);
    }

    /*
    * delete all tasks from the very first to @param = timeTo(milliseconds)
    * */
    @Override
    public int deletePrevious(long timeTo) {
        return mDB.delete(DBHelper.DB_TABLE_NAME, COLUMN_START + " < " + timeTo, null);
    }

    @Override
    public void addRec(TaskDB task) {
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_NAME, task.getName());
        cv.put(COLUMN_DESCRIPTION, task.getDescription());
        cv.put(COLUMN_START, task.getStartes());
        cv.put(COLUMN_END, task.getExpires());
        cv.put(COLUMN_PRIORITY, task.getPriority());
        mDB.insert(DBHelper.DB_TABLE_NAME, null, cv);
        System.out.println("inserted");
    }
}
