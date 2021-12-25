package gr.hua.ds.freetransportation.entities;

public enum RoleTypes {
    DEFAULT_USER(1),
    UNEMPLOYED(2),
    OAED_EMPLOYEE(3),
    TRANSPORTATION_EMPLOYEE(4),
    ADMIN(5);

    private final int text;

    /**
     * @param text
     */
    RoleTypes(final int text) {
        this.text = text;
    }

    public int toInt() {
        return text;
    }
}
