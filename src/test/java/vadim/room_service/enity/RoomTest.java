package vadim.room_service.enity;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import vadim.room_service.entity.Room;

import java.math.BigDecimal;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class RoomTest {
    private static Validator validator;

    @BeforeAll
    static void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void testRoomValidation_ValidData() {
        Room room = new Room();
        room.setName("Deluxe Room");
        room.setSleepingPlaces(2);
        room.setDescription("A spacious room with a beautiful view");
        room.setPrice(new BigDecimal("100.50"));

        Set<ConstraintViolation<Room>> violations = validator.validate(room);
        assertTrue(violations.isEmpty(), "There should be no validation errors");
    }

    @Test
    void testRoomValidation_InvalidPrice() {
        Room room = new Room();
        room.setName("Standard Room");
        room.setSleepingPlaces(2);
        room.setDescription("A basic room");
        room.setPrice(new BigDecimal("0.0"));

        Set<ConstraintViolation<Room>> violations = validator.validate(room);
        assertFalse(violations.isEmpty(), "There should be validation errors");
        assertEquals(1, violations.size(), "There should be one violation");
        assertEquals("Price must be greater than 0", violations.iterator().next().getMessage());
    }

    @Test
    void testRoomValidation_InvalidSleepingPlaces() {
        Room room = new Room();
        room.setName("Economy Room");
        room.setSleepingPlaces(0);
        room.setDescription("A basic economy room");
        room.setPrice(new BigDecimal("50.0"));

        Set<ConstraintViolation<Room>> violations = validator.validate(room);
        assertFalse(violations.isEmpty(), "There should be validation errors");
        assertEquals(1, violations.size(), "There should be one violation");
        assertEquals("Sleeping places cannot be 0 or below", violations.iterator().next().getMessage());
    }

    @Test
    void testRoomValidation_EmptyName() {
        Room room = new Room();
        room.setName(" ");
        room.setSleepingPlaces(2);
        room.setDescription("A room with no name");
        room.setPrice(new BigDecimal("75.0"));

        Set<ConstraintViolation<Room>> violations = validator.validate(room);
        assertFalse(violations.isEmpty(), "There should be validation errors");
        assertEquals(1, violations.size(), "There should be one violation");
        assertEquals("The room name cannot be empty", violations.iterator().next().getMessage());
    }
}
