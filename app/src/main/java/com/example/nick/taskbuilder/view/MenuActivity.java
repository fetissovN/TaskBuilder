package com.example.nick.taskbuilder.view;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.nick.taskbuilder.R;
import com.example.nick.taskbuilder.util.Strings;

public class MenuActivity extends AppCompatActivity implements View.OnClickListener{

    EditText editTextSave;
    Button save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int mode = getIntent().getIntExtra("menu",0);
        if (mode == Strings.MODE_INFO){
            setContentView(R.layout.activity_menu_info);
        }else if (mode == Strings.MODE_SETTINGS){
            setContentView(R.layout.activity_menu_setting);
            editTextSave = findViewById(R.id.etDays);
            save = findViewById(R.id.btnSaveMenu);
            save.setOnClickListener(this);
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            Integer savedInt = sharedPreferences.getInt(Strings.DAYS_DELAY_TO_DELETE, -1);
            if (savedInt != -1){
                editTextSave.setText(savedInt.toString());
            }

        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnSaveMenu:
                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                SharedPreferences.Editor ed = sharedPreferences.edit();
                ed.putInt(Strings.DAYS_DELAY_TO_DELETE, Integer.parseInt(editTextSave.getText().toString()));
                ed.commit();
                Toast.makeText(this, "Saved", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
