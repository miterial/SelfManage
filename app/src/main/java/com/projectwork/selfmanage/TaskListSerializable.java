package com.projectwork.selfmanage;

import java.io.Serializable;
import java.util.ArrayList;

public class TaskListSerializable implements Serializable {

    private ArrayList<Task> taskList;

    public TaskListSerializable() {
        taskList = new ArrayList<>(64); //64 задания max
    }

    public void add(Task t) {
        taskList.add(t);
    }

    public int size() {
        return taskList.size();
    }

    public Task get(int pos) {
        return taskList.get(pos);
    }
}
