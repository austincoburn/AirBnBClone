package learn.wreckmyhouse.domain;

import learn.wreckmyhouse.data.DataException;
import learn.wreckmyhouse.data.GuestRepository;
import learn.wreckmyhouse.data.HostRespository;
import learn.wreckmyhouse.data.ReservationRepository;
import learn.wreckmyhouse.model.Guest;
import learn.wreckmyhouse.model.Host;
import learn.wreckmyhouse.model.Reservation;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;

import java.rmi.registry.LocateRegistry;
import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final HostRespository hostRespository;
    private final GuestRepository guestRepository;

    public ReservationService(ReservationRepository reservationRepository, HostRespository hostRespository, GuestRepository guestRepository) {
        this.reservationRepository = reservationRepository;
        this.hostRespository = hostRespository;
        this.guestRepository = guestRepository;
    }

    public List<Reservation> findAllReservations(String hostId) throws DataException {
        List<Reservation> result = reservationRepository.findAllReservations(hostId);
       Map<Integer, Guest> guestMap = guestRepository.findAll().stream().collect(Collectors.toMap(g -> g.getGuest_Id(), g -> g));
       Map<String, Host> hostMap = hostRespository.findAllHosts().stream().collect(Collectors.toMap(h -> h.getHostId(), h -> h));


       for(Reservation reservation : result) {
           reservation.setHost(hostMap.get(reservation.getHost().getHostId()));
           reservation.setGuest(guestMap.get(reservation.getGuest().getGuest_Id()));
       }
        return result;
    }

    public Reservation findReservationById(String hostId, int reservationId) throws DataException {
        return reservationRepository.findReservationById(hostId, reservationId);
    }

    public Result<Reservation> add(Reservation reservation) throws DataException {
       Result<Reservation> result = validateNulls(reservation);
       if(!result.isSuccess()) {
          return result;
       }

       result = validateDates(reservation, result);
       if(!result.isSuccess()) {
           return result;
       }

       Reservation r = reservationRepository.add(reservation);
       result.setPayload(r);

        return result;
    }

    public Result<Reservation> updateReservation(Reservation reservation) throws DataException {
       Result<Reservation> result = validateDates(reservation);
       if(!result.isSuccess()) {
           return result;
       }
       boolean success = reservationRepository.editReservation(reservation);
       if(!success) {
           result.addErrorMessage("Could not update Reservation Id: " + reservation.getId());
       }

       result.setPayload(reservation);
       return result;
    }

    public Result<Reservation> deleteReservation(int reservationId, String hostEmail) throws DataException {

        Result<Reservation> result = new Result<>();
        Reservation reservation = findReservationById(hostEmail, reservationId);
        if(reservation == null) {
            result.addErrorMessage("The reservation ID: " + reservationId + " you entered was invalid");
            return result;
        }
        boolean success = reservationRepository.deleteReservation(reservationId, hostEmail);
        if(!success) {
            result.addErrorMessage("Deletion was unsuccessful!");
            return result;
        }
        result.setPayload(reservation);
        return result;
    }

    private Result<Reservation> validateNulls(Reservation reservation) {
        Result<Reservation> result = new Result<>();

        if(reservation == null) {
            result.addErrorMessage("Reservation cannot be null");
            return result;
        }

        if(reservation.getHost().getEmail() == null || reservation.getHost().getEmail().isBlank()) {
            result.addErrorMessage("Must enter a valid host email");
            return result;
        }

        if(reservation.getGuest().getEmail() == null || reservation.getGuest().getEmail().isBlank()) {
            result.addErrorMessage("Must enter a valid guest email");
            return result;
        }

        if(reservation.getStartDate() == null) {
            result.addErrorMessage("Must enter a start date");
            return result;
        }

        if(reservation.getEndDate() == null) {
            result.addErrorMessage("Must enter a end date");
            return result;
        }
        return result;
    }

    private Result<Reservation> validateDates(Reservation reservation, Result<Reservation> result) throws DataException {

        if(reservation.getStartDate().isBefore(LocalDate.now())) {
            result.addErrorMessage("Start date must be in the future!");
            return result;
        }

        if(reservation.getStartDate().isAfter(reservation.getEndDate())) {
            result.addErrorMessage("Start date must be before end date!");
            return result;
        }

        List<Reservation> all = findAllReservations(reservation.getHost().getHostId());
        for(Reservation line : all) {
            for(LocalDate date = line.getStartDate(); date.isBefore(line.getEndDate()); date = date.plusDays(1)) {
                for(LocalDate date1 = reservation.getStartDate(); date1.isBefore(reservation.getEndDate()); date1 = date1.plusDays(1)) {
                    if(date1.isEqual(date)) {
                        result.addErrorMessage("This date is already booked!");
                        return result;
                    }
                }
            }
        }
        return result;
    }

    private Result<Reservation> validateDates(Reservation reservation) throws DataException {
        Result<Reservation> result = new Result<>();
        if(reservation.getStartDate().isBefore(LocalDate.now())) {
            result.addErrorMessage("Start date must be in the future!");
            return result;
        }

        if(reservation.getStartDate().isAfter(reservation.getEndDate())) {
            result.addErrorMessage("Start date must be before end date!");
            return result;
        }

        List<Reservation> all = findAllReservations(reservation.getHost().getHostId());
        for(Reservation line : all) {
            for(LocalDate date = line.getStartDate(); date.isBefore(line.getEndDate()); date = date.plusDays(1)) {
                for(LocalDate date1 = reservation.getStartDate(); date1.isBefore(reservation.getEndDate()); date1 = date1.plusDays(1)) {
                    if(date1.isEqual(date)) {
                        result.addErrorMessage("This date is already booked!");
                        return result;
                    }
                }
            }
        }
        return result;
    }



}
