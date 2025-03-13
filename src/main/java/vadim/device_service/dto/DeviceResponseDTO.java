package vadim.device_service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vadim.device_service.entity.Category;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DeviceResponseDTO implements Serializable {
    private Long id;
    private String name;
    private String brand;
    private Category category;
    private String description;
    private LocalDate releaseDate;
    private Set<ImageResponseDTO> images;
    private BigDecimal averageRating;
}
