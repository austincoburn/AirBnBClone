package learn.wreckmyhouse.data;

import learn.wreckmyhouse.model.Guest;
import learn.wreckmyhouse.model.Host;
import learn.wreckmyhouse.model.Reservation;
import org.springframework.beans.factory.annotation.Value;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ReservationFileRepository implements ReservationRepository {

    private final String directory;

    public ReservationFileRepository(@Value("${reservationFileDirectory}") String directory) {
        this.directory = directory;
    }

    @Override
    public List<Reservation> findAllReservations(String hostId) throws DataException {
        ArrayList<Reservation> result = new ArrayList<>();

        try(BufferedReader reader = new BufferedReader(new FileReader(getFilePath(hostId)))) {
            //read header
            reader.readLine();

            for(String line = reader.readLine(); line != null; line = reader.readLine()) {
                String[] fields = line.split(",", -1);
                result.add(deserialize(fields, hostId));
            }
        } catch(IOException ex) {
        }
        return result;
    }

    @Override
    public Reservation add(Reservation reservation) throws DataException {

        return null;
    }

    @Override
    public boolean editReservation(Reservation reservation) throws DataException {
        return false;
    }

    @Override
    public boolean deleteReservation(int id) throws DataException {
        return false;
    }

    private String getFilePath(String hostId) {
        return Paths.get(directory, hostId + ".csv").toString();
    }

    private Reservation deserialize(String[] fields, String hostId) {
        //id,start_date,end_date,guest_id,total
        Reservation reservation = new Reservation();
        Host host = new Host();
        host.setHostId(hostId);
        reservation.setHost(host);
        reservation.setId(Integer.parseInt(fields[0]));
        reservation.setStartDate(LocalDate.parse(fields[1]));
        reservation.setEndDate(LocalDate.parse(fields[2]));
        Guest guest = new Guest();
        guest.setGuest_Id(Integer.parseInt(fields[3]));
        reservation.setGuest(guest);
        reservation.setTotalPrice(new BigDecimal(fields[4]));
        return reservation;
    }

}
