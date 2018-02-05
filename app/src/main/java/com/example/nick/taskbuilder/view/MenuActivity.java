package com.example.nick.taskbuilder.view;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.nick.taskbuilder.R;
import com.example.nick.taskbuilder.util.Strings;

public class MenuActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText editTextSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int mode = getIntent().getIntExtra("menu",0);
        if (mode == Strings.MODE_INFO){
            setContentView(R.layout.activity_menu_info);
        }else if (mode == Strings.MODE_SETTINGS){
            setContentView(R.layout.activity_menu_setting);
            editTextSave = findViewById(R.id.etDays);
            editTextSave.setOnClickListener(this);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnSaveMenu:
                SharedPreferences sharedPreferences = getPreferences(MODE_PRIVATE);
                SharedPreferences.Editor ed = sharedPreferences.edit();
                ed.putInt(Strings.DAYS_DELAY_TO_DELETE, Integer.parseInt(editTextSave.getText().toString()));
                ed.commit();
                Toast.makeText(this, "Saved", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
