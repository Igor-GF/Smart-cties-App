package nl.igf.SmartCitiesApp.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Sensor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String type;
    private String description;

    @ManyToOne
    private City city;
}
