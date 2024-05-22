package nl.igf.SmartCitiesApp.service;

import nl.igf.SmartCitiesApp.dto.SensorDTO;
import nl.igf.SmartCitiesApp.entity.City;
import nl.igf.SmartCitiesApp.entity.Sensor;
import nl.igf.SmartCitiesApp.repository.CityRepo;
import nl.igf.SmartCitiesApp.repository.SensorRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SensorService {

    @Autowired
    private SensorRepo sensorRepo;
    @Autowired
    private CityRepo cityRepo;

    public List<SensorDTO> getAllSensors() {
        return sensorRepo.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public Optional<SensorDTO> getSensorById(Long id) {
        return sensorRepo.findById(id).map(this::convertToDTO);
    }

    public SensorDTO addSensor(SensorDTO sensorDTO) {
        Sensor sensor = convertToEntity(sensorDTO);
        Sensor savedSensor = sensorRepo.save(sensor);
        return convertToDTO(savedSensor);
    }

    public SensorDTO updateSensor(Long id, SensorDTO sensorDTO) {
        Optional<Sensor> sensorOptional = sensorRepo.findById(id);
        if (sensorOptional.isPresent()) {
            Sensor sensor = sensorOptional.get();
            sensor.setType(sensorDTO.getType());
            sensor.setDescription(sensorDTO.getDescription());
            Optional<City> cityOptional = cityRepo.findById(sensorDTO.getCityId());
            cityOptional.ifPresent(sensor::setCity);
            Sensor updatedCity = sensorRepo.save(sensor);
            return convertToDTO(updatedCity);
        } else {
            throw new NoSuchElementException("This sensor does not exist");
        }
    }

    public void deleteSensor(Long id) {
        sensorRepo.deleteById(id);
    }

    private SensorDTO convertToDTO(Sensor sensor) {
        SensorDTO sensorDTO = new SensorDTO();
        sensorDTO.setId(sensor.getId());
        sensorDTO.setType(sensor.getType());
        sensorDTO.setDescription(sensor.getDescription());
        sensorDTO.setCityId(sensor.getCity().getId());
        return sensorDTO;
    }

    private Sensor convertToEntity(SensorDTO sensorDTO) {
        Sensor sensor = new Sensor();
        sensor.setType(sensorDTO.getType());
        sensor.setDescription(sensorDTO.getDescription());
        Optional<City> cityOptional = cityRepo.findById(sensorDTO.getCityId());
        cityOptional.ifPresent(sensor::setCity);
        return sensor;
    }
}
