package nl.igf.SmartCitiesApp.service;

import nl.igf.SmartCitiesApp.dto.CityDTO;
import nl.igf.SmartCitiesApp.dto.CityWithSensorsDTO;
import nl.igf.SmartCitiesApp.dto.SensorDTO;
import nl.igf.SmartCitiesApp.entity.City;
import nl.igf.SmartCitiesApp.repository.CityRepo;
import nl.igf.SmartCitiesApp.specification.CitySpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CityService {

    @Autowired
    private CityRepo cityRepo;

    public Optional<CityWithSensorsDTO> getCityWithSensors(Long id) {
        Optional<City> cityOptional = cityRepo.findByIdWithSensors(id);
        if (cityOptional.isPresent()) {
            City city = cityOptional.get();
            CityWithSensorsDTO cityWithSensorsDTO = new CityWithSensorsDTO();
            cityWithSensorsDTO.setId(city.getId());
            cityWithSensorsDTO.setName(city.getName());
            cityWithSensorsDTO.setStateOrProvince(city.getStateOrProvince());
            cityWithSensorsDTO.setCountry(city.getCountry());

            List<SensorDTO> sensors = city.getSensors().stream()
                    .map(sensor -> {
                        var sensorDTO = new SensorDTO();
                        sensorDTO.setId(sensor.getId());
                        sensorDTO.setType(sensor.getType());
                        sensorDTO.setDescription(sensor.getDescription());
                        return sensorDTO;
                    })
                    .collect(Collectors.toList());

            cityWithSensorsDTO.setSensors(sensors);
            return Optional.of(cityWithSensorsDTO);
        }
        return Optional.empty();
    }

    public List<CityDTO> getAllCities(int page, int size, String sortBy, String name, String country, String stateOrProvince) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        Specification<City> spec = Specification.where(null);

        if (name != null && !name.isEmpty()) spec = spec.and(CitySpecification.hasName(name));
        if (country != null && !country.isEmpty()) spec = spec.and(CitySpecification.hasCountry(country));
        if (stateOrProvince != null && !stateOrProvince.isEmpty()) spec = spec.and(CitySpecification.hasStateOrProvince(stateOrProvince));

        Page<City> citiesPage = cityRepo.findAll(spec, pageable);
        return citiesPage.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public Optional<CityDTO> getCityById(long id) {
        return cityRepo.findById(id).map(this::convertToDTO);
    }

    public CityDTO addCity(CityDTO cityDTO) {
        City city = convertToEntity(cityDTO);
        City savedCity = cityRepo.save(city);
        return convertToDTO(savedCity);
    }

    public CityDTO updateCity(Long id, CityDTO cityDDTO) {
        Optional<City> cityOptional = cityRepo.findById(id);
        if (cityOptional.isPresent()) {
            City city = cityOptional.get();
            city.setName(cityDDTO.getName());
            city.setStateOrProvince((cityDDTO.getStateOrProvince()));
            city.setCountry(cityDDTO.getCountry());
            City updatedCity = cityRepo.save(city);
            return convertToDTO(updatedCity);
        } else {
            throw new NoSuchElementException("This city does not exist");
        }
    }

    public void deleteCity(long id) {
        cityRepo.deleteById(id);
    }

    private CityDTO convertToDTO(City city) {
        CityDTO cityDTO = new CityDTO();
        cityDTO.setId(city.getId());
        cityDTO.setName(city.getName());
        cityDTO.setStateOrProvince(city.getStateOrProvince());
        cityDTO.setCountry(city.getCountry());
        return cityDTO;
    }

    private City convertToEntity(CityDTO cityDTO) {
        City city = new City();
        city.setName(cityDTO.getName());
        city.setStateOrProvince(cityDTO.getStateOrProvince());
        city.setCountry(cityDTO.getCountry());
        return city;
    }
}
