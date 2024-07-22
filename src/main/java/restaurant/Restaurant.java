package restaurant;

public class Restaurant {

    private String name;
    private GeneralLocation generalLocation;
    private Cuisine type;

    public Restaurant(String name, Cuisine type, GeneralLocation generalLocation) {
        this.name = name;
        this.generalLocation = generalLocation;
        this.type = type;
    }

    public Restaurant() {
        name = "";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNormalizedName() {
        String normalizedName = name.toLowerCase();
        normalizedName = normalizedName.replaceAll("\\s","");
        return normalizedName;
    }

    public String getGeneralLocation() {
        return generalLocation.toString();
    }

    public void setGeneralLocation(GeneralLocation generalLocation) {
        this.generalLocation = generalLocation;
    }

    public String getType() {
        return type.toString();
    }

    public void setType(Cuisine type) {
        this.type = type;
    }
}
