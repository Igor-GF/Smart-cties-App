package nl.igf.SmartCitiesApp.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SensorDTO {

    private Long id;
    private String type;
    private String description;
    private Long cityId; // Only store the city ID
}
