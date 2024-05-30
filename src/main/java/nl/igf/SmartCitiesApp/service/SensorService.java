package nl.igf.SmartCitiesApp.service;

import nl.igf.SmartCitiesApp.dto.SensorDTO;
import nl.igf.SmartCitiesApp.entity.City;
import nl.igf.SmartCitiesApp.entity.Sensor;
import nl.igf.SmartCitiesApp.repository.CityRepo;
import nl.igf.SmartCitiesApp.repository.SensorRepo;
import nl.igf.SmartCitiesApp.specification.SensorSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
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

    public Optional<SensorDTO> addSensorToCity(Long cityId, SensorDTO sensorDTO) {
        Optional<City> cityOpt = cityRepo.findById(cityId);
        if (cityOpt.isPresent()) {
            City city = cityOpt.get();
            Sensor sensor = new Sensor();
            sensor.setType(sensorDTO.getType());
            sensor.setDescription(sensorDTO.getDescription());
            sensor.setCity(city);
            Sensor savedSensor = sensorRepo.save(sensor);

            SensorDTO savedSensorDTO = new SensorDTO();
            savedSensorDTO.setId(savedSensor.getId());
            savedSensorDTO.setType(savedSensor.getType());
            savedSensorDTO.setDescription(savedSensor.getDescription());

            return Optional.of(savedSensorDTO);
        }
        return Optional.empty();
    }

    public List<SensorDTO> getAllSensors(int page, int size, String sortBy, String type, Long cityId) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        Specification<Sensor> spec = Specification.where(null);

        if (type != null && !type.isEmpty()) spec = spec.and(SensorSpecification.hasType(type));
        if (cityId != null) spec = spec.and(SensorSpecification.hasCityId(cityId));

        Page<Sensor> sensorsPage = sensorRepo.findAll(spec, pageable);
        return sensorsPage.stream()
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
