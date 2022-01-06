package gr.hua.ds.freetransportation;

public enum Status {
    PENDING("PENDING"),
    ACCEPT("ACCEPT"),
    REJECT("REJECT");

    private final String text;

    /**
     * @param text
     */
    Status(final String text) {
        this.text = text;
    }

    public String toString() {
        return text;
    }
}
