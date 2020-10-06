package learn.wreckmyhouse.data;

import learn.wreckmyhouse.model.Reservation;

import java.util.List;

public interface ReservationRepository {
    List<Reservation> findAllReservations(String hostEmail) throws DataException;
    Reservation add(Reservation reservation) throws DataException;
    boolean editReservation(Reservation reservation) throws DataException;
    boolean deleteReservation(int id) throws DataException;



//
//    private void writeAll(List<Reservation> reservations);
//    private String serialize(Reservation reservation);
//    private Reservation deserialize(String[] fields);
}
