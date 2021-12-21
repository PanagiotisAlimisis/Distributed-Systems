package gr.hua.ds.freetransportation.entities;

public enum RoleTypes {
    UNEMPLOYED(1),
    OAED_EMPLOYEE(2),
    TRANSPORTATION_EMPLOYEE(3),
    ADMIN(4);

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
