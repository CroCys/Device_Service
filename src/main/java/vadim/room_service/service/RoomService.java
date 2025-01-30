package vadim.room_service.service;

import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import vadim.room_service.entity.Room;
import vadim.room_service.exception.RoomNotFoundException;
import vadim.room_service.repository.RoomRepository;

@Service
public class RoomService {

    private final RoomRepository roomRepository;

    public RoomService(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    public Page<Room> getAllRooms(Pageable pageable) {
        return roomRepository.findAll(pageable);
    }

    public Room getRoomById(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Invalid room id " + id);
        }

        return roomRepository.findById(id).orElseThrow(() -> new RoomNotFoundException("Room not found with id " + id));
    }

    public Room createRoom(Room room) {
        if (room == null || room.getName() == null || room.getName().isEmpty() ||
                room.getPrice() == null || room.getIsAvailable() == null) {
            throw new IllegalArgumentException("Invalid room");
        }

        return roomRepository.save(room);
    }

    @Transactional
    public Room updateRoom(Long id, Room updatedRoom) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Invalid room id " + id);
        }

        if (updatedRoom == null) {
            throw new IllegalArgumentException("Invalid room");
        }

        Room room = roomRepository.findById(id)
                .orElseThrow(() -> new RoomNotFoundException("No room found with id " + id));

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

        if (!roomRepository.existsById(id)) {
            throw new RoomNotFoundException("No room found with id " + id);
        }

        roomRepository.deleteById(id);
    }
}
