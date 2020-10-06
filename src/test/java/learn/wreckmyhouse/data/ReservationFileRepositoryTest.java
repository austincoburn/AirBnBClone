package learn.wreckmyhouse.data;

import learn.wreckmyhouse.model.Reservation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
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
}