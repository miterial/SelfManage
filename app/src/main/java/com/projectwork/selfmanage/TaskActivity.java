package com.projectwork.selfmanage;

import android.app.AlertDialog;
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
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.widget.Toast;
import android.widget.ToggleButton;

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
        buttonDateTime = (ImageButton) findViewById(R.id.buttonDateTime);
        saveBtn.setOnClickListener(this);

        ((RadioGroup) findViewById(R.id.toggleGroup)).setOnCheckedChangeListener(ToggleListener);

    }

    static final RadioGroup.OnCheckedChangeListener ToggleListener = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(final RadioGroup radioGroup, final int i) {
            for (int j = 0; j < radioGroup.getChildCount(); j++) {
                final ToggleButton view = (ToggleButton) radioGroup.getChildAt(j);
                view.setChecked(view.getId() == i);
            }
        }
    };

    public void onToggle(View view) {
        ((RadioGroup)view.getParent()).check(view.getId());

        Toast.makeText(this,"checked", Toast.LENGTH_SHORT).show();
    }

    private TextWatcher eTextDurationWatcher = new TextWatcher() {
        private boolean isRunning = false;
        private boolean isDeleting = false;
        private String mask = "##:##";

        @Override
        public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {
            isDeleting = count > after;
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
        }

        @Override
        public void afterTextChanged(Editable editable) {
            if (isRunning || isDeleting) {
                return;
            }
            isRunning = true;

            int editableLength = editable.length();
            if (editableLength < mask.length()) {
                if (mask.charAt(editableLength) != '#') {
                    editable.append(mask.charAt(editableLength));
                } else if (mask.charAt(editableLength-1) == '#') {

                    editable.insert(editableLength-1, mask, editableLength-1, editableLength);
                }
            }
            else {

                String tokens[] = editable.toString().split("[:]");
                if (Integer.parseInt(tokens[0]) < 0 || Integer.parseInt(tokens[0]) > 23
                        || Integer.parseInt(tokens[1]) < 0 || Integer.parseInt(tokens[1]) > 59) {
                    eTextDuration.setError("Время введено некорректно!"); //TODO: переместить в класс
                }
            }

            isRunning = false;
        }

    };

    public void setTime(View view) {
        showDialog(DIALOG_TIME);
        showDialog(DIALOG_DATE);
    }

    protected Dialog onCreateDialog(int id) {
        if (id == DIALOG_TIME) {
            TimePickerDialog tpd = new TimePickerDialog(this, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT,
                    timeCallback, myHour, myMinute, true);
            return tpd;
        }

        if (id == DIALOG_DATE) {
            DatePickerDialog tpd = new DatePickerDialog(this, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT,
                    dateCallback, myYear, myMonth, myDay);
            return tpd;
        }

        return super.onCreateDialog(id);
    }

    OnTimeSetListener timeCallback = new OnTimeSetListener() {
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            myHour = hourOfDay;
            myMinute = minute;
            String str = tvDateTime.getText().toString();
            tvDateTime.setText(str + myHour + ":" + myMinute + " ");
        }
    };

    DatePickerDialog.OnDateSetListener dateCallback = new DatePickerDialog.OnDateSetListener() {
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
