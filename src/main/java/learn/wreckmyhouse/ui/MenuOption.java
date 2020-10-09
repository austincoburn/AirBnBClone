package learn.wreckmyhouse.ui;

public enum MenuOption {
    EXIT("Exit"),
    VIEW_RESERVATIONS("View Reservations for Host"),
    MAKE_RESERVATION("Make a Reservation"),
    EDIT_RESERVATION("Edit a Reservation"),
    DELETE_RESERVATION("Cancel a Reservation");

    private final String title;

    MenuOption(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
