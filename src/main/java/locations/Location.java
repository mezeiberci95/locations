package locations;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class Location {

    private Long id;

    private String name;

    private double lat;

    private double lon;

}
