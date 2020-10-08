package learn.wreckmyhouse.domain;

import learn.wreckmyhouse.data.DataException;
import learn.wreckmyhouse.data.GuestFileRepository;
import learn.wreckmyhouse.data.GuestRepository;
import learn.wreckmyhouse.model.Guest;
import learn.wreckmyhouse.model.Reservation;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GuestService {
    private final GuestRepository repository;

    public GuestService(GuestRepository repository) {
        this.repository = repository;
    }

    public List<Guest> findAllGuests() throws DataException {
        List<Guest> all = repository.findAll();
        if(all.isEmpty() || all.size() == 0) {
            return null;
        }
        return all;
    }

    public Result<Guest> findGuestByEmail(String guestEmail) throws DataException {
        Result<Guest> result = new Result<>();
        if(guestEmail == null || guestEmail.trim().length() == 0) {
            result.addErrorMessage("No guest email was entered!");
            return result;
        }

        if(validateGuestEmail(guestEmail) == null) {
            result.addErrorMessage("This guest email does not exist!");
            return result;
        }

        Guest guest = repository.findByEmail(guestEmail);
        result.setPayload(guest);
        return result;
    }

    public Guest validateGuestEmail(String guestEmail) throws DataException {
        List<Guest> guests = findAllGuests();
        for(Guest guest : guests) {
            if(guest.getEmail().equalsIgnoreCase(guestEmail)) {
                return guest;
            }
        }
        return null;
    }


}
