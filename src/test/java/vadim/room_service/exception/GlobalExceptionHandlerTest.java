package vadim.room_service.exception;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class GlobalExceptionHandlerTest {

    private GlobalExceptionHandler globalExceptionHandler;

    @BeforeEach
    void setUp() {
        globalExceptionHandler = new GlobalExceptionHandler();
    }

    @Test
    void testIllegalArgumentExceptionHandler() {
        IllegalArgumentException exception = new IllegalArgumentException("Invalid argument");

        ResponseEntity<ErrorResponse> response = globalExceptionHandler.handleIllegalArgumentException(exception);

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Invalid request", response.getBody().getError());
        assertEquals("Invalid argument", response.getBody().getMessage());
        assertEquals(HttpStatus.BAD_REQUEST, response.getBody().getStatus());
        assertNotNull(response.getBody().getTimestamp());
    }

    @Test
    void testRoomNotFoundExceptionHandler() {
        RoomNotFoundException exception = new RoomNotFoundException("Room not found");

        ResponseEntity<ErrorResponse> response = globalExceptionHandler.handleRoomNotFoundException(exception);

        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Room not found", response.getBody().getError());
        assertEquals("Room not found", response.getBody().getMessage());
        assertEquals(HttpStatus.NOT_FOUND, response.getBody().getStatus());
        assertNotNull(response.getBody().getTimestamp());
    }

    @Test
    void testRuntimeExceptionHandler() {
        RuntimeException exception = new RuntimeException("Internal server error");

        ResponseEntity<ErrorResponse> response = globalExceptionHandler.handleRuntimeException(exception);

        assertNotNull(response);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Internal server error", response.getBody().getError());
        assertEquals("Internal server error", response.getBody().getMessage());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getBody().getStatus());
        assertNotNull(response.getBody().getTimestamp());
    }

    @Test
    void testGenericExceptionHandler() {
        Exception exception = new Exception("Unexpected exception");

        ResponseEntity<ErrorResponse> response = globalExceptionHandler.handleGenericException(exception);

        assertNotNull(response);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Unexpected error", response.getBody().getError());
        assertEquals("Unexpected exception", response.getBody().getMessage());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getBody().getStatus());
        assertNotNull(response.getBody().getTimestamp());
    }
}