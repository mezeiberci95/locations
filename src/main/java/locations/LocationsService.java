package locations;

import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class LocationsService {

    private List<Location> locationList = Arrays.asList(new Location(1L, "Location One", 1, 1),
                                                        new Location(2L, "Location Two", 2, 2),
                                                        new Location(3L, "Location Three", 3, 3));

    List<Location> getLocations() {
        return locationList;
    }


}
