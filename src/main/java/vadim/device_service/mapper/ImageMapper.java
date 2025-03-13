package vadim.device_service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import vadim.device_service.dto.ImageResponseDTO;
import vadim.device_service.entity.Image;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ImageMapper {

    @Mapping(source = "device.id", target = "deviceId")
    ImageResponseDTO toDTO(Image image);

    List<ImageResponseDTO> toDTOSet(List<Image> images);
}
