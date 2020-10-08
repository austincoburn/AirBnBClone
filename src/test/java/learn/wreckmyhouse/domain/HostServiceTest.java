package learn.wreckmyhouse.domain;

import learn.wreckmyhouse.data.DataException;
import learn.wreckmyhouse.data.HostRepositoryDouble;
import learn.wreckmyhouse.data.HostRespository;
import learn.wreckmyhouse.model.Host;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class HostServiceTest {

    private HostService service;

    @BeforeEach
    void setUp() {
        service = new HostService(new HostRepositoryDouble());
    }

    @Test
    void findAllHosts() throws DataException {
        List<Host> hosts = service.findAllHosts();
        assertEquals(3, hosts.size());
    }

    @Test
    void findHostByEmail() throws DataException {
        Result<Host> result = service.findHostByEmail("ac@gmail.com");
        assertEquals("coburn", result.getPayload().getLastName());
    }

    @Test
    void findHostByEmailShouldShowErrorMessage() throws DataException {
        Result<Host> result = service.findHostByEmail("sdfsdfsdfs");
        assertEquals("[Host email was not found!]", result.getErrorMessages().toString());
    }

    @Test
    void shouldNotFindHostWithNullEmail() throws DataException {
        Result<Host> result = service.findHostByEmail(null);
        assertEquals("[No host was entered!]", result.getErrorMessages().toString());
    }

}