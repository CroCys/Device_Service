package vadim.device_service.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.web.config.PageableHandlerMethodArgumentResolverCustomizer;

@Configuration
public class PageableConfig {

    @Bean
    public PageableHandlerMethodArgumentResolverCustomizer customizePagination() {
        return resolver -> {
            resolver.setMaxPageSize(100); // Максимальное кол-во записей на страницу
            resolver.setOneIndexedParameters(true); // Можно делать нумерацию страниц с 1
            resolver.setFallbackPageable(org.springframework.data.domain.PageRequest.of(0, 25)); // Размер страницы по умолчанию
        };
    }
}
