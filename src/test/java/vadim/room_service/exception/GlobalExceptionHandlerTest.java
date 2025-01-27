package vadim.room_service.exception;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


public class GlobalExceptionHandlerTest {

    private GlobalExceptionHandler globalExceptionHandler;

    @BeforeEach
    void setUp() {
        globalExceptionHandler = new GlobalExceptionHandler();
    }

    @Test
    void testIllegalArgumentExceptionHandler() {
        IllegalArgumentException exception = new IllegalArgumentException("Invalid argument");

        ResponseEntity<String> response = globalExceptionHandler.illegalArgumentExceptionHandler(exception);

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Invalid argument", response.getBody());
    }

    @Test
    void testRoomNotFoundExceptionHandler() {
        RoomNotFoundException exception = new RoomNotFoundException("Room not found");

        ResponseEntity<String> response = globalExceptionHandler.roomNotFoundExceptionHandler(exception);

        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Room not found", response.getBody());
    }

    @Test
    void testRuntimeExceptionHandler() {
        RuntimeException exception = new RuntimeException("Internal server error");

        ResponseEntity<String> response = globalExceptionHandler.runtimeExceptionHandler(exception);

        assertNotNull(response);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Internal server error", response.getBody());
    }
}
