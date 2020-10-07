package learn.wreckmyhouse.domain;

import learn.wreckmyhouse.data.DataException;
import learn.wreckmyhouse.data.GuestFileRepository;
import learn.wreckmyhouse.data.GuestRepository;
import learn.wreckmyhouse.model.Guest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GuestService {
    private final GuestRepository repository;

    public GuestService(GuestRepository repository) {
        this.repository = repository;
    }

    public List<Guest> findAllGuests() throws DataException {
        Result<Guest> result = new Result<>();
        List<Guest> all = repository.findAll();
        if(all.isEmpty() || all.size() == 0) {
            return null;
        }
        return all;
    }

    public Guest findGuestByEmail(String guestEmail) throws DataException {
//        Result<Guest> result = new Result<>();
        if(guestEmail == null || guestEmail.trim().length() == 0) {
            return null;
        }
//        result.addErrorMessage("The email you entered does not exist");
        return repository.findByEmail(guestEmail);
    }


}
