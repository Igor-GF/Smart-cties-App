package nl.igf.SmartCitiesApp.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CityDTO {

    private Long id;
    private String name;
    private String stateOrProvince;
    private String country;
}
