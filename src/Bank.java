public enum Bank {
    BNI("Bank BNI"),
    BRI("Bank BRI"),
    MANDIRI("Bank Mandiri"),
    BCA("Bank BCA"),
    SEABANK("SeaBank"),
    BSI("Bank BSI"),
    CIMB("Bank CIMB Niaga");

    private final String displayName;

    Bank(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}