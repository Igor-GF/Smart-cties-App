package nl.igf.SmartCitiesApp.service;

import nl.igf.SmartCitiesApp.dto.CityDTO;
import nl.igf.SmartCitiesApp.entity.City;
import nl.igf.SmartCitiesApp.repository.CityRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CityService {

    @Autowired
    private CityRepo cityRepo;

    public List<CityDTO> getAllCities() {
        return cityRepo.findAll().stream()
                .map(city -> this.convertToDTO(city))
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
