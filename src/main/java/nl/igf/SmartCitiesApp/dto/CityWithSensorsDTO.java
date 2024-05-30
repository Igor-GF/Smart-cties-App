package nl.igf.SmartCitiesApp.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CityWithSensorsDTO {

    private Long id;
    private String name;
    private String stateOrProvince;
    private String country;
    private List<SensorDTO> sensors;
}
