package nl.igf.SmartCitiesApp.repository;

import nl.igf.SmartCitiesApp.entity.City;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CityRepo extends JpaRepository<City, Long> {
}
