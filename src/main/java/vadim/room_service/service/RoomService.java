package vadim.room_service.service;

import jakarta.transaction.Transactional;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import vadim.room_service.dto.RoomRequestDTO;
import vadim.room_service.dto.RoomResponseDTO;
import vadim.room_service.entity.Room;
import vadim.room_service.exception.RoomNotFoundException;
import vadim.room_service.mapper.RoomMapper;
import vadim.room_service.repository.RoomRepository;

import java.math.BigDecimal;

@Service
public class RoomService {

    private final RoomRepository roomRepository;
    private final RoomMapper roomMapper;

    public RoomService(RoomRepository roomRepository, RoomMapper roomMapper) {
        this.roomRepository = roomRepository;
        this.roomMapper = roomMapper;
    }

    @Cacheable(value = "rooms", key = "#pageable.pageNumber + '-' + #pageable.pageSize")
    public Page<RoomResponseDTO> getAllRooms(Pageable pageable) {
        return roomRepository.findAll(pageable).map(roomMapper::roomToRoomResponseDTO);
    }

    public Page<RoomResponseDTO> getAllRooms(Pageable pageable, String name, Integer minSleepingPlaces, Integer maxSleepingPlaces, BigDecimal minPrice, BigDecimal maxPrice) {
        Specification<Room> spec = Specification.where(RoomSpecification.nameFilter(name));

        if (name != null) {
            spec = spec.and(RoomSpecification.nameFilter(name));
        }
        if (minSleepingPlaces != null) {
            spec = spec.and(RoomSpecification.minSleepingPlaces(minSleepingPlaces));
        }
        if (maxSleepingPlaces != null) {
            spec = spec.and(RoomSpecification.maxSleepingPlaces(maxSleepingPlaces));
        }
        if (minPrice != null) {
            spec = spec.and(RoomSpecification.minPrice(minPrice));
        }
        if (maxPrice != null) {
            spec = spec.and(RoomSpecification.maxPrice(maxPrice));
        }

        return roomRepository.findAll(spec, pageable).map(roomMapper::roomToRoomResponseDTO);
    }

    @Cacheable(value = "rooms", key = "#id")
    public RoomResponseDTO getRoomById(Long id) {
        return roomRepository.findById(id)
                .map(roomMapper::roomToRoomResponseDTO)
                .orElseThrow(() -> new RoomNotFoundException("Room not found with id " + id));
    }

    @CacheEvict(value = "rooms", allEntries = true)
    public RoomResponseDTO createRoom(RoomRequestDTO roomRequestDTO) {
        if (roomRequestDTO == null || roomRequestDTO.getName() == null || roomRequestDTO.getName().isEmpty() ||
                roomRequestDTO.getPrice() == null || roomRequestDTO.getSleepingPlaces() <= 0) {
            throw new IllegalArgumentException("Invalid room data");
        }

        Room room = roomMapper.roomRequestDTOToRoom(roomRequestDTO);
        Room savedRoom = roomRepository.save(room);

        return roomMapper.roomToRoomResponseDTO(savedRoom);
    }

    @Transactional
    public RoomResponseDTO updateRoom(Long id, RoomRequestDTO updatedRoomDTO) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Invalid room id " + id);
        }

        if (updatedRoomDTO == null) {
            throw new IllegalArgumentException("Invalid room data");
        }

        Room room = roomRepository.findById(id)
                .orElseThrow(() -> new RoomNotFoundException("No room found with id " + id));

        if (updatedRoomDTO.getName() != null && !updatedRoomDTO.getName().isEmpty()) {
            room.setName(updatedRoomDTO.getName());
        }
        if (updatedRoomDTO.getDescription() != null && !updatedRoomDTO.getDescription().isEmpty()) {
            room.setDescription(updatedRoomDTO.getDescription());
        }
        if (updatedRoomDTO.getPrice() != null && updatedRoomDTO.getPrice().compareTo(BigDecimal.ZERO) > 0) {
            room.setPrice(updatedRoomDTO.getPrice());
        }
        if (updatedRoomDTO.getSleepingPlaces() != null && updatedRoomDTO.getSleepingPlaces() > 0) {
            room.setSleepingPlaces(updatedRoomDTO.getSleepingPlaces());
        }

        Room savedRoom = roomRepository.save(room);
        return roomMapper.roomToRoomResponseDTO(savedRoom);
    }

    @CacheEvict(value = "rooms", key = "#id")
    public void deleteRoom(Long id) {
        if (!roomRepository.existsById(id)) {
            throw new RoomNotFoundException("No room found with id " + id);
        }

        roomRepository.deleteById(id);
    }
}
