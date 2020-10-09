package learn.wreckmyhouse.ui;

import learn.wreckmyhouse.data.DataException;
import learn.wreckmyhouse.domain.GuestService;
import learn.wreckmyhouse.domain.HostService;
import learn.wreckmyhouse.domain.ReservationService;
import learn.wreckmyhouse.domain.Result;
import learn.wreckmyhouse.model.Guest;
import learn.wreckmyhouse.model.Host;
import learn.wreckmyhouse.model.Reservation;
import org.springframework.stereotype.Component;

import java.util.List;

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
            switch (option) {
                case EXIT:
                    view.printHeader("Goodbye!");
                    break;
                case VIEW_RESERVATIONS:
                    viewReservations();
                    break;
                case MAKE_RESERVATION:
                    makeReservation();
                    break;
                case EDIT_RESERVATION:
                    view.printHeader("Edit a Reservation");
                    editReservation();
                    break;
                case DELETE_RESERVATION:
                    //Cancel a reservation
                    break;
            }
        } while (option != MenuOption.EXIT);
    }


    private void viewReservations() throws DataException {
        String hostEmail = view.getHostEmail();
        Result<Host> result = hostService.findHostByEmail(hostEmail);
        if(result.isSuccess()) {
            String hostId = result.getPayload().getHostId();
            List<Reservation> allReservations = reservationService.findAllReservations(hostId);
            view.viewReservations(allReservations, result.getPayload());
        } else {
            view.displayResult(result);
        }
    }

    private void makeReservation() throws DataException {
        String guestEmail = view.getGuestEmail();
        Result<Guest> guestResult = guestService.findGuestByEmail(guestEmail);
        if(!guestResult.isSuccess()) {
            view.displayResult(guestResult);
            return;
        }
        String hostEmail = view.getHostEmail();
        Result<Host> hostResult = hostService.findHostByEmail(hostEmail);
        if(!hostResult.isSuccess()) {
            view.displayResult(hostResult);
            return;
        }
        String hostId = hostResult.getPayload().getHostId();
        List<Reservation> allReservations = reservationService.findAllReservations(hostId);
        Reservation reservation = view.makeReservation(allReservations, hostResult.getPayload(), guestResult.getPayload());
        Result<Reservation> result = reservationService.add(reservation);
        view.displayResult(result);
    }

    private void editReservation() throws DataException {
        String guestEmail = view.getGuestEmail();
        Result<Guest> guestResult = guestService.findGuestByEmail(guestEmail);
        if(!guestResult.isSuccess()) {
            view.displayResult(guestResult);
            return;
        }
        String hostEmail = view.getHostEmail();
        Result<Host> hostResult = hostService.findHostByEmail(hostEmail);
        if(!hostResult.isSuccess()) {
            view.displayResult(hostResult);
            return;
        }
        String hostId = hostResult.getPayload().getHostId();
        List<Reservation> allReservations = reservationService.findAllReservations(hostId);
        List<Reservation> guestReservationsList = view.viewReservationsForGuest(allReservations, hostResult.getPayload(), guestEmail);
        Reservation reservationSelection = view.findReservation(guestReservationsList);
        Reservation updatedReservation = view.updateReservation(reservationSelection);
        Result<Reservation> result = reservationService.updateReservation(updatedReservation);
        view.displayResult(result);
    }

    private void deleteReservation() throws DataException {

    }
}
