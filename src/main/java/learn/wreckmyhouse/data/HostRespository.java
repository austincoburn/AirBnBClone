package learn.wreckmyhouse.data;

import learn.wreckmyhouse.model.Host;

import java.util.List;

public interface HostRespository {

    List<Host> findAllHosts() throws DataException;
    Host findByEmail(String hostEmail) throws DataException;
}
