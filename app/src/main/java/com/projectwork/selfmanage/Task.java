package com.projectwork.selfmanage;

/**
 * Created by Svetlana on 23.09.2017.
 */

public class Task {

    String name, dateStart, duration;
    String repeat;          //Повторяемость (список дней)
    boolean termless;   //Выбрано ли "каждый день" на экране выпбора времени
    boolean checked;        //Отметка о выполнении

    public Task(String[] tokens) {
        this.name = tokens[0];
        this.dateStart = tokens[1];
        this.duration = tokens[2];
        this.repeat = tokens[3];
        this.termless = Boolean.valueOf(tokens[4]);
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

    public boolean isTermless() {
        return termless;
    }

    public void setTermless(boolean termless) {
        this.termless = termless;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }
}
