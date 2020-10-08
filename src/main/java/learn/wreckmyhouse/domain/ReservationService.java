package learn.wreckmyhouse.domain;

import learn.wreckmyhouse.data.DataException;
import learn.wreckmyhouse.data.GuestRepository;
import learn.wreckmyhouse.data.HostRespository;
import learn.wreckmyhouse.data.ReservationRepository;
import learn.wreckmyhouse.model.Guest;
import learn.wreckmyhouse.model.Host;
import learn.wreckmyhouse.model.Reservation;
import org.springframework.stereotype.Service;

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

//    id,start_date,end_date,guest_id,total
    //1,2021-02-02,2021-02-09,302,3240

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

    public Reservation findReservationById(String hostId, int reservationId) {
//
//        if()
//        return null;
        return null;
    }

    /*
    public List<Reservation> findAllReservations(String hostId)
    public Reservation findReservationById(String hostId, int reservationId)
    public Reservation add(Reservation reservation)
    public boolean editReservation(Reservation reservation)
    public boolean deleteReservation(int reservationId, Reservation reservation)
     */


}
