package learn.wreckmyhouse.domain;

import learn.wreckmyhouse.data.*;
import learn.wreckmyhouse.model.Reservation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ReservationServiceTest {

//    private final ReservationRepository reservationRepository;
//    private final HostRespository hostRespository;
//    private final GuestRepository guestRepository;



    ReservationService reservationService;
    HostService hostService;
    GuestService guestService;
    String hostId = "3edda6bc-ab95-49a8-8962-d50b53f84b15";

    @BeforeEach
    void setUp() {
        reservationService = new ReservationService(new ReservationRepositoryDouble(), new HostRepositoryDouble(), new GuestRepositoryDouble());
    }

    @Test
    void shouldFindAllReservations() throws DataException {
        List<Reservation> result = reservationService.findAllReservations(hostId);
        assertEquals(1, result.size());
    }
}