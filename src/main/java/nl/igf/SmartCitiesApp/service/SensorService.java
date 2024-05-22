package nl.igf.SmartCitiesApp.service;

import nl.igf.SmartCitiesApp.entity.Sensor;
import nl.igf.SmartCitiesApp.repository.SensorRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class SensorService {

    @Autowired
    private SensorRepo sensorRepo;

    public List<Sensor> getAllSensors() {
        return sensorRepo.findAll();
    }

    public Optional<Sensor> getSensorById(Long id) {
        return sensorRepo.findById(id);
    }

    public Sensor addSensor(Sensor sensor) {
        return sensorRepo.save(sensor);
    }

    public Sensor updateSensor(Long id, Sensor sensorDetails) {
        Optional<Sensor> sensorOptional = sensorRepo.findById(id);
        if (sensorOptional.isPresent()) {
            Sensor sensor = sensorOptional.get();
            sensor.setType(sensorDetails.getType());
            sensor.setDescription(sensorDetails.getDescription());
            sensor.setCity(sensorDetails.getCity());
            return sensorRepo.save(sensor);
        } else {
            throw new NoSuchElementException("This sensor does not exist");
        }
    }

    public void deleteSensor(Long id) {
        sensorRepo.deleteById(id);
    }
}
