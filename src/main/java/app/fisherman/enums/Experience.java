package app.fisherman.enums;

public enum Experience {

    //TODO: Every Experience level must be associated with the corresponding initial cash amount. Think a way to do it.
//    100
//    200
//    300
    BEGINNER("Beginner"),
    INTERMEDIATE("Intermediate"),
    EXPERT("Expert");

    private final String displayName;

    Experience(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
