package learn.wreckmyhouse.data;

import learn.wreckmyhouse.model.Host;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Repository
public class HostFileRepository implements HostRespository {

    private final String filePath;

    public HostFileRepository(@Value("${hostsFilePath}") String filePath) {
        this.filePath = filePath;
    }

    @Override
    public List<Host> findAllHosts() throws DataException {
        ArrayList<Host> result = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            //read header
            reader.readLine();

            for(String line = reader.readLine(); line != null; line = reader.readLine()) {
                String[] fields = line.split(",", -1);
                if(fields.length == 10) {
                    result.add(deserialize(fields));
                }
            }
        } catch (IOException ex) {

        }
        return result;
    }

    @Override
    public Host findByEmail(String hostEmail) throws DataException {
        return findAllHosts().stream().filter(h -> h.getEmail().equalsIgnoreCase(hostEmail)).findFirst().orElse(null);
    }

    private Host deserialize(String[] fields) {
        Host result = new Host();
        result.setHostId(fields[0]);
        result.setLastName(fields[1]);
        result.setEmail(fields[2]);
        result.setPhoneNumber(fields[3]);
        result.setAddress(fields[4]);
        result.setCity(fields[5]);
        result.setState(fields[6]);
        result.setPostal_code(Integer.parseInt(fields[7]));
        result.setStandard_rate(new BigDecimal(fields[8]));
        result.setWeekend_rate(new BigDecimal(fields[9]));
        return result;
    }
}
