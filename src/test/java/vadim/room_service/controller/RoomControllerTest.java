package vadim.room_service.controller;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import vadim.room_service.entity.Room;
import vadim.room_service.service.RoomService;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;


@SpringBootTest
public class RoomControllerTest {

    @Mock
    private RoomService roomService;

    @InjectMocks
    private RoomController roomController;

    @Test
    void getAllRoomsTest() {
        List<Room> mockRooms = List.of(new Room(), new Room());
        when(roomService.getAllRooms()).thenReturn(mockRooms);

        ResponseEntity<List<Room>> response = roomController.getAllRooms();

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockRooms, response.getBody());

        verify(roomService, times(1)).getAllRooms();
    }

    @Test
    void getRoomByIdTest() {
        Room mockRoom = new Room();
        mockRoom.setId(1L);
        when(roomService.getRoomById(mockRoom.getId())).thenReturn(mockRoom);

        ResponseEntity<Room> response = roomController.getRoomById(mockRoom.getId());

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockRoom, response.getBody());

        verify(roomService, times(1)).getRoomById(mockRoom.getId());
    }

    @Test
    void createRoomTest() {
        Room mockRoom = new Room();

        when(roomService.createRoom(mockRoom)).thenReturn(mockRoom);

        ResponseEntity<Room> response = roomController.createRoom(mockRoom);

        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(mockRoom, response.getBody());

        verify(roomService, times(1)).createRoom(mockRoom);
    }

    @Test
    void updateRoomTest() {
        Long roomId = 1L;
        Room updatedRoom = new Room();
        updatedRoom.setId(roomId);
        updatedRoom.setName("Updated Room");
        updatedRoom.setPrice(BigDecimal.valueOf(100));

        when(roomService.updateRoom(roomId, updatedRoom)).thenReturn(updatedRoom);

        ResponseEntity<Room> response = roomController.updateRoom(roomId, updatedRoom);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(updatedRoom, response.getBody());

        verify(roomService, times(1)).updateRoom(roomId, updatedRoom);
    }

    @Test
    void deleteRoomTest() {
        Long roomId = 1L;

        doNothing().when(roomService).deleteRoom(roomId);

        ResponseEntity<Void> response = roomController.deleteRoom(roomId);

        assertNotNull(response);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());

        verify(roomService, times(1)).deleteRoom(roomId);
    }
}
