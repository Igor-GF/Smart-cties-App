package nl.igf.SmartCitiesApp.controller;

import jakarta.validation.Valid;
import nl.igf.SmartCitiesApp.dto.SensorDTO;
import nl.igf.SmartCitiesApp.service.SensorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/sensors")
public class SensorController {

    @Autowired
    private SensorService sensorService;

    @PostMapping("/city/{cityId}")
    public ResponseEntity<SensorDTO> addSensorToCity(@PathVariable Long cityId, @Valid @RequestBody SensorDTO sensorDTO) {
        Optional<SensorDTO> createdSensor = sensorService.addSensorToCity(cityId, sensorDTO);
        return createdSensor.map(sensor -> ResponseEntity.status(201).body(sensor))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public List<SensorDTO> getAllSensors(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size,
        @RequestParam(defaultValue = "type") String sortBy,
        @RequestParam(required = false) String type,
        @RequestParam(required = false) Long cityId
    ) {
        return sensorService.getAllSensors(page, size, sortBy, type, cityId);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SensorDTO> getSensorById(@PathVariable long id) {
        Optional<SensorDTO> sensor = sensorService.getSensorById(id);
        return sensor.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/add")
    public ResponseEntity<SensorDTO> addSensor(@Valid @RequestBody SensorDTO sensorDTO) {
        SensorDTO createdSensor = sensorService.addSensor(sensorDTO);
        return ResponseEntity.status(201).body(createdSensor);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SensorDTO> updateSensor(@PathVariable long id, @Valid @RequestBody SensorDTO sensorDTO) {
        Optional<SensorDTO> updatedSensor = sensorService.updateSensor(id, sensorDTO);
        return updatedSensor.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSensor(@PathVariable long id) {
        sensorService.deleteSensor(id);
        return ResponseEntity.noContent().build();
    }
}