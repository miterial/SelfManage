package com.projectwork.selfmanage;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class Task implements Parcelable, Serializable {

    private String name, dateStart, duration;
    //TODO: парсинг repeat, превращение в дни
    private String repeat;          //Повторяемость (список дней)
    private int termless;   //Выбрано ли "каждый день" на экране выпбора времени
    private boolean checked;        //Отметка о выполнении

    public Task(String name, String dateStart, String duration, String repeat, String termless) {
        this.name = name;
        this.dateStart = dateStart;
        this.duration = duration;
        this.repeat = repeat;
        this.termless = Integer.valueOf(termless);
        this.checked = false;
    }

    public Task(Parcel in) {
        this.name = in.readString();
        this.dateStart = in.readString();
        this.duration = in.readString();
        this.repeat = in.readString();
        this.termless = in.readInt();
        this.checked = in.readString().equals("0") ? true : false;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDateStart() {
        return dateStart;
    }

    public void setDateStart(String dateStart) {
        this.dateStart = dateStart;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getRepeat() {
        return repeat;
    }

    public void setRepeat(String repeat) {
        this.repeat = repeat;
    }

    public int isTermless() { return termless; }

    public void setTermless(int termless) {
        this.termless = termless;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Task createFromParcel(Parcel in ) {
            return new Task( in );
        }

        public Task[] newArray(int size) {
            return new Task[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    //Сохранение данных задания для передачи между активностями
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(dateStart);
        dest.writeString(duration);
        dest.writeString(repeat);
        dest.writeInt(termless);

        String ch = checked ? "0" : "1";
        dest.writeString(ch);
    }
}
