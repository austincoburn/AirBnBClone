package learn.wreckmyhouse.data;

import learn.wreckmyhouse.model.Reservation;

import java.util.List;

public interface ReservationRepository {
    List<Reservation> findAllReservations(String hostId) throws DataException;
    public Reservation findReservationById(String hostId, int reservationId) throws DataException;
    Reservation add(Reservation reservation) throws DataException;
    boolean editReservation(Reservation reservation) throws DataException;
    boolean deleteReservation(int reservationId, Reservation reservation) throws DataException;



//
//    private void writeAll(List<Reservation> reservations);
//    private String serialize(Reservation reservation);
//    private Reservation deserialize(String[] fields);
}
