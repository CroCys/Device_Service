package vadim.device_service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import vadim.device_service.dto.DeviceRequestDTO;
import vadim.device_service.dto.DeviceResponseDTO;
import vadim.device_service.entity.Device;

@Mapper
public interface DeviceMapper {
    DeviceResponseDTO deviceToDeviceResponseDTO(Device device);

    @Mapping(target = "id", ignore = true)
    Device deviceRequestDTOToDevice(DeviceRequestDTO deviceRequestDTO);
}
