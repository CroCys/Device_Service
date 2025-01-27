package vadim.room_service.service;

import org.springframework.stereotype.Service;
import vadim.room_service.entity.Room;
import vadim.room_service.exception.RoomNotFoundException;
import vadim.room_service.repository.RoomRepository;

import java.util.List;

@Service
public class RoomService {

    private final RoomRepository roomRepository;

    public RoomService(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    public List<Room> getAllRooms() {
        if (roomRepository.findAll().isEmpty()) {
            throw new RuntimeException("No rooms found");
        }

        return roomRepository.findAll();
    }

    public Room getRoomById(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Invalid room id " + id);
        }

        return roomRepository.findById(id).orElseThrow(() -> new RoomNotFoundException("Room not found"));
    }

    public Room createRoom(Room room) {
        if (room == null) {
            throw new IllegalArgumentException("Invalid room");
        }

        return roomRepository.save(room);
    }

    public Room updateRoom(Long id, Room updatedRoom) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Invalid room id " + id);
        }

        if (getRoomById(id) == null) {
            throw new RoomNotFoundException("No room found with id " + id);
        }

        if (updatedRoom == null) {
            throw new IllegalArgumentException("Invalid room");
        }

        Room room = getRoomById(id);
        room.setName(updatedRoom.getName());
        room.setDescription(updatedRoom.getDescription());
        room.setPrice(updatedRoom.getPrice());
        room.setIsAvailable(updatedRoom.getIsAvailable());
        room.setUpdatedAt(updatedRoom.getUpdatedAt());
        return roomRepository.save(room);
    }

    public void deleteRoom(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Invalid room id " + id);
        }

        roomRepository.deleteById(id);
    }
}
