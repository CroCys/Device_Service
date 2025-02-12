package vadim.room_service.configuration;

import org.mapstruct.factory.Mappers;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import vadim.room_service.mapper.RoomMapper;

@Configuration
@EnableSpringDataWebSupport(pageSerializationMode = EnableSpringDataWebSupport.PageSerializationMode.VIA_DTO)
public class MapperConfig {

    @Bean
    public RoomMapper roomMapper() {
        return Mappers.getMapper(RoomMapper.class);
    }
}
