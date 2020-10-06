package learn.wreckmyhouse.data;

import learn.wreckmyhouse.model.Guest;

import java.util.ArrayList;
import java.util.List;

public class GuestRepositoryDouble implements GuestRepository {

    private ArrayList<Guest> guests = new ArrayList<>();
    public GuestRepositoryDouble() {
        guests.add(new Guest(1, "Austin", "Coburn", "austin@gmail.com", "(715) 6510725", "CO"));
        guests.add(new Guest(2, "Colter", "Weinke", "colter@gmail.com", "(715) 6310745", "WI"));
        guests.add(new Guest(3, "James", "Churchill", "james@gmail.com", "(715) 4410725", "OR"));
    }

    @Override
    public List<Guest> findAll() throws DataException {
        return new ArrayList<>(guests);
    }

    @Override
    public Guest findByEmail(String email) throws DataException {
        for(Guest guest : guests) {
            if(guest.getEmail().equalsIgnoreCase(email)) {
                return guest;
            }
        }
        return null;
    }
}
