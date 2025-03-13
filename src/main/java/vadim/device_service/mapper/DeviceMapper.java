package vadim.device_service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import vadim.device_service.dto.DeviceRequestDTO;
import vadim.device_service.dto.DeviceResponseDTO;
import vadim.device_service.entity.Device;

@Mapper(componentModel = "spring", uses = {ImageMapper.class})
public interface DeviceMapper {

    @Mapping(target = "images", ignore = true)
    Device toEntity(DeviceRequestDTO dto);

    DeviceResponseDTO toDto(Device entity);
}
