package com.projectwork.selfmanage;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;

public class AddTask extends AppCompatActivity implements View.OnClickListener {
    int DIALOG_TIME = 1;
    int myHour = 24;
    int myMinute = 59;
    Button saveBtn, cancelBtn;
    EditText eTextName, eTextDateTime, eTextDuration;
    TextView testTView;
    CheckBox chbRepeat, chbTermless;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_task);
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
        chbTermless = (CheckBox) findViewById(R.id.chBtermless);
        saveBtn.setOnClickListener(this);
        eTextDateTime = (EditText) findViewById(R.id.eTextDateTime);
    }

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
                // Получение параметров задания
                sb.append(eTextName.getText().toString());
                sb.append(",");
                sb.append(eTextDateTime.getText().toString());
                sb.append(",");
                sb.append(eTextDuration.getText().toString());
                sb.append(",");
                if(chbRepeat.isChecked())
                    sb.append(1);
                else sb.append(0);
                sb.append(",");
                if(chbTermless.isChecked())
                    sb.append(1);
                else sb.append(0);

                // Пересылка на главный экран
                //TODO: Добавление данных в базу
                Intent intent = new Intent();
                intent.putExtra("result", sb.toString());      //Возврат названия задачи на главный экран
                setResult(RESULT_OK, intent);
                finish();
                break;
        }
    }
}
