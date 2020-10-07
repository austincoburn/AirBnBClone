package learn.wreckmyhouse.domain;

import learn.wreckmyhouse.data.DataException;
import learn.wreckmyhouse.data.HostRespository;
import learn.wreckmyhouse.model.Host;
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

    public Host findHostByEmail(String hostEmail) throws DataException {
        if(hostEmail == null || hostEmail.isBlank()) {
            return null;
        }
        return respository.findByEmail(hostEmail);
    }

}


