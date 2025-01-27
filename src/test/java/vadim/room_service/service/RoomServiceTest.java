package vadim.room_service.service;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import vadim.room_service.entity.Room;
import vadim.room_service.repository.RoomRepository;

import java.time.LocalDateTime;
import java.util.Arrays;
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

    @Test
    void getAllRoomsTest() {
        List<Room> rooms = Arrays.asList(new Room(), new Room());
        when(roomRepository.findAll()).thenReturn(rooms);

        List<Room> result = roomService.getAllRooms();

        assertEquals(rooms.size(), result.size());
        verify(roomRepository, times(2)).findAll();
    }

    @Test
    void getRoomByIdTest() {
        Long id = 1L;
        Room room = new Room();
        room.setId(id);
        room.setCreatedAt(LocalDateTime.now());
        when(roomRepository.findById(id)).thenReturn(Optional.of(room));

        Room result = roomService.getRoomById(id);

        assertNotNull(result);
        assertEquals(id, result.getId());
        assertEquals(room.getCreatedAt(), result.getCreatedAt());
        verify(roomRepository, times(1)).findById(id);
    }

    @Test
    void getRoomByIdInvalidIdTest() {
        RuntimeException exception = assertThrows(RuntimeException.class, () -> roomService.getRoomById(-1L));
        assertEquals("Invalid room id " + -1, exception.getMessage());
    }

    @Test
    void getRoomByIdNotFoundTest() {
        Long id = 1L;
        when(roomRepository.findById(id)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> roomService.getRoomById(id));
        assertEquals("Room not found", exception.getMessage());
    }

    @Test
    void createRoomTest() {
        Room room = new Room();
        when(roomRepository.save(room)).thenReturn(room);

        Room result = roomService.createRoom(room);

        assertNotNull(result);
        verify(roomRepository, times(1)).save(room);
    }

    @Test
    void createRoomInvalidTest() {
        RuntimeException exception = assertThrows(RuntimeException.class, () -> roomService.createRoom(null));
        assertEquals("Invalid room", exception.getMessage());
    }

    @Test
    void updateRoomTest() {
        Long id = 1L;
        Room existingRoom = new Room();
        existingRoom.setId(id);
        Room updatedRoom = new Room();
        updatedRoom.setName("Updated Name");

        when(roomRepository.findById(id)).thenReturn(Optional.of(existingRoom));
        when(roomRepository.save(existingRoom)).thenReturn(existingRoom);

        Room result = roomService.updateRoom(id, updatedRoom);

        assertNotNull(result);
        assertEquals("Updated Name", result.getName());
        verify(roomRepository, times(2)).findById(id);
        verify(roomRepository, times(1)).save(existingRoom);
    }

    @Test
    void updateRoomInvalidIdTest() {
        RuntimeException exception = assertThrows(RuntimeException.class, () -> roomService.updateRoom(null, new Room()));
        assertEquals("Invalid room id " + null, exception.getMessage());
    }

    @Test
    void updateRoomInvalidRoomTest() {
        RuntimeException exception = assertThrows(RuntimeException.class, () -> roomService.updateRoom(1L, null));
        assertEquals("Room not found", exception.getMessage());
    }

    @Test
    void deleteRoomTest() {
        Long id = 1L;

        roomService.deleteRoom(id);

        verify(roomRepository, times(1)).deleteById(id);
    }

    @Test
    void deleteRoomInvalidIdTest() {
        RuntimeException exception = assertThrows(RuntimeException.class, () -> roomService.deleteRoom(null));
        assertEquals("Invalid room id " + null, exception.getMessage());
    }
}
