package learn.wreckmyhouse.domain;

import learn.wreckmyhouse.data.DataException;
import learn.wreckmyhouse.data.HostRespository;
import learn.wreckmyhouse.model.Guest;
import learn.wreckmyhouse.model.Host;
import learn.wreckmyhouse.model.Reservation;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HostService {
    private final HostRespository respository;

    public HostService(HostRespository respository) {
        this.respository = respository;
    }

    public List<Host> findAllHosts() throws DataException {
        List<Host> hosts = respository.findAllHosts();
        if(hosts.isEmpty() || hosts == null) {
            return null;
        }
        return hosts;
    }

    public Result<Host> findHostByEmail(String hostEmail) throws DataException {
        Result<Host> result = new Result<>();
        if(hostEmail == null || hostEmail.isBlank()) {
            result.addErrorMessage("No host was entered!");
            return result;
        }

        if(validateHostEmail(hostEmail) == null) {
            result.addErrorMessage("Host email was not found!");
            return result;
        }

        Host host = respository.findByEmail(hostEmail);
        result.setPayload(host);
        return result;
    }

    private Host validateHostEmail(String hostEmail) throws DataException {
        List<Host> hosts = findAllHosts();
        for(Host host : hosts) {
            if(host.getEmail().equalsIgnoreCase(hostEmail)) {
                return host;
            }
        }
        return null;
    }


}


