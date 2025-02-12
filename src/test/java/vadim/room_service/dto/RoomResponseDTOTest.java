package vadim.room_service.dto;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class RoomResponseDTOTest {

    @Test
    void testNoArgsConstructor() {
        RoomResponseDTO roomResponseDTO = new RoomResponseDTO();
        assertNotNull(roomResponseDTO);
    }

    @Test
    void testAllArgsConstructor() {
        RoomResponseDTO roomResponseDTO = new RoomResponseDTO(1L, "Suite", 2, "Luxury suite",
                BigDecimal.valueOf(200.0), LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES));

        assertEquals(1L, roomResponseDTO.getId());
        assertEquals("Suite", roomResponseDTO.getName());
        assertEquals(2, roomResponseDTO.getSleepingPlaces());
        assertEquals("Luxury suite", roomResponseDTO.getDescription());
        assertEquals(BigDecimal.valueOf(200.0), roomResponseDTO.getPrice());
    }

    @Test
    void testSettersAndGetters() {
        RoomResponseDTO roomResponseDTO = new RoomResponseDTO();

        roomResponseDTO.setId(2L);
        roomResponseDTO.setName("Deluxe Room");
        roomResponseDTO.setSleepingPlaces(3);
        roomResponseDTO.setDescription("A spacious deluxe room");
        roomResponseDTO.setPrice(BigDecimal.valueOf(150.0));

        assertEquals(2L, roomResponseDTO.getId());
        assertEquals("Deluxe Room", roomResponseDTO.getName());
        assertEquals(3, roomResponseDTO.getSleepingPlaces());
        assertEquals("A spacious deluxe room", roomResponseDTO.getDescription());
        assertEquals(BigDecimal.valueOf(150.0), roomResponseDTO.getPrice());
    }
}
