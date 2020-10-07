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
        Guest guest = new Guest(1, "Austin", "Coburn", "austin@gmail.com", "(715) 6510725", "CO");
        Host host = new Host("sda22adfasdf3434", "coburn", "ac@gmail.com", "(933) 4434332", "2323 Main Dr.", "Plover", "WI", 34893, new BigDecimal(29032.0343432), new BigDecimal(232.0343432));
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
    public boolean deleteReservation(int reservationId, Reservation reservation) throws DataException {
        return true;
    }
}