package learn.wreckmyhouse.ui;

import learn.wreckmyhouse.data.DataException;
import learn.wreckmyhouse.domain.GuestService;
import learn.wreckmyhouse.domain.HostService;
import learn.wreckmyhouse.domain.ReservationService;
import org.springframework.stereotype.Component;

@Component
public class Controller {
    private final GuestService guestService;
    private final HostService hostService;
    private final ReservationService reservationService;
    private final View view;

    public Controller(GuestService guestService, HostService hostService, ReservationService reservationService, View view) {
        this.guestService = guestService;
        this.hostService = hostService;
        this.reservationService = reservationService;
        this.view = view;
    }

    public void run() {
        view.printHeader("Main Menu");
        try {
            runMenu();
        } catch (DataException ex) {
            view.displayException(ex);
        }
    }

    public void runMenu() throws DataException {
        MenuOption option;
        do{
            option = view.displayMenuAndSelect();
            System.out.println(option.getTitle());
            switch (option) {
                case EXIT:
                    view.printHeader("Goodbye!");
                    break;
                case VIEW_RESERVATIONS:
                    //viewwwww
                    break;
                case MAKE_RESERVATION:
                    //make a reservation method
                    break;
                case EDIT_RESERVATION:
                    //Edit a reservation
                    break;
                case DELETE_RESERVATION:
                    //Cancel a reservation
                    break;
            }
        } while (option != MenuOption.EXIT);
    }
}
