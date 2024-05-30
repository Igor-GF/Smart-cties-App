package nl.igf.SmartCitiesApp.repository;

import nl.igf.SmartCitiesApp.entity.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface CityRepo extends JpaRepository<City, Long>, JpaSpecificationExecutor<City> {

    @Query("SELECT c FROM City c LEFT JOIN FETCH c.sensors WHERE c.id = :id")
    Optional<City> findByIdWithSensors(Long id);
}
