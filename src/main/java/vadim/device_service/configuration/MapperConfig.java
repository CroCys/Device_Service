package vadim.device_service.configuration;

import org.mapstruct.factory.Mappers;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import vadim.device_service.mapper.DeviceMapper;

@Configuration
@EnableSpringDataWebSupport(pageSerializationMode = EnableSpringDataWebSupport.PageSerializationMode.VIA_DTO)
public class MapperConfig {

    @Bean
    public DeviceMapper deviceMapper() {
        return Mappers.getMapper(DeviceMapper.class);
    }
}
