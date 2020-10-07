package learn.wreckmyhouse.data;

import learn.wreckmyhouse.model.Guest;
import learn.wreckmyhouse.model.Host;
import learn.wreckmyhouse.model.Reservation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.math.BigDecimal;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ReservationFileRepository implements ReservationRepository {

    private final String directory;
    private static final String HEADER = "id,start_date,end_date,guest_id,total";

    public ReservationFileRepository(@Value("${reservationFileDirectory}") String directory) {
        this.directory = directory;
    }

    @Override
    public List<Reservation> findAllReservations(String hostId) throws DataException {
        ArrayList<Reservation> result = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(getFilePath(hostId)))) {
            //read header
            reader.readLine();

            for(String line = reader.readLine(); line != null; line = reader.readLine()) {
                String[] fields = line.split(",", -1);
                if(fields.length == 5) {
                    result.add(deserialize(fields, hostId));
                }
            }
        } catch(IOException ex) {
        }
        return result;
    }

    @Override
    public Reservation add(Reservation reservation) throws DataException {
        if(reservation == null) {
            return null;
        }
       List<Reservation> reservations = findAllReservations(reservation.getHost().getHostId());
       int nextId = reservations.stream().mapToInt(Reservation::getId).max().orElse(0) + 1;
       reservation.setId(nextId);
       reservations.add(reservation);
       writeAll(reservations, reservation.getHost().getHostId());
       return reservation;
    }

    @Override
    public boolean editReservation(Reservation reservation) throws DataException {
        List<Reservation> all = findAllReservations(reservation.getHost().getHostId());
        for(int i = 0; i < all.size(); i++) {
            if(all.get(i).getId() == reservation.getId()) {
                all.set(i, reservation);
                writeAll(all, reservation.getHost().getHostId());
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean deleteReservation(int reservationId, String hostId) throws DataException {
        List<Reservation> all = findAllReservations(hostId);
        for(int i = 0; i < all.size(); i++) {
            if(all.get(i).getId() == reservationId) {
                all.remove(i);
                writeAll(all, hostId);
                return true;
            }
        }
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

    private void writeAll(List<Reservation> reservations, String hostId) throws DataException {
        try(PrintWriter writer = new PrintWriter(getFilePath(hostId))) {
            writer.println(HEADER);

            for(Reservation reservation : reservations) {
                writer.println(serialize(reservation));
            }
        } catch (FileNotFoundException ex) {
            throw new DataException(ex);
        }
    }

    private String serialize(Reservation reservation) {
        return String.format("%s,%s,%s,%s,%.2f", reservation.getId(),
                reservation.getStartDate(), reservation.getEndDate(),
                reservation.getGuest().getGuest_Id(), reservation.getTotalPrice());
    }

}
