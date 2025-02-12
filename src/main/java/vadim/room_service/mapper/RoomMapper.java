package vadim.room_service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import vadim.room_service.dto.RoomRequestDTO;
import vadim.room_service.dto.RoomResponseDTO;
import vadim.room_service.entity.Room;

@Mapper
public interface RoomMapper {
    RoomResponseDTO roomToRoomResponseDTO(Room room);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Room roomRequestDTOToRoom(RoomRequestDTO roomRequestDTO);
}
