package saarland.dfki.socialanxietytrainer.task;

public class Task {

    private int id;
    private String description;
    private String category;
    private int level;

    public Task(int id, String desc, String category, int level) {
        this.id = id;
        this.description = desc;
        this.category = category;
        this.level = level;
    }

    public int getLevel() {
        return level;
    }

    public int getId() {
        return id;
    }

    public String getCategory() {
        return category;
    }

    public String getDescription() {
        return description;
    }

    public void setLevel(int level) {
        this.level = level;
    }


    @Override
    public String toString() {
        return "description: " + description + "\n" +
                " category: " + category + "\n" +
                "level: " + level;
    }
}
