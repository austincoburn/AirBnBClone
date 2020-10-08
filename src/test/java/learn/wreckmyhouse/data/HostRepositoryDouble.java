package learn.wreckmyhouse.data;

import learn.wreckmyhouse.model.Host;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class HostRepositoryDouble implements HostRespository {
    ArrayList<Host> hosts = new ArrayList<>();
    public final static String hostId = "3edda6bc-ab95-49a8-8962-d50b53f84b15";

    public HostRepositoryDouble() {
        hosts.add(new Host(hostId, "Jameson", "aj@gmail.com", "(904) 4434332", "2323 Drover Dr.", "Plover", "WI", 34893, new BigDecimal(23232.0343432), new BigDecimal(232.0343432)));
        hosts.add(new Host("sdafs3434dfasdf3434", "Jamesfon", "aj@gmaisfsfl.com", "(904) 4434662", "2353 Plover Dr.", "Plover", "WI", 34893, new BigDecimal(232.0343432), new BigDecimal(2900.0343432)));
        hosts.add(new Host("sda22adfasdf3434", "coburn", "ac@gmail.com", "(933) 4434332", "2323 Main Dr.", "Plover", "WI", 34893, new BigDecimal(29032.0343432), new BigDecimal(232.0343432)));
    }
    @Override
    public List<Host> findAllHosts() throws DataException {
        return new ArrayList<>(hosts);
    }

    @Override
    public Host findByEmail(String hostEmail) throws DataException {
        for(Host host : hosts) {
            if(host.getEmail().equalsIgnoreCase(hostEmail)) {
                return host;
            }
        }
        return null;
    }
}
