package nl.igf.SmartCitiesApp.controller;

import jakarta.validation.Valid;
import nl.igf.SmartCitiesApp.entity.City;
import nl.igf.SmartCitiesApp.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/cities")
public class CityController {

    @Autowired
    private CityService cityService;

    @GetMapping
    public List<City> getAllCities() {
        return cityService.getAllCities();
    }

    @GetMapping("/{id}")
    public ResponseEntity<City> getCityById(@PathVariable long id) {
        Optional<City> cityById = cityService.getCityById(id);
        return cityById.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/add")
    public City addCity(@Valid @RequestBody City city) {
        return cityService.addCity(city);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<City> updateCity(@PathVariable long id, @Valid @RequestBody City cityDetails) {
        City updatedCity = cityService.updateCity(id, cityDetails);
        if (updatedCity != null) {
            return ResponseEntity.ok(updatedCity);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/delete/{id}")
    public void deleteCity(@PathVariable long id) {
        cityService.deleteCity(id);
    }
}
