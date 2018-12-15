package saarland.dfki.socialanxietytrainer.execute_tasks;

public class Task {

    private int id;
    private String description;
    private String category;
    private int difficulty;

    public Task(int id, String desc, String cat, int diff) {
        this.id = id;
        this.description = desc;
        this.category = cat;
        this.difficulty = diff;
    }

    public int getDifficulty() {
        return difficulty;
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


    //for adapting difficulty later
    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }


    @Override
    public String toString() {
        return "description: " + description + "\n" +
                " category: " + category + "\n" +
                "difficulty: " + difficulty;
    }
}
