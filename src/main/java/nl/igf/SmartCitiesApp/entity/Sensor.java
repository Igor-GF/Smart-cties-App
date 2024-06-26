package nl.igf.SmartCitiesApp.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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

    @NotNull(message = "City is mandatory")
    @ManyToOne
    @JoinColumn(name = "city_id")
    private City city;
}
