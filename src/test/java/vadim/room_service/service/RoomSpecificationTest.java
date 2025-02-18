package vadim.room_service.service;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.domain.Specification;
import vadim.room_service.entity.Room;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class RoomSpecificationTest {

    @Test
    void testNameFilter() {
        Specification<Room> spec = RoomSpecification.nameFilter("Deluxe");
        assertThat(spec).isNotNull();
    }

    @Test
    void testMinSleepingPlaces() {
        Specification<Room> spec = RoomSpecification.minSleepingPlaces(2);
        assertThat(spec).isNotNull();
    }

    @Test
    void testMaxSleepingPlaces() {
        Specification<Room> spec = RoomSpecification.maxSleepingPlaces(4);
        assertThat(spec).isNotNull();
    }

    @Test
    void testMinPrice() {
        Specification<Room> spec = RoomSpecification.minPrice(new BigDecimal("100.00"));
        assertThat(spec).isNotNull();
    }

    @Test
    void testMaxPrice() {
        Specification<Room> spec = RoomSpecification.maxPrice(new BigDecimal("300.00"));
        assertThat(spec).isNotNull();
    }
}
