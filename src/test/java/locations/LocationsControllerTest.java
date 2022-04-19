package locations;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class LocationsControllerTest {

    @Mock
    LocationsService locationsService;

    @InjectMocks
    LocationsController locationsController;

    @Test
    void getLocations() {
        when(locationsService.getLocations()).thenReturn(Arrays.asList(new Location(1L, "Location One", 1, 1),
                new Location(2L, "Location Two", 2, 2)));

        String message = locationsController.getLocations();

        assertThat(message).startsWith("[Location(id=1, name=Location One");
    }
}
