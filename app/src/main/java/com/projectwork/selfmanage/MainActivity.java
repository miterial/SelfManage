package com.projectwork.selfmanage;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    TextView tvTask1, tViewMain;

    ArrayList<Task> taskList = new ArrayList<Task>(64);     //64 задания max

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //tvTask1 = (TextView) findViewById(R.id.tvTask1);
        //tViewMain = (TextView) findViewById(R.id.tViewMain);

       FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this, AddTask.class);
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data == null) {return;}
        String result = data.getStringExtra("result");

        String[] tokens = result.split("[,]");
        taskList.add(new Task(tokens));     // Добавление задания в список
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // получим идентификатор выбранного пункта меню
        int id = item.getItemId();

        // Операции для выбранного пункта меню
        switch (id) {
            case R.id.action_settings:
                //tViewMain.setText("Вы выбрали настройки!");
                return true;
            case R.id.action_delete:
                //tViewMain.setText("Вы выбрали удаление!");
                return true;
            case R.id.action_color:
                //tViewMain.setText("Вы выбрали цвет!");
                return true;
            case R.id.action_help:
                //tViewMain.setText("Вы выбрали статистику!");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
