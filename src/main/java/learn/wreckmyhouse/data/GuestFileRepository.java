package learn.wreckmyhouse.data;

import learn.wreckmyhouse.model.Guest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class GuestFileRepository implements GuestRepository {

    private final String filePath;

    public GuestFileRepository(@Value("${guestsFilePath}") String filePath) {
        this.filePath = filePath;
    }

    @Override
    public List<Guest> findAll() throws DataException {
        ArrayList<Guest> result = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            //read header
            reader.readLine();

            for(String line = reader.readLine(); line != null; line = reader.readLine()) {
                String[] fields = line.split(",", -1);
                if(fields.length == 6) {
                    result.add(deserialize(fields));
                }
            }

        } catch (IOException ex) {

        }
        return result;
    }

    @Override
    public Guest findByEmail(String email) throws DataException {
        return findAll().stream().filter(g -> g.getEmail().equalsIgnoreCase(email)).findFirst().orElse(null);
    }

    private Guest deserialize(String[] fields) {
        //guest_id,first_name,last_name,email,phone,state
        Guest result = new Guest();
        result.setGuest_Id(Integer.parseInt(fields[0]));
        result.setFirstname(fields[1]);
        result.setLastName(fields[2]);
        result.setEmail(fields[3]);
        result.setPhoneNumber(fields[4]);
        result.setState(fields[5]);
        return result;
    }

}
