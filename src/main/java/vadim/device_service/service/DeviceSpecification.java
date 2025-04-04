package vadim.device_service.service;

import org.springframework.data.jpa.domain.Specification;
import vadim.device_service.entity.Category;
import vadim.device_service.entity.Device;

import java.math.BigDecimal;
import java.time.LocalDate;

public class DeviceSpecification {

    private DeviceSpecification() {
    }

    public static Specification<Device> nameFilter(final String name) {
        return (root, query, criteriaBuilder) ->
                name == null ? null : criteriaBuilder.like(criteriaBuilder.lower(root.get("name")),
                        "%" + name.toLowerCase() + "%");
    }

    public static Specification<Device> brandFilter(final String brand) {
        return (root, query, criteriaBuilder) ->
                brand == null ? null : criteriaBuilder.like(criteriaBuilder.lower(root.get("brand")),
                        "%" + brand.toLowerCase() + "%");
    }

    public static Specification<Device> categoryFilter(final Category category) {
        return (root, query, criteriaBuilder) ->
                category == null ? null : criteriaBuilder.equal(root.get("category"), category);
    }

    public static Specification<Device> minReleaseDate(LocalDate minReleaseDate) {
        return (root, query, criteriaBuilder) ->
                minReleaseDate == null ? null : criteriaBuilder
                        .greaterThanOrEqualTo(root.get("releaseDate"), minReleaseDate);
    }

    public static Specification<Device> maxReleaseDate(LocalDate maxReleaseDate) {
        return (root, query, criteriaBuilder) ->
                maxReleaseDate == null ? null : criteriaBuilder
                        .lessThanOrEqualTo(root.get("releaseDate"), maxReleaseDate);
    }

    public static Specification<Device> minRating(BigDecimal minRating) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.greaterThanOrEqualTo(root.get("averageRating"), minRating);
    }

    public static Specification<Device> maxRating(BigDecimal maxRating) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.lessThanOrEqualTo(root.get("averageRating"), maxRating);
    }
}
