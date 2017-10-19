package com.projectwork.selfmanage;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

public class TaskActivity extends AppCompatActivity implements View.OnClickListener {

    Button saveBtn, cancelBtn;
    EditText eTextName, eTextDateTime, eTextDuration;
    TextView testTView;
    CheckBox chbRepeat, chbTermless;
    Task task;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.task);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();
            }
        });

        saveBtn = (Button) findViewById(R.id.saveBtn);
        eTextName = (EditText) findViewById(R.id.taskName);
        eTextDateTime = (EditText) findViewById(R.id.eTextDateTime);
        eTextDuration = (EditText) findViewById(R.id.eTextDuration);
        chbRepeat = (CheckBox) findViewById(R.id.chBrepeat);
        chbTermless = (CheckBox)  findViewById(R.id.chBtermless);
        saveBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        String res = "";
        StringBuilder sb = new StringBuilder(100);
        switch (v.getId()) {
            case R.id.saveBtn:
                task = new Task(eTextName.getText().toString(), eTextDateTime.getText().toString(),
                        eTextDuration.getText().toString(), chbRepeat.isChecked() ? "1" : "0",
                        chbTermless.isChecked() ? "1" : "0");

                // Пересылка на главный экран
                //TODO: Добавление данных в базу (сериализация)
                Intent intent = new Intent();
                intent.putExtra("result", (Parcelable) task);      //Возврат названия задачи на главный экран
                setResult(RESULT_OK, intent);
                finish();
                break;
        }
    }
}
