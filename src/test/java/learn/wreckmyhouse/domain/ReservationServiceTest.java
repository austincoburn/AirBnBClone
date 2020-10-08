package learn.wreckmyhouse.domain;

import learn.wreckmyhouse.data.*;
import learn.wreckmyhouse.model.Guest;
import learn.wreckmyhouse.model.Host;
import learn.wreckmyhouse.model.Reservation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.xml.crypto.Data;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ReservationServiceTest {


    private Host host = new Host(HostRepositoryDouble.hostId, "Blah", "dskfsdf@gjskd.com", "905-232-2323", "skfdksa ave.", "Chicago", "IL", 23023, new BigDecimal(1), new BigDecimal(2));
    private Guest guest = new Guest(2, "Paul", "Bunyon", "paul@paul.com", "232-232-8989", "WA");
    private Reservation reservation = new Reservation(0, guest, host, LocalDate.parse("2021-01-01"), LocalDate.parse("2021-02-01"), null);

    ReservationService reservationService;
    HostService hostService;
    GuestService guestService;

    @BeforeEach
    void setUp() {
        reservationService = new ReservationService(new ReservationRepositoryDouble(), new HostRepositoryDouble(), new GuestRepositoryDouble());
    }

    @Test
    void shouldFindAllReservations() throws DataException {
        List<Reservation> result = reservationService.findAllReservations(HostRepositoryDouble.hostId);
        assertEquals(1, result.size());
    }

    @Test
    void shouldFindReservationById() throws DataException {
        Reservation result = reservationService.findReservationById(HostRepositoryDouble.hostId, 1);
        assertNotNull(result);
        assertEquals(LocalDate.parse("2020-09-25"), result.getEndDate());
    }

    @Test
    void shouldAddReservationSuccessfully() throws DataException {
        Result<Reservation> result = reservationService.add(reservation);
        assertTrue(result.isSuccess());
    }




}