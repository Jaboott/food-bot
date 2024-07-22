package restaurant;

public enum GeneralLocation {
    WEST_VAN("West Vancouver"),
    NORTH_VAN("North Vancouver"),
    DOWNTOWN("Downtown"),
    BURNABY("Burnaby"),
    RICHMOND("Richmond"),
    SURREY("Surrey"),
    COQUITLAM("Coquitlam"),
    OTHER("Other");

    private final String location;

    GeneralLocation(String location) {
        this.location = location;
    }

    public String getLocation() {
        return location;
    }
}

