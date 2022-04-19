package locations;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
public class LocationsService {

    private ModelMapper modelMapper;

    private List<Location> locationList = Collections.synchronizedList(new ArrayList<>(List.of(new Location(1L, "Location One", 1, 1),
                                                        new Location(2L, "Location Two", 2, 2),
                                                        new Location(3L, "Location Three", 3, 3))));

    public LocationsService(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    List<LocationDto> getLocations() {
        // ez mondja meg, hogy milyen típusú listává konvertálja a modelMapper az átadott location listát
        Type targetListType = new TypeToken<List<LocationDto>>(){}.getType();
        return modelMapper.map(locationList, targetListType);
    }


}
