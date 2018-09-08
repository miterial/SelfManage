package com.projectwork.selfmanage.utils;

import com.projectwork.selfmanage.task.Task;
import com.projectwork.selfmanage.task.TaskListSerializable;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * Utils is a final class that contains static help methods
 */

public final class Utils {

    public static void saveTaskList(TaskListSerializable taskList, File f) {
        FileOutputStream fos;
        ObjectOutputStream out;
        File file;
        try {
            file = f;
            fos = new FileOutputStream(file);
            out = new ObjectOutputStream(fos);
            out.writeObject(taskList);
            out.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static TaskListSerializable loadData(File f) {
        TaskListSerializable tmptl = new TaskListSerializable();
        FileInputStream fis;
        ObjectInputStream in;
        File file;
        try {
            file = f;
            fis = new FileInputStream(file);
            in = new ObjectInputStream(fis);
            tmptl = (TaskListSerializable) in.readObject();
            in.close();
        } catch (IOException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        return tmptl;
    }

    public static void saveSingleTask(Task task, File f) {
        FileOutputStream fos;
        ObjectOutputStream out;
        File file;
        try {
            file = f;
            fos = new FileOutputStream(file);
            out = new ObjectOutputStream(fos);
            out.writeObject(task);
            out.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static Task loadSingleTask(File f) {
        Task tmptl = new Task();
        FileInputStream fis;
        ObjectInputStream in;
        File file;
        try {
            file = f;
            fis = new FileInputStream(file);
            in = new ObjectInputStream(fis);
            tmptl = (Task) in.readObject();
            in.close();
        } catch (IOException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        return tmptl;
    }
}
