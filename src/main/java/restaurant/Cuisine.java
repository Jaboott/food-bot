package restaurant;

public enum Cuisine {
    ITALIAN("Italian"),
    JAPANESE("Japanese"),
    CHINESE("Chinese"),
    INDIAN("Indian"),
    AMERICAN("American"),
    KOREAN("Korean"),
    OTHER("Other");

    private final String type;

    Cuisine(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
