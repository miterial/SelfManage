package com.projectwork.selfmanage;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

import com.projectwork.selfmanage.adapter.TaskListAdapter;
import com.projectwork.selfmanage.task.Task;
import com.projectwork.selfmanage.task.TaskListSerializable;
import com.projectwork.selfmanage.utils.Utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    TaskListSerializable taskList;
    TaskListAdapter tlAdapter;
    ListView lvMain;
    private String dbname = "dbsmanag";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(this);

        File f = new File(getApplicationContext().getFilesDir(), dbname);
        taskList = Utils.loadData(f);
        tlAdapter = new TaskListAdapter(this, taskList);

        lvMain = (ListView) findViewById(R.id.lvMain);
        lvMain.setAdapter(tlAdapter);
    }

    /**
     * Add new task
     *
     * @param v add button view
     */
    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this, TaskActivity.class);
        startActivityForResult(intent, 1);
    }

    /**
     * Save new task to list and serialize new taskList
     *
     * @param requestCode
     * @param resultCode
     * @param data newly created task
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data == null) {
            return;
        }
        Task task = data.getParcelableExtra("result");
        taskList.add(task);     // Добавление задания в список

        File f = new File(getApplicationContext().getFilesDir(), dbname);
        Utils.saveData(taskList, f);     //Сериализация задания

        //TODO: подгружать только последнее добавленное задание
        //TODO: [2] сделать БД, а не сериализацию?
        tlAdapter.notifyDataSetChanged();
    }

    /**
     * Create options menu on top of the screen
     *
     * @param menu
     * @return state of creation ???
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
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
            case R.id.action_delete:
                //tViewMain.setText("Вы выбрали удаление!");
            case R.id.action_color:
                //tViewMain.setText("Вы выбрали цвет!");
            case R.id.action_help:
                //tViewMain.setText("Вы выбрали статистику!");
            default:
                return super.onOptionsItemSelected(item);
        }
        //return true;
    }

}
