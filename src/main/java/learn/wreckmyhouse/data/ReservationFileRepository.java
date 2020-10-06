package learn.wreckmyhouse.data;

import learn.wreckmyhouse.model.Guest;
import learn.wreckmyhouse.model.Reservation;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class ReservationFileRepository implements ReservationRepository {
    @Override
    public List<Reservation> findAllReservations(String hostEmail) throws DataException {
        return null;
    }

    @Override
    public Reservation add(Reservation reservation) throws DataException {
        return null;
    }

    @Override
    public boolean editReservation(Reservation reservation) throws DataException {
        return false;
    }

    @Override
    public boolean deleteReservation(int id) throws DataException {
        return false;
    }

    private Reservation deserialize(String[] fields, String hostId) {
        //id,start_date,end_date,guest_id,total
        Reservation reservation = new Reservation();
        reservation.setId(Integer.parseInt(fields[0]));
        reservation.setStartDate(LocalDate.parse(fields[1]));
        reservation.setEndDate(LocalDate.parse(fields[2]));
        Guest guest = new Guest();
        guest.setGuest_Id(Integer.parseInt(fields[3]));
        reservation.setGuest(guest);
        reservation.setTotalPrice(new BigDecimal(fields[4]));

        return reservation;
    }

}
