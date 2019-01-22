package saarland.dfki.socialanxietytrainer.task;

import saarland.dfki.socialanxietytrainer.classification.AnxietyLevel;

public class TaskManager {

    private final TaskSet tasks;

    public TaskManager(TaskSet tasks) {
        this.tasks = tasks;
    }

    public TaskSet select(AnxietyLevel level) {
        assert (level != AnxietyLevel.NONE);
        return tasks.getTasksSelection(level);
    }

    public TaskSet getTasks() {
        return tasks;
    }

}
