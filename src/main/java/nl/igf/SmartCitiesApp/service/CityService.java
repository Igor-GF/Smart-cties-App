package nl.igf.SmartCitiesApp.service;

import nl.igf.SmartCitiesApp.entity.City;
import nl.igf.SmartCitiesApp.repository.CityRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class CityService {

    @Autowired
    private CityRepo cityRepo;

    public List<City> getAllCities() {
        return cityRepo.findAll();
    }

    public Optional<City> getCityById(long id) {
        return cityRepo.findById(id);
    }

    public City addCity(City city) {
        return cityRepo.save(city);
    }

    public City updateCity(Long id, City cityDetails) {
        Optional<City> cityOptional = cityRepo.findById(id);
        if (cityOptional.isPresent()) {
            City city = cityOptional.get();
            city.setName(cityDetails.getName());
            city.setStateOrProvince((cityDetails.getStateOrProvince()));
            city.setCountry(cityDetails.getCountry());
            return cityRepo.save(city);
        } else {
            throw new NoSuchElementException("This city does not exist");
        }
    }

    public void deleteCity(long id) {
        cityRepo.deleteById(id);
    }
}
