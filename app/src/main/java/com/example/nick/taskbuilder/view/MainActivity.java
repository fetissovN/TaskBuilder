package com.example.nick.taskbuilder.view;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.nick.taskbuilder.R;
import com.example.nick.taskbuilder.adapter.SectionsPageAdapter;
import com.example.nick.taskbuilder.service.CleanDBService;
import com.example.nick.taskbuilder.util.Strings;
import com.example.nick.taskbuilder.view.fragments.CalendarFragment;
import com.example.nick.taskbuilder.view.fragments.CurrentWeekScheduleFragment;
import com.example.nick.taskbuilder.view.fragments.DayScheduleFragment;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity{

    private SectionsPageAdapter sectionsPageAdapter;
    private ViewPager viewPager;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        getBaseContext().deleteDatabase("tasks");
//        sectionsPageAdapter = new SectionsPageAdapter(getSupportFragmentManager());
        viewPager = (ViewPager) findViewById(R.id.pager);
        setupViewPager(viewPager);
        viewPager.setCurrentItem(1);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        Integer savedInt = sharedPreferences.getInt(Strings.DAYS_DELAY_TO_DELETE, -1);
        long daysInMillis;
        if (savedInt != -1){
            daysInMillis = savedInt*24*3600*1000;
        }else {
            daysInMillis = 5*24*3600*1000;
        }
        long timeTo = (Calendar.getInstance().getTimeInMillis() - daysInMillis);
        Intent intent = new Intent(this, CleanDBService.class);
        intent.putExtra("timeTo", timeTo);
        startService(intent);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_info){
            Intent intent = new Intent(this, MenuActivity.class);
            intent.putExtra("menu", Strings.MODE_INFO);
            startActivity(intent);
        }
        if (id == R.id.action_settings){
            Intent intent = new Intent(this, MenuActivity.class);
            intent.putExtra("menu", Strings.MODE_SETTINGS);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }


    private void setupViewPager (ViewPager viewPager){
        sectionsPageAdapter = new SectionsPageAdapter(getSupportFragmentManager());
        sectionsPageAdapter.addFragment(new CurrentWeekScheduleFragment(),"All in month");
        sectionsPageAdapter.addFragment(new DayScheduleFragment(),"Your day");
        sectionsPageAdapter.addFragment(new CalendarFragment(),"Calendar");
        viewPager.setAdapter(sectionsPageAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
