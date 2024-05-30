package nl.igf.SmartCitiesApp.specification;

import nl.igf.SmartCitiesApp.entity.Sensor;
import org.springframework.data.jpa.domain.Specification;

public class SensorSpecification {

    public static Specification<Sensor> hasType(String type) {
        return (sensor, cq, cb) -> cb.equal(sensor.get("type"), type);
    }

    public static Specification<Sensor> hasCityId(Long cityId) {
        return (sensor, cq, cb) -> cb.equal(sensor.get("city").get("id"), cityId);
    }
}
