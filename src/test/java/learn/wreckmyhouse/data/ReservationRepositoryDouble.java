package learn.wreckmyhouse.data;

import learn.wreckmyhouse.model.Guest;
import learn.wreckmyhouse.model.Host;
import learn.wreckmyhouse.model.Reservation;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ReservationRepositoryDouble implements ReservationRepository {
    ArrayList<Reservation> reservations = new ArrayList<>();

    public ReservationRepositoryDouble() {
        Guest guest = new Guest(1, null, null, null, null, null);
        Host host = new Host("3edda6bc-ab95-49a8-8962-d50b53f84b15", null, null, null, null, null, null, 0, null,null);
        reservations.add(new Reservation(1, guest, host, LocalDate.parse("2020-09-19"), LocalDate.parse("2020-09-25"), new BigDecimal(40093.34)));
    }

    @Override
    public List<Reservation> findAllReservations(String hostId) throws DataException {
        return reservations.stream().filter(r -> r.getHost().getHostId().equalsIgnoreCase(hostId)).collect(Collectors.toList());
    }

    @Override
    public Reservation findReservationById(String hostId, int reservationId) throws DataException {
        return reservations.stream().filter(a -> a.getId() == reservationId).findFirst().orElse(null);
    }

    @Override
    public Reservation add(Reservation reservation) throws DataException {
        reservations.add(reservation);
        return reservation;
    }

    @Override
    public boolean editReservation(Reservation reservation) throws DataException {
        return true;
    }

    @Override
    public boolean deleteReservation(int reservationId, String hostId) throws DataException {
        return true;
    }
}