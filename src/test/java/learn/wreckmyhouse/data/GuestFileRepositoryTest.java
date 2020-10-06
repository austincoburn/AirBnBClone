package learn.wreckmyhouse.data;

import learn.wreckmyhouse.model.Guest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GuestFileRepositoryTest {

    static final String SEED_FILE_PATH = "./data/guests-seed.csv";
    static final String TEST_FILE_PATH = "./data/guests-test.csv";

    GuestFileRepository repository = new GuestFileRepository(TEST_FILE_PATH);


    @BeforeEach
    void setUp() throws IOException {
        Path seedPath = Paths.get(SEED_FILE_PATH);
        Path testPath = Paths.get(TEST_FILE_PATH);

        Files.copy(seedPath, testPath, StandardCopyOption.REPLACE_EXISTING);
    }

    @Test
    void findAll() throws DataException {
        List<Guest> actual = repository.findAll();
        assertEquals(3, actual.size());
    }

    @Test
    void shouldFindByEmail() throws DataException {
        Guest result = repository.findByEmail("nrittere@dagondesign.com");
        assertNotNull(result);
    }

    @Test
    void shouldNotFindByInvalidEmail() throws DataException {
        Guest result = repository.findByEmail("austin@gmail.com");
        assertNull(result);
    }

}