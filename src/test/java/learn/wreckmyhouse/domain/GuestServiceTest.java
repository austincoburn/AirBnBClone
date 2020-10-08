package learn.wreckmyhouse.domain;

import learn.wreckmyhouse.data.DataException;
import learn.wreckmyhouse.data.GuestRepositoryDouble;
import learn.wreckmyhouse.model.Guest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.xml.crypto.Data;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GuestServiceTest {
    GuestService service;


    @BeforeEach
    void setUp() {
        service = new GuestService(new GuestRepositoryDouble());
    }

    @Test
    void shouldFindAllGuests() throws DataException {
       List<Guest> guests =  service.findAllGuests();
       assertEquals(3, guests.size());
    }

    @Test
    void shouldFindGuestByEmail() throws DataException {
        Result<Guest> result = service.findGuestByEmail("colter@gmail.com");
        assertEquals("Colter", result.getPayload().getFirstname());
    }

    @Test
    void shouldNotFindGuestByInvalidEmail() throws DataException {
        Result<Guest> result = service.findGuestByEmail("sdsdfs");
        assertEquals("[This guest email does not exist!]", result.getErrorMessages().toString());
    }

    @Test
    void shouldNotFindGuestWithNullEmail() throws DataException {
        Result<Guest> result = service.findGuestByEmail(null);
        assertEquals("[No guest email was entered!]", result.getErrorMessages().toString());
    }
}