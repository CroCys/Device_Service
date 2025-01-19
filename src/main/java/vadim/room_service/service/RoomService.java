package vadim.room_service.service;

import org.springframework.stereotype.Service;
import vadim.room_service.entity.Room;
import vadim.room_service.repository.RoomRepository;

import java.util.List;

@Service
public class RoomService {

    private final RoomRepository roomRepository;

    public RoomService(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    public List<Room> getAllRooms() {
        try {
            return roomRepository.findAll();
        } catch (RuntimeException e) {
            throw new RuntimeException("Error while retrieving all rooms", e);
        }
    }

    public Room getRoomById(Long id) {
        if (id == null || id <= 0) {
            throw new RuntimeException("Invalid room id");
        }

        try {
            return roomRepository.findById(id).orElseThrow(() -> new RuntimeException("Room not found"));
        } catch (RuntimeException e) {
            throw new RuntimeException("Error while retrieving room with id: " + id, e);
        }
    }

    public Room createRoom(Room room) {
        if (room == null) {
            throw new RuntimeException("Invalid room");
        }

        try {
            return roomRepository.save(room);
        } catch (RuntimeException e) {
            throw new RuntimeException("Error while creating room", e);
        }
    }

    public Room updateRoom(Long id, Room updatedRoom) {
        if (id == null || id <= 0) {
            throw new RuntimeException("Invalid room id");
        }

        if (updatedRoom == null) {
            throw new RuntimeException("Invalid room");
        }

        try {
            Room room = getRoomById(id);
            room.setName(updatedRoom.getName());
            room.setDescription(updatedRoom.getDescription());
            room.setPrice(updatedRoom.getPrice());
            room.setIsAvailable(updatedRoom.getIsAvailable());
            room.setUpdatedAt(updatedRoom.getUpdatedAt());
            return roomRepository.save(room);
        } catch (RuntimeException e) {
            throw new RuntimeException("Error while updating room", e);
        }
    }

    public void deleteRoom(Long id) {
        if (id == null || id <= 0) {
            throw new RuntimeException("Invalid room id");
        }

        try {
            roomRepository.deleteById(id);
        } catch (RuntimeException e) {
            throw new RuntimeException("Error while deleting room with id: " + id, e);
        }
    }
}
