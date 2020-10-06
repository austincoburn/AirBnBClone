package learn.wreckmyhouse.data;

import learn.wreckmyhouse.model.Guest;

import java.util.List;

public interface GuestRepository {
    List<Guest> findAll() throws DataException;

    Guest findByEmail(String email) throws DataException;
}
