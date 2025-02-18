package vadim.room_service.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    // 400 - Некорректные аргументы
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgumentException(IllegalArgumentException ex) {
        logger.error("IllegalArgumentException: {}", ex.getMessage());
        ErrorResponse errorResponse = new ErrorResponse("Invalid request", ex.getMessage(),
                HttpStatus.BAD_REQUEST, LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    // 404 - Комната не найдена
    @ExceptionHandler(RoomNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleRoomNotFoundException(RoomNotFoundException ex) {
        logger.error("RoomNotFoundException: {}", ex.getMessage());
        ErrorResponse errorResponse = new ErrorResponse("Room not found", ex.getMessage(),
                HttpStatus.NOT_FOUND, LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    // 500 - Все остальные исключения
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponse> handleRuntimeException(RuntimeException ex) {
        logger.error("RuntimeException: {}", ex.getMessage());
        ErrorResponse errorResponse = new ErrorResponse("Internal server error", ex.getMessage(),
                HttpStatus.INTERNAL_SERVER_ERROR, LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }

    // Общий обработчик для всех непредусмотренных исключений
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(Exception ex) {
        logger.error("Exception: {}", ex.getMessage());
        ErrorResponse errorResponse = new ErrorResponse("Unexpected error", ex.getMessage(),
                HttpStatus.INTERNAL_SERVER_ERROR, LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }
}

