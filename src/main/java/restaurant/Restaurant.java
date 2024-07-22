package restaurant;

public class Restaurant {

    private String name;
    private GeneralLocation generalLocation;
    private Cuisine type;

    public Restaurant(String name, GeneralLocation generalLocation, Cuisine type) {
        this.name = name;
        this.generalLocation = generalLocation;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public GeneralLocation getGeneralLocation() {
        return generalLocation;
    }

    public void setGeneralLocation(GeneralLocation generalLocation) {
        this.generalLocation = generalLocation;
    }

    public Cuisine getType() {
        return type;
    }

    public void setType(Cuisine type) {
        this.type = type;
    }
}
