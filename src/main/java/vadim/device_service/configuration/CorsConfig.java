package vadim.device_service.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CorsConfig {

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();

        // Разрешаем конкретные источники
        config.addAllowedOrigin("http://localhost:5500"); // Для Live Server
        config.addAllowedOrigin("http://127.0.0.1:5500"); // Альтернативный URL для Live Server

        // Разрешаем все заголовки
        config.addAllowedHeader("*");

        // Разрешаем все методы
        config.addAllowedMethod("*");

        // Разрешаем передачу учетных данных
        config.setAllowCredentials(true);

        // Применяем конфигурацию ко всем URL
        source.registerCorsConfiguration("/**", config);

        return new CorsFilter(source);
    }
}
