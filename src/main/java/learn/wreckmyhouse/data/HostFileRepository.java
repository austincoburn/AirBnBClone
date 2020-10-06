package learn.wreckmyhouse.data;

import learn.wreckmyhouse.model.Host;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class HostFileRepository implements HostRespository {

    private final String filePath;

    public HostFileRepository(@Value("${hostsFilePath}") String filePath) {
        this.filePath = filePath;
    }


    @Override
    public List<Host> findAllHosts() throws DataException {
        return null;
    }

    @Override
    public List<Host> findByEmail(String hostEmail) throws DataException {
        return null;
    }
}
