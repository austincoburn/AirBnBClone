package learn.wreckmyhouse.data;

import learn.wreckmyhouse.model.Guest;
import learn.wreckmyhouse.model.Host;
import learn.wreckmyhouse.model.Reservation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.xml.crypto.Data;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ReservationFileRepositoryTest {

    private final String TEST_FILE_PATH = "./data/reservation-data-test/3edda6bc-ab95-49a8-8962-d50b53f84b15.csv";
    private final String SEED_FILE_PATH = "./data/reservation-seed-3edda6bc-ab95-49a8-8962-d50b53f84b15.csv";
    private final String TEST_DIR_PATH = "./data/reservation-data-test";

    String hostId = "3edda6bc-ab95-49a8-8962-d50b53f84b15";

    ReservationFileRepository repository = new ReservationFileRepository(TEST_DIR_PATH);


    @BeforeEach
    void setUp() throws IOException {
        Path testPath = Paths.get(TEST_FILE_PATH);
        Path seedPath = Paths.get(SEED_FILE_PATH);

        Files.copy(seedPath, testPath, StandardCopyOption.REPLACE_EXISTING);
    }

    @Test
    void findAllReservations() throws DataException {
        List<Reservation> result = repository.findAllReservations(hostId);
        assertEquals(3, result.size());
    }

    @Test
    void shouldAddReservation() throws DataException {
        Host host = new Host(hostId, "coburn", "ac@gmail.com", "(933) 4434332", "2323 Main Dr.", "Plover", "WI", 34893, new BigDecimal(1), new BigDecimal(2));
        Guest guest = new Guest(3, "James", "Churchill", "james@gmail.com", "(715) 4410725", "OR");

        Reservation reservation = new Reservation();
        reservation.setGuest(guest);
        reservation.setHost(host);
        reservation.setStartDate(LocalDate.parse("2019-09-23"));
        reservation.setEndDate(LocalDate.parse("2020-10-23"));
        reservation.setTotalPrice(reservation.calculateTotalPrice());

        Reservation result = repository.add(reservation);
        assertEquals( 4, result.getId());

    }

    @Test
    void shouldNotAddNullReservation() throws DataException {
        Reservation result = repository.add(null);
        assertNull(result);
    }


}