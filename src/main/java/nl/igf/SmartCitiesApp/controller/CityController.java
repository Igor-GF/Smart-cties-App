package nl.igf.SmartCitiesApp.controller;

import jakarta.validation.Valid;
import nl.igf.SmartCitiesApp.dto.CityDTO;
import nl.igf.SmartCitiesApp.dto.CityWithSensorsDTO;
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

    @GetMapping("/{id}/sensors")
    public ResponseEntity<CityWithSensorsDTO> getCityWithSensors(@PathVariable Long id) {
        Optional<CityWithSensorsDTO> city = cityService.getCityWithSensors(id);
        return city.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public List<CityDTO> getAllCities(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size,
        @RequestParam(defaultValue = "name") String sortBy,
        @RequestParam(required = false) String name,
        @RequestParam(required = false) String country,
        @RequestParam(required = false) String stateOrProvince
    ) {
        return cityService.getAllCities(page, size, sortBy, name, country, stateOrProvince);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CityDTO> getCityById(@PathVariable long id) {
        Optional<CityDTO> cityById = cityService.getCityById(id);
        return cityById.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/add")
    public ResponseEntity<CityDTO> addCity(@Valid @RequestBody CityDTO cityDTO) {
        CityDTO createdCity = cityService.addCity(cityDTO);
        return ResponseEntity.status(200).body(createdCity);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<CityDTO> updateCity(@PathVariable long id, @Valid @RequestBody CityDTO cityDetails) {
        CityDTO updatedCity = cityService.updateCity(id, cityDetails);
        if (updatedCity != null) {
            return ResponseEntity.ok(updatedCity);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteCity(@PathVariable long id) {
        cityService.deleteCity(id);
        return ResponseEntity.noContent().build();
    }
}
