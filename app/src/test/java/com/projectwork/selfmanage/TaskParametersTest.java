package com.projectwork.selfmanage;

import com.projectwork.selfmanage.task.Task;
import com.projectwork.selfmanage.task.TaskListSerializable;
import com.projectwork.selfmanage.utils.Utils;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.io.File;

public class TaskParametersTest {

    @Test
    public void createTask_isCorrect() {

        Task task = new Task("name", "21.12.2012 14:00",
                "1:00", new String []{"0","0","1","1","0","0","0"}, "0");

        File f = new File("TaskParametersTest_DB");
        TaskListSerializable list = new TaskListSerializable();
        list.add(task);
        Utils.saveData(list, f);

        TaskListSerializable tl = Utils.loadData(new File("TaskParametersTest_DB"));
        Assert.assertTrue(tl.size() > 0);
    }
}
