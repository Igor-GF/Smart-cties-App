package nl.igf.SmartCitiesApp.specification;

import nl.igf.SmartCitiesApp.entity.City;
import org.springframework.data.jpa.domain.Specification;

public class CitySpecification {

    public static Specification<City> hasName(String name) {
        return (city, cq, cb) -> cb.equal(city.get("name"), name);
    }

    public static Specification<City> hasCountry(String country) {
        return (city, cq, cb) -> cb.equal(city.get("country"), country);
    }

    public static Specification<City> hasStateOrProvince(String stateOrProvince) {
        return (city, cq, cb) -> cb.equal(city.get("stateOrProvince"), stateOrProvince);
    }
}
