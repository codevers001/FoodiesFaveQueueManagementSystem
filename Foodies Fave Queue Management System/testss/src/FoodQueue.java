class FoodQueue {
    private static final int MAX_WAITING_LIST_SIZE = 5;

    private String firstName;
    private String lastName;
    private int burgersRequired;

    public FoodQueue(String firstName, String lastName, int burgersRequired) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.burgersRequired = burgersRequired;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public int getBurgersRequired() {
        return burgersRequired;
    }
}
