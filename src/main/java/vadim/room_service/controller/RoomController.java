package vadim.room_service.controller;

import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vadim.room_service.dto.RoomRequestDTO;
import vadim.room_service.dto.RoomResponseDTO;
import vadim.room_service.service.RoomService;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/v1/rooms")
public class RoomController {

    private final RoomService roomService;


    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    @GetMapping
    public ResponseEntity<Page<RoomResponseDTO>> getAllRooms(Pageable pageable) {
        Page<RoomResponseDTO> rooms = roomService.getAllRooms(pageable);
        return ResponseEntity.ok(rooms);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<RoomResponseDTO>> getAllRooms(
            Pageable pageable,
            @RequestParam(required = false) String name,
            @RequestParam(name = "minsleepingplaces", required = false) Integer minSleepingPlaces,
            @RequestParam(name = "maxsleepingplaces", required = false) Integer maxSleepingPlaces,
            @RequestParam(name = "minprice", required = false) BigDecimal minPrice,
            @RequestParam(name = "maxprice", required = false) BigDecimal maxPrice) {

        Page<RoomResponseDTO> rooms = roomService.getAllRooms(pageable, name, minSleepingPlaces, maxSleepingPlaces, minPrice, maxPrice);
        return ResponseEntity.ok(rooms);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RoomResponseDTO> getRoomById(@PathVariable Long id) {
        RoomResponseDTO room = roomService.getRoomById(id);
        return ResponseEntity.ok(room);
    }

    @PostMapping
    public ResponseEntity<RoomResponseDTO> createRoom(@Valid @RequestBody RoomRequestDTO roomRequestDTO) {
        RoomResponseDTO savedRoom = roomService.createRoom(roomRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedRoom);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RoomResponseDTO> updateRoom(@PathVariable Long id, @Valid @RequestBody RoomRequestDTO updatedRoomDTO) {
        RoomResponseDTO updatedRoom = roomService.updateRoom(id, updatedRoomDTO);
        return ResponseEntity.ok(updatedRoom);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRoom(@PathVariable Long id) {
        roomService.deleteRoom(id);
        return ResponseEntity.noContent().build();
    }

}
