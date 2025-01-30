package vadim.room_service.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;
import vadim.room_service.entity.Room;
import vadim.room_service.exception.RoomNotFoundException;
import vadim.room_service.repository.RoomRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class RoomServiceTest {

    @Mock
    private RoomRepository roomRepository;

    @InjectMocks
    private RoomService roomService;

    private Room room;

    @BeforeEach
    void setUp() {
        room = new Room();
        room.setId(1L);
        room.setName("Deluxe Room");
        room.setDescription("A luxury room");
        room.setPrice(BigDecimal.valueOf(150.0));
        room.setIsAvailable(true);
    }

    @Test
    void testGetAllRooms() {
        when(roomRepository.findAll()).thenReturn(List.of(room));

        List<Room> rooms = roomService.getAllRooms();

        assertEquals(1, rooms.size());
        assertEquals("Deluxe Room", rooms.getFirst().getName());
        verify(roomRepository, times(1)).findAll();
    }

    @Test
    void testGetAllRoomsSorted() {
        when(roomRepository.findAll(Sort.by("price"))).thenReturn(List.of(room));

        List<Room> rooms = roomService.getAllRoomsSorted("price");

        assertEquals(1, rooms.size());
        assertEquals(BigDecimal.valueOf(150.0), rooms.getFirst().getPrice());
        verify(roomRepository, times(1)).findAll(Sort.by("price"));
    }

    @Test
    void testGetRoomById_Success() {
        when(roomRepository.findById(1L)).thenReturn(Optional.of(room));

        Room foundRoom = roomService.getRoomById(1L);

        assertNotNull(foundRoom);
        assertEquals(1L, foundRoom.getId());
        assertEquals("Deluxe Room", foundRoom.getName());
        verify(roomRepository, times(1)).findById(1L);
    }

    @Test
    void testGetRoomById_NotFound() {
        when(roomRepository.findById(2L)).thenReturn(Optional.empty());

        assertThrows(RoomNotFoundException.class, () -> roomService.getRoomById(2L));
    }

    @Test
    void testCreateRoom_Success() {
        when(roomRepository.save(room)).thenReturn(room);

        Room savedRoom = roomService.createRoom(room);

        assertNotNull(savedRoom);
        assertEquals("Deluxe Room", savedRoom.getName());
        verify(roomRepository, times(1)).save(room);
    }

    @Test
    void testCreateRoom_InvalidData() {
        Room invalidRoom = new Room();

        assertThrows(IllegalArgumentException.class, () -> roomService.createRoom(invalidRoom));
    }

    @Test
    void testUpdateRoom_Success() {
        Room updatedRoom = new Room();
        updatedRoom.setName("Updated Room");
        updatedRoom.setDescription("Updated description");
        updatedRoom.setPrice(BigDecimal.valueOf(200.0));
        updatedRoom.setIsAvailable(false);

        when(roomRepository.findById(1L)).thenReturn(Optional.of(room));
        when(roomRepository.save(any(Room.class))).thenReturn(updatedRoom);

        Room result = roomService.updateRoom(1L, updatedRoom);

        assertNotNull(result);
        assertEquals("Updated Room", result.getName());
        assertEquals(BigDecimal.valueOf(200.0), result.getPrice());
        assertFalse(result.getIsAvailable());
        verify(roomRepository, times(1)).save(any(Room.class));
    }

    @Test
    void testUpdateRoom_NotFound() {
        Room updatedRoom = new Room();
        when(roomRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RoomNotFoundException.class, () -> roomService.updateRoom(1L, updatedRoom));
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
