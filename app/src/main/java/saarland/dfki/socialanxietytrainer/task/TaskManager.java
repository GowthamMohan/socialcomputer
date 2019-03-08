package saarland.dfki.socialanxietytrainer.task;

import java.util.ArrayList;
import java.util.List;

import saarland.dfki.socialanxietytrainer.MainActivity;
import saarland.dfki.socialanxietytrainer.Preferences;
import saarland.dfki.socialanxietytrainer.classification.AnxietyLevel;

public class TaskManager {


    private List<Task> task_list;
    private int nr_tasks;
    private int[] current_levels;


    public TaskManager(TaskSet taskSet) {
        this.task_list = taskSet.getTasks();
        nr_tasks = task_list.size();

    }


    public List<Task> getSelection() {
        current_levels = getLevels();
        List<Task> selection = new ArrayList<>();
        for (int i = 0; i < nr_tasks; i++) {
            Task task = task_list.get(i);
            if(task.getLevel() == current_levels[task.getCategoryID()]) {
                selection.add(task);
            }
        }
        return task_list;
    }


    private int[] getLevels() {

        int[] levels = new int[5];

        levels[0] = Preferences.Companion.getLevelC1(MainActivity.Companion.getContext());
        levels[1] = Preferences.Companion.getLevelC2(MainActivity.Companion.getContext());
        levels[2] =Preferences.Companion.getLevelC3(MainActivity.Companion.getContext());
        levels[3] = Preferences.Companion.getLevelC4(MainActivity.Companion.getContext());
        levels[4] = Preferences.Companion.getLevelC5(MainActivity.Companion.getContext());


        return levels;
    }


}


