package locations;

import io.restassured.http.ContentType;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static io.restassured.module.mockmvc.RestAssuredMockMvc.with;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.equalTo;

@SpringBootTest
@AutoConfigureMockMvc // ezzel indul el a MockMvc
public class LocationsControllerRestAssuredIT {

    // http protokol eléréséhez
    @Autowired
    MockMvc mockMvc;

    @Autowired
    LocationsService locationsService;

    @BeforeEach
    void init() {
        RestAssuredMockMvc.mockMvc(mockMvc);
        RestAssuredMockMvc.requestSpecification =
                given()
                        .contentType(ContentType.JSON)
                        .accept(ContentType.JSON);

        locationsService.deleteLocations();
    }

    @Test
    void testListLocations() {
        with()
                .body(new CreateLocationCommand("Location1", 1d, 1d))
                .post("/api/locations")
                .then()
                .statusCode(201)
                .body("name", equalTo("Location1"))
                .body("lat", equalTo(1f))
                .body("lon", equalTo(1f))
                .log();

        with()
                .get("/api/locations")
                .then()
                .statusCode(200)
                .body("size()", equalTo(1))
                .body("[0].name", equalTo("Location1"))
                .body("[0].lat", equalTo(1f))
                .body("[0].lon", equalTo(1f));
    }

    // séma alapján validálja a responseban kapott objektumot
    @Test
    void validate() {
        with()
                .body(new CreateLocationCommand("Location1", 1, 1))
                .post("/api/locations")
                .then()
                .body(matchesJsonSchemaInClasspath("location-dto.json"));
    }
}
