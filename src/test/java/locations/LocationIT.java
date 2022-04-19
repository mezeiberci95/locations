package locations;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class LocationIT {

    @Autowired
    LocationsController locationsController;

    @Test
    void getLocations() {
        String message = locationsController.getLocations();

        assertThat(message).startsWith("[Location(id=1, name=Location One");
    }
}
