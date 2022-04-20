package locations;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Service
public class LocationsService {

    private AtomicLong idGenerator = new AtomicLong();

    private ModelMapper modelMapper;

    private List<Location> locationList = Collections.synchronizedList(new ArrayList<>(List.of(new Location(idGenerator.incrementAndGet(), "LocationOne", 1, 1),
                                                        new Location(idGenerator.incrementAndGet(), "Location Two", 2, 2),
                                                        new Location(idGenerator.incrementAndGet(), "Location Three", 3, 3))));

    public LocationsService(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    List<LocationDto> getLocations(Optional<String> name) {
        // ez mondja meg, hogy milyen típusú listává konvertálja a modelMapper az átadott location listát
        Type targetListType = new TypeToken<List<LocationDto>>(){}.getType();
        List<Location> filtered = locationList.stream()
                .filter(e -> name.isEmpty() || e.getName().equalsIgnoreCase(name.get()))
                .collect(Collectors.toList());
        return modelMapper.map(filtered, targetListType);
    }


    public LocationDto findLocationById(long id) {
        return modelMapper.map(locationList.stream()
                        .filter(e -> e.getId() == id).findAny()
                        .orElseThrow(() -> new LocationNotFoundException(id)),
                LocationDto.class);
    }

    public LocationDto createLocation(CreateLocationCommand command) {
        Location location = new Location(idGenerator.incrementAndGet(), command.getName(), command.getLat(), command.getLon());
        locationList.add(location);

        return modelMapper.map(location, LocationDto.class);
    }

    public LocationDto updateLocation(long id, UpdateLocationCommand command) {
        Location location = locationList.stream()
                .filter(e -> e.getId() == id)
                .findFirst().orElseThrow(() -> new LocationNotFoundException(id));
        location.setName(command.getName());
        location.setLat(command.getLat());
        location.setLon(command.getLon());
        return modelMapper.map(location, LocationDto.class);
    }

    public void deleteLocation(long id) {
        Location location = locationList.stream()
                .filter(e -> e.getId() == id)
                .findFirst().orElseThrow(() -> new LocationNotFoundException(id));
        locationList.remove(location);
    }

}
