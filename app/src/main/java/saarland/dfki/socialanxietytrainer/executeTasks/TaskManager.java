package saarland.dfki.socialanxietytrainer.executeTasks;

import java.util.ArrayList;

public class TaskManager {

    private int[] user_levels;
    private ArrayList<Task> tasks[][];
    private int nr_categories;


    public TaskManager(ArrayList<Task> tasks[][], int[] user_levels) {
        this.tasks = tasks;
        this.nr_categories = tasks.length;
        this.user_levels = user_levels;
    }

    public ArrayList<Task> select() {
        ArrayList<Task> selection = new ArrayList<>();
        int random_index;
        int min = 0;
        int max;
        for (int category = 0; category < nr_categories; category++) {
            int user_level = user_levels[category];
            max = tasks[category][user_level - 1].size() - 1;
            random_index = min + (int)(Math.random() * max);
            selection.add(tasks[category][user_level - 1].get(random_index));
        }

        return selection;
    }

    public void decreaseLevel(int category) {
        if(user_levels[category] > 1) {
            user_levels[category]--;
        }
    }

    public void increaseLevel(int category) {
        if(user_levels[category] < 3) {
            user_levels[category]++;
        }
    }
    public ArrayList<Task>[][] getTasks() {
        return tasks;
    }

    public int getNr_categories() {
        return nr_categories;
    }

    public int[] getUser_levels() {
        return user_levels;
    }

    public void setUser_levels(int[] user_levels) {
        this.user_levels = user_levels;
    }
}
