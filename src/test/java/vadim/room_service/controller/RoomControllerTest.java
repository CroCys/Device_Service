package vadim.room_service.controller;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import vadim.room_service.dto.RoomRequestDTO;
import vadim.room_service.dto.RoomResponseDTO;
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
        List<RoomResponseDTO> mockRooms = List.of(
                new RoomResponseDTO(1L, "Room 1", 2, "empty",
                        BigDecimal.valueOf(100)),
                new RoomResponseDTO(2L, "Room 2", 4, "empty",
                        BigDecimal.valueOf(150)));

        Page<RoomResponseDTO> page = new PageImpl<>(mockRooms, PageRequest.of(0, 10), mockRooms.size());
        when(roomService.getAllRooms(PageRequest.of(0, 10))).thenReturn(page);

        ResponseEntity<Page<RoomResponseDTO>> response = roomController.getAllRooms(PageRequest.of(0, 10));

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(page, response.getBody());

        verify(roomService, times(1)).getAllRooms(PageRequest.of(0, 10));
    }

    @Test
    void getRoomByIdTest() {
        RoomResponseDTO mockRoom = new RoomResponseDTO(1L, "Room 1", 2, "empty",
                BigDecimal.valueOf(100));
        when(roomService.getRoomById(mockRoom.getId())).thenReturn(mockRoom);

        ResponseEntity<RoomResponseDTO> response = roomController.getRoomById(mockRoom.getId());

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockRoom, response.getBody());

        verify(roomService, times(1)).getRoomById(mockRoom.getId());
    }

    @Test
    void createRoomTest() {
        RoomRequestDTO roomRequestDTO = new RoomRequestDTO("Room 1", 2, "empty", BigDecimal.valueOf(100));
        RoomResponseDTO mockRoom = new RoomResponseDTO(1L, "Room 1", 2, "empty",
                BigDecimal.valueOf(100));

        when(roomService.createRoom(roomRequestDTO)).thenReturn(mockRoom);

        ResponseEntity<RoomResponseDTO> response = roomController.createRoom(roomRequestDTO);

        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(mockRoom, response.getBody());

        verify(roomService, times(1)).createRoom(roomRequestDTO);
    }

    @Test
    void updateRoomTest() {
        Long roomId = 1L;
        RoomRequestDTO updatedRoomDTO = new RoomRequestDTO("Updated Room", 3, "empty", BigDecimal.valueOf(120));
        RoomResponseDTO updatedRoom = new RoomResponseDTO(roomId, "Updated Room", 3, "empty",
                BigDecimal.valueOf(120));

        when(roomService.updateRoom(roomId, updatedRoomDTO)).thenReturn(updatedRoom);

        ResponseEntity<RoomResponseDTO> response = roomController.updateRoom(roomId, updatedRoomDTO);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(updatedRoom, response.getBody());

        verify(roomService, times(1)).updateRoom(roomId, updatedRoomDTO);
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
