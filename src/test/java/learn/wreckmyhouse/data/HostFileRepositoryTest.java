package learn.wreckmyhouse.data;

import learn.wreckmyhouse.model.Host;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class HostFileRepositoryTest {

    private static final String TEST_PATH = "./data/hosts-test.csv";
    private static final String SEED_PATH = "./data/hosts-seed.csv";

    HostFileRepository repository = new HostFileRepository(TEST_PATH);

    @BeforeEach
    void setUp() throws IOException {
        Path testPath = Paths.get(TEST_PATH);
        Path seedPath = Paths.get(SEED_PATH);
        Files.copy(seedPath, testPath, StandardCopyOption.REPLACE_EXISTING);
    }

    @Test
    void findAllHosts() throws DataException {
       List<Host> result = repository.findAllHosts();
       assertEquals(3, result.size());
    }

    @Test
    void shouldFindByEmail() throws DataException {
        Host result = repository.findByEmail("nwhapplef@1688.com");
        assertNotNull(result);
    }

    @Test
    void shouldNotFindByEmail() throws DataException {
        Host result = repository.findByEmail("sdfsdfsdf");
        assertNull(result);
    }
}