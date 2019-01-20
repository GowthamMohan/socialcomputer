package saarland.dfki.socialanxietytrainer.task;

import com.google.gson.Gson;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import saarland.dfki.socialanxietytrainer.classification.AnxietyLevel;

public class TaskSet {

    private final static Map<AnxietyLevel, Integer> LEVEL_MAP = new HashMap<>();
    static {
        // Note: The Mapping from/to integer is inversed
        LEVEL_MAP.put(AnxietyLevel.NONE, -1);
        LEVEL_MAP.put(AnxietyLevel.LOW, 3);
        LEVEL_MAP.put(AnxietyLevel.MILD, 2);
        LEVEL_MAP.put(AnxietyLevel.HIGH, 1);
    }

    private List<Task> tasks;

    public TaskSet(List<Task> tasks) {
        this.tasks = tasks;
    }

    public static TaskSet fromJson(String s) {
        return new Gson().fromJson(s, TaskSet.class);
    }

    private int getCorrespondingLevel(AnxietyLevel level) {
        assert(LEVEL_MAP.containsKey(level));
        return LEVEL_MAP.get(level);
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    public Task get(int i) {
        return this.tasks.get(i);
    }

    public int getSize() {
        return this.tasks.size();
    }

    public static Set<String> getCategories(TaskSet tasks) {
        return tasks.getTasks().stream().map(Task::getCategory).collect(Collectors.toSet());
    }

    public static Task getRandomTaskFromCategory(TaskSet tasks, String category) {
        List<Task> filtered = tasks.getTasks().stream()
                .filter(task -> task.getCategory().equals(category))
                .collect(Collectors.toList());
        Collections.shuffle(filtered);
        return filtered.get(0);
    }

    public TaskSet getTasksFilteredByAnxietyLevel(AnxietyLevel level) {
        int levelInt = getCorrespondingLevel(level);
        List<Task> collect = tasks.stream()
                .filter(task -> task.getLevel() == levelInt).collect(Collectors.toList());
        return new TaskSet(collect);
    }

    public TaskSet getTasksSelection(AnxietyLevel level) {
        TaskSet tasksLevel = getTasksFilteredByAnxietyLevel(level);
        Set<String> categories = getCategories(tasksLevel);
        List<Task> randomTasks = categories.stream()
                .map(c -> getRandomTaskFromCategory(tasksLevel, c)).collect(Collectors.toList());
        return new TaskSet(randomTasks);
    }

}
