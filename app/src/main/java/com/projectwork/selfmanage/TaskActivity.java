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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.widget.Toast;

import com.projectwork.selfmanage.task.Task;

import java.util.Calendar;

public class TaskActivity extends AppCompatActivity implements View.OnClickListener {

    int DIALOG_TIME = 1;
    int DIALOG_DATE = 2;
    String[] repeat = {"0", "0", "0", "0", "0", "0", "0"};

    Button saveBtn;
    ImageButton buttonDateTime;
    EditText eTextName, eTextDuration;
    TextView tvDateTime;
    RadioGroup rGroup;
    Task task;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.task_activity);
        Toolbar toolbar = findViewById(R.id.toolbar);
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

        saveBtn = findViewById(R.id.saveBtn);
        eTextName = findViewById(R.id.taskName);
        tvDateTime = findViewById(R.id.tvDateTime);

        rGroup = findViewById(R.id.rGroup);
        rGroup.setOnCheckedChangeListener(rGroupOnCheckWatcher);

        eTextDuration = findViewById(R.id.eTextDuration);
        eTextDuration.addTextChangedListener(eTextDurationWatcher);


        buttonDateTime = findViewById(R.id.buttonDateTime);
        saveBtn.setOnClickListener(this);

    }


    RadioGroup.OnCheckedChangeListener rGroupOnCheckWatcher = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            if (checkedId == R.id.rBTermless) {
                eTextDuration.setVisibility(View.GONE);
            }
            else {
                eTextDuration.setVisibility(View.VISIBLE);
            }
        }
    };

    public void onToggle(View view) {
        repeat[0] = "1";
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
        // TODO: обработать кнопку "отмена"
        // TODO: alwaysnull в дате и времени

        final Calendar calendar = Calendar.getInstance();


        if (id == DIALOG_DATE) {
            DatePickerDialog tpd = new DatePickerDialog(this, R.style.Theme_Design_Light,
                    dateCallback, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
            return tpd;
        }

        if (id == DIALOG_TIME) {
            TimePickerDialog tpd = new TimePickerDialog(this, timeCallback,
                    calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true);
            return tpd;
        }

        return super.onCreateDialog(id);
    }

    DatePickerDialog.OnDateSetListener dateCallback = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            tvDateTime.setText(String.format("%02d.%02d.%02d", year, monthOfYear, dayOfMonth));
        }
    };

    OnTimeSetListener timeCallback = new OnTimeSetListener() {
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

            String str = tvDateTime.getText().toString();
            tvDateTime.append(" " + String.format("%02d:%02d", hourOfDay, minute));
        }
    };

    // TODO: назначить onClickListener нормально
    @Override
    public void onClick(View v) {

        String res = "";
        StringBuilder sb = new StringBuilder(100);

        switch (v.getId()) {

            case R.id.saveBtn: {

                String termless;
                String duration = null;

                String name = eTextName.getText().toString();
                String datetime = tvDateTime.getText().toString();

                termless = (rGroup.getCheckedRadioButtonId() == R.id.rBTermless) ? "1" : "0"; // 0 - нет, 1 - да
                if(termless.equals("0")) {
                    duration = eTextDuration.getText().toString();
                }

                task = new Task(name, datetime, duration, repeat, termless);

                // Пересылка на главный экран
                Intent intent = new Intent();
                intent.putExtra("result", (Parcelable) task);      //Возврат названия задачи на главный экран
                setResult(RESULT_OK, intent);
                finish();
            }
                break;
        }
    }
}
