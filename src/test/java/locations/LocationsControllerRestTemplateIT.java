package locations;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Controller RestTemplate integrációs teszt.
 */

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class LocationsControllerRestTemplateIT {


    @Autowired
    TestRestTemplate template; // ezzel lehet kéréseket kiadni

    @Autowired
    LocationsService locationsService;

    //@Test
    @RepeatedTest(2)
    void testListLocations() {
        locationsService.deleteLocations();
        // ezzel létrejön egy Location
        LocationDto locationDto =
                template.postForObject("/api/locations", new CreateLocationCommand("Location1", 1, 1), LocationDto.class);

        assertEquals("Location1", locationDto.getName());
        assertEquals(1, locationDto.getLat());
        assertEquals(1, locationDto.getLon());

        template.postForObject("/api/locations", new CreateLocationCommand("Location2", 2, 2), LocationDto.class);

        List<LocationDto> locationDtos = template.exchange("/api/locations",
                HttpMethod.GET, //kérés típus
                null, // mit küldünk törzsben
                new ParameterizedTypeReference<List<LocationDto>>() {}).
        getBody();
        // a ParameterizedTypeReference adja meg, hogy List<LocationDto>-et várunk

        assertThat(locationDtos)
                .extracting(LocationDto::getName)
                .containsExactly("Location1", "Location2");
        assertThat(locationDtos)
                .extracting(LocationDto::getLat)
                .containsExactly(1d, 2d);
        assertThat(locationDtos)
                .extracting(LocationDto::getLon)
                .containsExactly(1d, 2d);

    }
}
