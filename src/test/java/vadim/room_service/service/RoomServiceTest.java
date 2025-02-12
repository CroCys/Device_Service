package vadim.room_service.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import vadim.room_service.dto.RoomRequestDTO;
import vadim.room_service.dto.RoomResponseDTO;
import vadim.room_service.entity.Room;
import vadim.room_service.exception.RoomNotFoundException;
import vadim.room_service.mapper.RoomMapper;
import vadim.room_service.repository.RoomRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class RoomServiceTest {

    @Mock
    private RoomRepository roomRepository;

    @Mock
    private RoomMapper roomMapper;

    @InjectMocks
    private RoomService roomService;

    private Room room;
    private RoomRequestDTO roomRequestDTO;
    private RoomResponseDTO roomResponseDTO;

    @BeforeEach
    void setUp() {
        room = new Room();
        room.setId(1L);
        room.setName("Deluxe Room");
        room.setDescription("A luxury room");
        room.setPrice(BigDecimal.valueOf(150.0));
        room.setSleepingPlaces(2);

        roomRequestDTO = new RoomRequestDTO("Deluxe Room", 1, "A luxury room", BigDecimal.valueOf(150.0));
        roomResponseDTO = new RoomResponseDTO(1L, "Deluxe Room", 1, "A luxury room",
                BigDecimal.valueOf(150.0), LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES));
    }

    @Test
    void testGetAllRooms() {
        Page<Room> page = new PageImpl<>(List.of(room));
        when(roomRepository.findAll(any(Pageable.class))).thenReturn(page);
        when(roomMapper.roomToRoomResponseDTO(any(Room.class))).thenReturn(roomResponseDTO);

        Page<RoomResponseDTO> result = roomService.getAllRooms(Pageable.unpaged());

        assertEquals(1, result.getTotalElements());
        assertEquals("Deluxe Room", result.getContent().get(0).getName());
        verify(roomRepository, times(1)).findAll(any(Pageable.class));
    }

    @Test
    void testGetRoomById_Success() {
        when(roomRepository.findById(1L)).thenReturn(Optional.of(room));
        when(roomMapper.roomToRoomResponseDTO(room)).thenReturn(roomResponseDTO);

        RoomResponseDTO result = roomService.getRoomById(1L);

        assertNotNull(result);
        assertEquals("Deluxe Room", result.getName());
        verify(roomRepository, times(1)).findById(1L);
    }

    @Test
    void testGetRoomById_NotFound() {
        when(roomRepository.findById(2L)).thenReturn(Optional.empty());

        assertThrows(RoomNotFoundException.class, () -> roomService.getRoomById(2L));
    }

    @Test
    void testCreateRoom_Success() {
        RoomRequestDTO roomRequestDTO = new RoomRequestDTO("Deluxe Room", 2, "Luxury suite", BigDecimal.valueOf(300.0));

        Room room = new Room();
        room.setId(1L);
        room.setName("Deluxe Room");
        room.setDescription("Luxury suite");
        room.setPrice(BigDecimal.valueOf(300.0));
        room.setSleepingPlaces(2);
        room.setCreatedAt(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES));
        room.setUpdatedAt(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES));

        RoomResponseDTO roomResponseDTO = new RoomResponseDTO(1L, "Deluxe Room", 2, "Luxury suite",
                BigDecimal.valueOf(300.0), room.getCreatedAt(), room.getUpdatedAt());

        when(roomMapper.roomRequestDTOToRoom(any(RoomRequestDTO.class))).thenReturn(room);
        when(roomRepository.save(any(Room.class))).thenReturn(room);
        when(roomMapper.roomToRoomResponseDTO(any(Room.class))).thenReturn(roomResponseDTO);

        RoomResponseDTO result = roomService.createRoom(roomRequestDTO);

        assertNotNull(result);
        assertEquals("Deluxe Room", result.getName());
        assertEquals(2, result.getSleepingPlaces());
        assertEquals(BigDecimal.valueOf(300.0), result.getPrice());
        assertNotNull(result.getCreatedAt());
        assertNotNull(result.getUpdatedAt());

        verify(roomRepository, times(1)).save(any(Room.class));
    }

    @Test
    void testCreateRoom_InvalidData() {
        assertThrows(IllegalArgumentException.class, () -> roomService.createRoom(new RoomRequestDTO()));
    }

    @Test
    void testUpdateRoom_Success() {
        RoomRequestDTO updatedRoomDTO = new RoomRequestDTO("Updated Room", 3, "Updated description", BigDecimal.valueOf(200.0));

        Room existingRoom = new Room();
        existingRoom.setId(1L);
        existingRoom.setName("Old Room");
        existingRoom.setDescription("Old description");
        existingRoom.setPrice(BigDecimal.valueOf(100.0));
        existingRoom.setSleepingPlaces(2);
        existingRoom.setCreatedAt(LocalDateTime.of(2025, 2, 12, 20, 5));
        existingRoom.setUpdatedAt(LocalDateTime.of(2025, 2, 12, 20, 5));

        Room updatedRoom = new Room();
        updatedRoom.setId(1L);
        updatedRoom.setName("Updated Room");
        updatedRoom.setDescription("Updated description");
        updatedRoom.setPrice(BigDecimal.valueOf(200.0));
        updatedRoom.setSleepingPlaces(3);
        updatedRoom.setCreatedAt(existingRoom.getCreatedAt());
        updatedRoom.setUpdatedAt(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES));

        when(roomRepository.findById(1L)).thenReturn(Optional.of(existingRoom));
        when(roomRepository.save(any(Room.class))).thenReturn(updatedRoom);
        when(roomMapper.roomToRoomResponseDTO(any(Room.class))).thenReturn(
                new RoomResponseDTO(1L, "Updated Room", 3, "Updated description",
                        BigDecimal.valueOf(200.0), existingRoom.getCreatedAt(), updatedRoom.getUpdatedAt()));

        RoomResponseDTO result = roomService.updateRoom(1L, updatedRoomDTO);

        assertNotNull(result);
        assertEquals("Updated Room", result.getName());
        assertEquals(3, result.getSleepingPlaces());
        assertEquals(BigDecimal.valueOf(200.0), result.getPrice());
        assertEquals(existingRoom.getCreatedAt(), result.getCreatedAt());
        assertNotNull(result.getUpdatedAt());

        verify(roomRepository, times(1)).save(any(Room.class));
    }


    @Test
    void testUpdateRoom_NotFound() {
        RoomRequestDTO updatedRoomDTO = new RoomRequestDTO("Updated Room", 1, "Updated description", BigDecimal.valueOf(200.0));
        when(roomRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RoomNotFoundException.class, () -> roomService.updateRoom(1L, updatedRoomDTO));
    }

    @Test
    void testDeleteRoom_Success() {
        when(roomRepository.existsById(1L)).thenReturn(true);
        doNothing().when(roomRepository).deleteById(1L);

        assertDoesNotThrow(() -> roomService.deleteRoom(1L));
        verify(roomRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteRoom_NotFound() {
        when(roomRepository.existsById(1L)).thenReturn(false);

        assertThrows(RoomNotFoundException.class, () -> roomService.deleteRoom(1L));
    }
}
