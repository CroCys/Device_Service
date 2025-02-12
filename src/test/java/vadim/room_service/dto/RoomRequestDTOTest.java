package vadim.room_service.dto;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class RoomRequestDTOTest {

    @Test
    void testNoArgsConstructor() {
        RoomRequestDTO roomRequestDTO = new RoomRequestDTO();
        assertNotNull(roomRequestDTO);
    }

    @Test
    void testAllArgsConstructor() {
        RoomRequestDTO roomRequestDTO = new RoomRequestDTO("Suite", 2, "Luxury suite", BigDecimal.valueOf(200.0));

        assertEquals("Suite", roomRequestDTO.getName());
        assertEquals(2, roomRequestDTO.getSleepingPlaces());
        assertEquals("Luxury suite", roomRequestDTO.getDescription());
        assertEquals(BigDecimal.valueOf(200.0), roomRequestDTO.getPrice());
    }

    @Test
    void testSettersAndGetters() {
        RoomRequestDTO roomRequestDTO = new RoomRequestDTO();

        roomRequestDTO.setName("Deluxe Room");
        roomRequestDTO.setSleepingPlaces(3);
        roomRequestDTO.setDescription("A spacious deluxe room");
        roomRequestDTO.setPrice(BigDecimal.valueOf(150.0));

        assertEquals("Deluxe Room", roomRequestDTO.getName());
        assertEquals(3, roomRequestDTO.getSleepingPlaces());
        assertEquals("A spacious deluxe room", roomRequestDTO.getDescription());
        assertEquals(BigDecimal.valueOf(150.0), roomRequestDTO.getPrice());
    }
}
