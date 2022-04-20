package locations;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Controller integrációs teszt
 * A Controller elindul, de Tomcat server nem indul mellé.
 */

@WebMvcTest(controllers = LocationsController.class)
public class LocationsControllerWebMvcIT {

    // ki kell mock-olni a service-t
    @MockBean
    LocationsService locationsService;

     // @WebMvcTest miatt tudjuk injektálni
    @Autowired
    MockMvc mockMvc;

    @Test
    void testListLocations() throws Exception {
        // megmondjuk mit a djon visssza service
        when(locationsService.getLocations(any())).thenReturn(List.of(new LocationDto(1L, "Location1", 1, 1),
                new LocationDto(2L, "Location2", 2, 2)
        ));

        mockMvc.perform(get("/api/locations"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name", equalTo("Location1")));

    }
}
