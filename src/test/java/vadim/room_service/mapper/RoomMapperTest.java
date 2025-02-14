package vadim.room_service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.springframework.boot.test.context.SpringBootTest;
import vadim.room_service.dto.RoomRequestDTO;
import vadim.room_service.dto.RoomResponseDTO;
import vadim.room_service.entity.Room;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class RoomMapperTest {

    private RoomMapper roomMapper;

    @BeforeEach
    void setUp() {
        roomMapper = Mappers.getMapper(RoomMapper.class);
    }

    @Test
    void testRoomToRoomResponseDTO() {
        Room room = new Room();
        room.setId(1L);
        room.setName("Suite");
        room.setSleepingPlaces(2);
        room.setDescription("Luxury suite");
        room.setPrice(BigDecimal.valueOf(250.0));

        RoomResponseDTO responseDTO = roomMapper.roomToRoomResponseDTO(room);

        assertNotNull(responseDTO);
        assertEquals(1L, responseDTO.getId());
        assertEquals("Suite", responseDTO.getName());
        assertEquals(2, responseDTO.getSleepingPlaces());
        assertEquals("Luxury suite", responseDTO.getDescription());
        assertEquals(BigDecimal.valueOf(250.0), responseDTO.getPrice());
    }

    @Test
    void testRoomRequestDTOToRoom() {
        RoomRequestDTO requestDTO = new RoomRequestDTO("Deluxe Room", 3, "A spacious deluxe room", BigDecimal.valueOf(180.0));

        Room room = roomMapper.roomRequestDTOToRoom(requestDTO);

        assertNotNull(room);
        assertNull(room.getId());
        assertEquals("Deluxe Room", room.getName());
        assertEquals(3, room.getSleepingPlaces());
        assertEquals("A spacious deluxe room", room.getDescription());
        assertEquals(BigDecimal.valueOf(180.0), room.getPrice());
    }
}
