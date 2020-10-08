package learn.wreckmyhouse.domain;

import learn.wreckmyhouse.data.*;
import learn.wreckmyhouse.model.Reservation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ReservationServiceTest {

//    private final ReservationRepository reservationRepository;
//    private final HostRespository hostRespository;
//    private final GuestRepository guestRepository;



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
}