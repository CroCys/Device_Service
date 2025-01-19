package vadim.room_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vadim.room_service.entity.Room;

public interface RoomRepository extends JpaRepository<Room, Long> {
}
