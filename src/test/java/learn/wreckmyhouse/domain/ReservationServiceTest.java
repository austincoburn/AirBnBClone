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
    ReservationService reservationService;

    @BeforeEach
    void setUp() {
        reservationService = new ReservationService(new ReservationRepositoryDouble(), new HostRepositoryDouble(), new GuestRepositoryDouble());
    }

    @Test
    void shouldFindAllReservations() throws DataException {
        List<Reservation> result = reservationService.findAllReservations(HostRepositoryDouble.hostId);
        assertEquals(2, result.size());
    }

    @Test
    void shouldFindReservationById() throws DataException {
        Reservation result = reservationService.findReservationById(HostRepositoryDouble.hostId, 1);
        assertNotNull(result);
        assertEquals(LocalDate.parse("2020-10-25"), result.getEndDate());
    }

    @Test
    void shouldAddReservationSuccessfully() throws DataException {
         Host host = new Host(HostRepositoryDouble.hostId, "Blah", "dskfsdf@gjskd.com", "905-232-2323", "skfdksa ave.", "Chicago", "IL", 23023, new BigDecimal(1), new BigDecimal(2));
         Guest guest = new Guest(2, "Paul", "Bunyon", "paul@paul.com", "232-232-8989", "WA");
         Reservation reservation = new Reservation(0, guest, host, LocalDate.parse("2021-01-01"), LocalDate.parse("2021-02-01"), null);
        Result<Reservation> result = reservationService.add(reservation);
        assertTrue(result.isSuccess());
    }

    @Test
    void shouldNotAddReservationWhereStartDateIsInPast() throws DataException {
        Host host = new Host(HostRepositoryDouble.hostId, "Blah", "dskfsdf@gjskd.com", "905-232-2323", "skfdksa ave.", "Chicago", "IL", 23023, new BigDecimal(1), new BigDecimal(2));
        Guest guest = new Guest(2, "Paul", "Bunyon", "paul@paul.com", "232-232-8989", "WA");
        Reservation reservation = new Reservation(0, guest, host, LocalDate.parse("1886-01-01"), LocalDate.parse("2021-02-01"), null);
        Result<Reservation> result = reservationService.add(reservation);
        assertEquals("[Start date must be in the future!]", result.getErrorMessages().toString());
    }

    @Test
    void shouldNotAddReservationThatIsNull() throws DataException {
        Result<Reservation> result = reservationService.add(null);
        assertFalse(result.isSuccess());
    }

    @Test
    void shouldNotAddReservationWhereStartDateIsAfterEndDate() throws DataException {
        Host host = new Host(HostRepositoryDouble.hostId, "Blah", "dskfsdf@gjskd.com", "905-232-2323", "skfdksa ave.", "Chicago", "IL", 23023, new BigDecimal(1), new BigDecimal(2));
        Guest guest = new Guest(2, "Paul", "Bunyon", "paul@paul.com", "232-232-8989", "WA");
        Reservation reservation = new Reservation(0, guest, host, LocalDate.parse("2021-01-01"), LocalDate.parse("2020-12-25"), null);
        Result<Reservation> result = reservationService.add(reservation);
        assertEquals("[Start date must be before end date!]", result.getErrorMessages().toString());
    }

    @Test
    void shouldNotAddReservationWhereDatesAreAlreadyBooked() throws DataException {
        Host host = new Host(HostRepositoryDouble.hostId, "Blah", "dskfsdf@gjskd.com", "905-232-2323", "skfdksa ave.", "Chicago", "IL", 23023, new BigDecimal(1), new BigDecimal(2));
        Guest guest = new Guest(2, "Paul", "Bunyon", "paul@paul.com", "232-232-8989", "WA");
        Reservation reservation = new Reservation(0, guest, host, LocalDate.parse("2020-10-10"), LocalDate.parse("2020-10-21"), null);
        Result<Reservation> result = reservationService.add(reservation);
        assertEquals("[This date is already booked!]", result.getErrorMessages().toString());
    }

    @Test
    void shouldNotUpdateReservationUsingInvalidDates() throws DataException {
        Guest guest = new Guest(1, null, null, null, null, null);
        Host host = new Host("3edda6bc-ab95-49a8-8962-d50b53f84b15", null, null, null, null, null, null, 0, null,null);
        Reservation reservation = new Reservation(1, guest, host, LocalDate.parse("2020-10-26"), LocalDate.parse("2020-10-30"), new BigDecimal(40093.34));

        //Duplicate date
        Result<Reservation> result = reservationService.updateReservation(reservation);
        assertEquals("[This date is already booked!]", result.getErrorMessages().toString());

        //DATE set to a date in the past
        reservation = new Reservation(1, guest, host, LocalDate.parse("1886-09-23"), LocalDate.parse("2020-10-30"), new BigDecimal(40093.34));
        Result<Reservation> result2 = reservationService.updateReservation(reservation);
        assertEquals("[Start date must be in the future!]", result2.getErrorMessages().toString());
    }

    @Test
    void shouldSuccessfullyUpdateReservation() throws DataException {
        Guest guest = new Guest(1, null, null, null, null, null);
        Host host = new Host("3edda6bc-ab95-49a8-8962-d50b53f84b15", null, null, null, null, null, null, 0, new BigDecimal(1),new BigDecimal(2));
        Reservation reservation = new Reservation(1, guest, host, LocalDate.parse("2021-10-26"), LocalDate.parse("2021-10-30"), new BigDecimal(0));
        Result<Reservation> result = reservationService.updateReservation(reservation);
        assertTrue(result.isSuccess());
    }

    @Test
    void shouldNotDeleteReservationWithInvalidId() throws DataException {
        Result<Reservation> result = reservationService.deleteReservation(89, "host@gmail.com");
        assertFalse(result.isSuccess());
    }

    @Test
    void shouldDeleteReservationSuccessfully() throws DataException {
        Result<Reservation> result = reservationService.deleteReservation(2,"host@gmail.com");
        assertTrue(result.isSuccess());
    }

    @Test
    void shouldNotDeleteReservationWithNullValues() throws DataException {
        Result<Reservation> result = reservationService.deleteReservation(0, null);
        assertFalse(result.isSuccess());
    }
}