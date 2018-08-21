package com.projectwork.selfmanage;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;

import com.projectwork.selfmanage.task.Task;

public class TaskActivity extends AppCompatActivity implements View.OnClickListener {

    int DIALOG_TIME = 1;
    int DIALOG_DATE = 2;
    int myHour = 12;
    int myMinute = 00;
    int myYear = 2018;
    int myMonth = 01;
    int myDay = 01;
    Button saveBtn;
    ImageButton buttonDateTime;
    EditText eTextName, eTextDuration;
    TextView tvDateTime;
    CheckBox chbRepeat, chbTermless;
    Task task;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.task_activity);
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
        tvDateTime = (TextView) findViewById(R.id.tvDateTime);
        eTextDuration = (EditText) findViewById(R.id.eTextDuration);
        eTextDuration.addTextChangedListener(eTextDurationWatcher);
        chbTermless = (CheckBox) findViewById(R.id.chBtermless);
        //TODO: обработчики кнопок ежедневного повторения
        buttonDateTime = (ImageButton) findViewById(R.id.buttonDateTime);
        saveBtn.setOnClickListener(this);
    }

    private TextWatcher eTextDurationWatcher = new TextWatcher() {
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String working = s.toString();
            boolean isValid = true;
            if (working.length() == 2 && before == 0) {
                if (Integer.parseInt(working) < 0 || Integer.parseInt(working) > 24) {
                    isValid = false;
                } else {
                    working += " ч.";
                    eTextDuration.setText(working);
                    eTextDuration.setSelection(working.length());
                }
            } else if (working.length() != 2) {
                isValid = false;
            }

            if (!isValid) {
                eTextDuration.setError("Введите время в формате: ЧЧ");
            } else {
                eTextDuration.setError(null);
            }
        }

        @Override
        public void afterTextChanged(Editable s) {
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

    };

    public void setTime(View view) {
        showDialog(DIALOG_TIME);
        showDialog(DIALOG_DATE);
    }

    protected Dialog onCreateDialog(int id) {
        if (id == DIALOG_TIME) {
            TimePickerDialog tpd = new TimePickerDialog(this, myCallBack, myHour, myMinute, true);
            return tpd;
        }

        if (id == DIALOG_DATE) {
            DatePickerDialog tpd = new DatePickerDialog(this, myCallBack1, myYear, myMonth, myDay);
            return tpd;
        }

        return super.onCreateDialog(id);
    }

    OnTimeSetListener myCallBack = new OnTimeSetListener() {
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            myHour = hourOfDay;
            myMinute = minute;
            String str = tvDateTime.getText().toString();
            tvDateTime.setText(str + myHour + ":" + myMinute + " ");
        }
    };

    DatePickerDialog.OnDateSetListener myCallBack1 = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            myYear = year;
            myMonth = monthOfYear;
            myDay = dayOfMonth;
            tvDateTime.setText(myDay + "." + myMonth + "." + myYear + " ");
        }
    };

    @Override
    public void onClick(View v) {

        String res = "";
        StringBuilder sb = new StringBuilder(100);

        switch (v.getId()) {

            case R.id.saveBtn: {
                String name, datetime, duration, repeatdays, chbTerm;
                name = eTextName.getText().toString();
                datetime = tvDateTime.getText().toString();
                duration = eTextDuration.getText().toString();
                //TODO: назначать 0 или 1 в зависимости от того, нажата кнопка соответствующего дня или нет
                repeatdays = "0000000";
                chbTerm = chbTermless.isChecked() ? "1" : "0";

                task = new Task(name, datetime, duration, repeatdays, chbTerm);

                // Пересылка на главный экран
                //TODO: Сохранение в singlton-БД вместо возврата intent
                Intent intent = new Intent();
                intent.putExtra("result", (Parcelable) task);      //Возврат названия задачи на главный экран
                setResult(RESULT_OK, intent);
                finish();
            }
                break;
        }
    }

    void inputError() {
        //TODO: вывод предупреждающего сообщения
    }
}
