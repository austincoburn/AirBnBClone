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
                    view.printHeader("View Reservations for Host");
                    viewReservations();
                    break;
                case MAKE_RESERVATION:
                    view.printHeader("Make a Reservation");
                    makeReservation();
                    break;
                case EDIT_RESERVATION:
                    view.printHeader("Edit a Reservation");
                    editReservation();
                    break;
                case DELETE_RESERVATION:
                    view.printHeader("Delete a Reservation");
                    deleteReservation();
                    break;
            }
        } while (option != MenuOption.EXIT);
    }


    private void viewReservations() throws DataException {
        Result<Host> result = hostValidation(view.getHostEmail());
        if(!result.isSuccess()) {
            view.displayResult(result);
            return;
        }
        String hostId = result.getPayload().getHostId();
        List<Reservation> allReservations = reservationService.findAllReservations(hostId);
        view.viewReservations(allReservations, result.getPayload());
    }

    private void makeReservation() throws DataException {
        Result<Guest> guestResult = guestValidation(view.getGuestEmail());
        if(!guestResult.isSuccess()) {
            view.displayResult(guestResult);
            return;
        }
        Result<Host> hostResult = hostValidation(view.getHostEmail());
        if(!hostResult.isSuccess()) {
            view.displayResult(hostResult);
            return;
        }
        String hostId = hostResult.getPayload().getHostId();
        List<Reservation> allReservations = reservationService.findAllReservations(hostId);
        Reservation reservation = view.makeReservation(allReservations, hostResult.getPayload(), guestResult.getPayload());
        if(reservation == null) {
            return;
        }
        Result<Reservation> result = reservationService.add(reservation);
        view.displayResult(result);
    }

    private void editReservation() throws DataException {
        Result<Guest> guestResult = guestValidation(view.getGuestEmail());
        if(!guestResult.isSuccess()) {
            view.displayResult(guestResult);
            return;
        }
        Result<Host> hostResult = hostValidation(view.getHostEmail());
        if(!hostResult.isSuccess()) {
            view.displayResult(hostResult);
            return;
        }
        String hostId = hostResult.getPayload().getHostId();
        List<Reservation> allReservations = reservationService.findAllReservations(hostId);
        List<Reservation> guestReservationsList = view.viewReservationsForGuest(allReservations, hostResult.getPayload(), guestResult.getPayload().getEmail());
        Reservation reservationSelection = view.findReservation(guestReservationsList);
        Reservation updatedReservation = view.updateReservation(reservationSelection);
        if(updatedReservation == null) {
            return;
        }
        Result<Reservation> result = reservationService.updateReservation(updatedReservation);
        view.displayResult(result);
    }

    private void deleteReservation() throws DataException {
        Result<Guest> guestResult = guestValidation(view.getGuestEmail());
        if(!guestResult.isSuccess()) {
            view.displayResult(guestResult);
            return;
        }
        Result<Host> hostResult = hostValidation(view.getHostEmail());
        if(!hostResult.isSuccess()) {
            view.displayResult(hostResult);
            return;
        }
        List<Reservation> allReservations = reservationService.findAllReservations(hostResult.getPayload().getHostId());
        Reservation reservation = view.deleteReservation(allReservations, hostResult.getPayload(), guestResult.getPayload().getEmail());
        Result<Reservation> result = reservationService.deleteReservation(reservation.getId(), hostResult.getPayload().getHostId());
        view.displayResult(result);
    }


    private Result hostValidation(String email) throws DataException {
        Result<Host> result = hostService.findHostByEmail(email);
        if(!result.isSuccess()) {
            return result;
        }
        return result;
    }

    private Result guestValidation(String email) throws DataException {
        Result<Guest> result = guestService.findGuestByEmail(email);
        if(!result.isSuccess()) {
            return result;
        }
        return result;
    }
}
