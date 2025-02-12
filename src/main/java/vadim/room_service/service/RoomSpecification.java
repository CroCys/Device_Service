package vadim.room_service.service;

import org.springframework.data.jpa.domain.Specification;
import vadim.room_service.entity.Room;

import java.math.BigDecimal;

public class RoomSpecification {

    public static Specification<Room> nameFilter(final String name) {
        return (root, query, criteriaBuilder) ->
                name == null ? null : criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%" + name.toLowerCase() + "%");
    }

    public static Specification<Room> minSleepingPlaces(Integer minSleepingPlaces) {
        return (root, query, criteriaBuilder) ->
                minSleepingPlaces == null ? null : criteriaBuilder.greaterThanOrEqualTo(root.get("sleepingPlaces"), minSleepingPlaces);
    }

    public static Specification<Room> maxSleepingPlaces(Integer maxSleepingPlaces) {
        return (root, query, criteriaBuilder) ->
                maxSleepingPlaces == null ? null : criteriaBuilder.lessThanOrEqualTo(root.get("sleepingPlaces"), maxSleepingPlaces);
    }

    public static Specification<Room> minPrice(BigDecimal minPrice) {
        return (root, query, criteriaBuilder) ->
                minPrice == null ? null : criteriaBuilder.greaterThanOrEqualTo(root.get("price"), minPrice);
    }

    public static Specification<Room> maxPrice(BigDecimal maxPrice) {
        return (root, query, criteriaBuilder) ->
                maxPrice == null ? null : criteriaBuilder.lessThanOrEqualTo(root.get("price"), maxPrice);
    }
}
