package nl.igf.SmartCitiesApp.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Entity
@Data
public class Sensor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank(message = "Type is mandatory")
    private String type;

    private String description;

    @NotBlank(message = "City is mandatory")
    @ManyToOne
    private City city;
}
