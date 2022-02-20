package gr.hua.ds.freetransportation;

public enum RoleTypes {
    DEFAULT_USER("DEFAULT_USER"),
    UNEMPLOYED("UNEMPLOYED"),
    OAED_EMPLOYEE("OAED_EMPLOYEE"),
    TRANSPORTATION_EMPLOYEE("TRANSPORTATION_EMPLOYEE"),
    ADMIN("ADMIN");

    private final String text;

    /**
     * @param text
     */
    RoleTypes(final String text) {
        this.text = text;
    }

    public String toString() {
        return text;
    }
}
