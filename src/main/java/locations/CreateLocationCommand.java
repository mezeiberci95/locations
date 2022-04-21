package locations;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateLocationCommand {

    @Schema(description = "Name of the location", example = "Good place")
    @NotBlank(message = "Name can not be blank")
    private String name;

    @Schema(description = "Lat coord of the location", example = "42")
    @Min(-90)
    @Max(90)
    private double lat;

    @Schema(description = "Lon coord of the location", example = "42")
    @Min(-180)
    @Max(180)
    private double lon;
}
