package com.projectwork.selfmanage.task;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

/**
 *  Task class implements Patcelable and Serializable in order to write and read serialized tasks to/from file
 */


@Getter
@Setter
public class Task implements Parcelable, Serializable {

    private String name, dateStart, duration;
    //TODO: парсинг repeat, превращение в дни
    private String[] repeat;          //Повторяемость (список дней)
    private int termless;   //Выбрано ли "каждый день" на экране выпбора времени
    private boolean checked;        //Отметка о выполнении

    public Task(String name, String dateStart, String duration, String[] repeat, String termless) {
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
        this.repeat = in.createStringArray();
        this.termless = in.readInt();
        this.checked = in.readString().equals("0");
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

    // Saving task data to pass between activities
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(dateStart);
        dest.writeString(duration);
        dest.writeStringArray(repeat);
        dest.writeInt(termless);

        String ch = checked ? "0" : "1";
        dest.writeString(ch);
    }
}
