package nl.igf.SmartCitiesApp.repository;

import nl.igf.SmartCitiesApp.entity.Sensor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface SensorRepo extends JpaRepository<Sensor, Long>, JpaSpecificationExecutor<Sensor> {
}
