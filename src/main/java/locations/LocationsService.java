package locations;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.lang.reflect.Type;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class LocationsService {

    private static final Logger LOGGER = LoggerFactory.getLogger(LocationsService.class);

    //private AtomicLong idGenerator = new AtomicLong();

    private ModelMapper modelMapper;

    private LocationRepository repository;

    /*private List<Location> locationList = Collections.synchronizedList(new ArrayList<>(List.of(new Location(idGenerator.incrementAndGet(), "LocationOne", 1, 1),
                                                        new Location(idGenerator.incrementAndGet(), "Location Two", 2, 2),
                                                        new Location(idGenerator.incrementAndGet(), "Location Three", 3, 3))));
    */
    List<LocationDto> getLocations(Optional<String> name) {
        // ez mondja meg, hogy milyen típusú listává konvertálja a modelMapper az átadott location listát
        Type targetListType = new TypeToken<List<LocationDto>>(){}.getType();
        List<Location> filtered = repository.findAll().stream()
                .filter(e -> name.isEmpty() || e.getName().equalsIgnoreCase(name.get()))
                .collect(Collectors.toList());
        return modelMapper.map(filtered, targetListType);
    }


    public LocationDto findLocationById(long id) {
        return modelMapper.map(repository.findById(id).orElseThrow(() -> new LocationNotFoundException(id)),
                LocationDto.class);
    }

    public LocationDto createLocation(CreateLocationCommand command) {
        Location location = new Location(command.getName(), command.getLat(), command.getLon());
        repository.save(location);

        LOGGER.info("Location has been created");
        LOGGER.debug("Location has been created with name {}", command.getName());
        return modelMapper.map(location, LocationDto.class);
    }
    @Transactional
    public LocationDto updateLocation(long id, UpdateLocationCommand command) {
        Location location = repository.findById(id).orElseThrow(() -> new LocationNotFoundException(id));
        location.setName(command.getName());
        location.setLat(command.getLat());
        location.setLon(command.getLon());
        return modelMapper.map(location, LocationDto.class);
    }

    public void deleteLocation(long id) {
        repository.deleteById(id);
    }

    // ahhoz kell, hogy a tesztek futtatásakor ne legyen Location
    public void deleteLocations(){
        repository.deleteAll();;
    }

}
