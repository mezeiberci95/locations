package locations;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateLocationCommand {

    @Schema(description = "Name of the location", example = "Good place")
    private String name;

    @Schema(description = "Lat coord of the location", example = "42")
    private double lat;

    @Schema(description = "Lon coord of the location", example = "42")
    private double lon;
}
