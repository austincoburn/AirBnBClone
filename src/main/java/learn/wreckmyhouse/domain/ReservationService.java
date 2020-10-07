package learn.wreckmyhouse.domain;

import learn.wreckmyhouse.data.DataException;
import learn.wreckmyhouse.data.ReservationRepository;
import learn.wreckmyhouse.model.Reservation;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReservationService {

    private final ReservationRepository repository;

    public ReservationService(ReservationRepository repository) {
        this.repository = repository;
    }

    public List<Reservation> findAllReservations(String hostId) throws DataException {
        List<Reservation> reservations = repository.findAllReservations(hostId);
        if(reservations == null || reservations.isEmpty()) {
            return null;
        }
        return reservations;
    }
    /*
    public List<Reservation> findAllReservations(String hostId)
    public Reservation findReservationById(String hostId, int reservationId)
    public Reservation add(Reservation reservation)
    public boolean editReservation(Reservation reservation)
    public boolean deleteReservation(int reservationId, Reservation reservation)
     */


}
