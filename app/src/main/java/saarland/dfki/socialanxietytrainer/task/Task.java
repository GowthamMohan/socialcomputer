package saarland.dfki.socialanxietytrainer.task;

public class Task {

    private int id;
    private String description;
    private String category;
    private int level;
    private int categoryID;

    public Task(int id, String desc, String category, int level) {
        this.id = id;
        this.description = desc;
        this.category = category;
        this.level = level;
        switch (category) {
            case "Talking with Strangers":
                categoryID = 0; break;
            case "Speaking in Public/Talking with people in authority":
                categoryID = 1; break;
            case "Interactions with the opposite sex":
                categoryID = 2; break;
            case "Criticism and embarrassment":
                categoryID = 3; break;
            case "Assertive expression of annoyance, disgust or displeasure":
                categoryID = 4; break;
            default:
                categoryID = -1; break;
        }
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

    public int getCategoryID() {
        return categoryID;
    }

    @Override
    public String toString() {
        return "description: " + description + "\n" +
                " category: " + category + "\n" +
                "level: " + level;
    }
}
