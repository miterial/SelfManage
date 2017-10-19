package com.projectwork.selfmanage;

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
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TaskActivity extends AppCompatActivity implements View.OnClickListener {

    int DIALOG_TIME = 1;
    int myHour = 24;
    int myMinute = 59;
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
        eTextDuration.addTextChangedListener(eTextDurationWatcher);
        chbTermless = (CheckBox) findViewById(R.id.chBtermless);
        saveBtn.setOnClickListener(this);
        eTextDateTime = (EditText) findViewById(R.id.eTextDateTime);
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
                eTextDuration.setError("Введите время в формате: HH");
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
    }

    protected Dialog onCreateDialog(int id) {
        if (id == DIALOG_TIME) {
            TimePickerDialog tpd = new TimePickerDialog(this, myCallBack, myHour, myMinute, true);
            return tpd;
        }
        return super.onCreateDialog(id);
    }

    OnTimeSetListener myCallBack = new OnTimeSetListener() {
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            myHour = hourOfDay;
            myMinute = minute;
            eTextDateTime.setText(myHour + ":" + myMinute + " ");
        }
    };

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