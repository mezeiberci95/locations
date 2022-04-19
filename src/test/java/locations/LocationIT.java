package locations;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class LocationIT {

    @Autowired
    LocationsController locationsController;

    @Test
    void getLocations() {
        List<LocationDto> locationList = locationsController.getLocations();

        assertThat(locationList).isNotEmpty();
    }
}
