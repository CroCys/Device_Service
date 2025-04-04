package vadim.device_service.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vadim.device_service.entity.Category;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DeviceRequestDTO implements Serializable {

    @NotBlank(message = "The device name cannot be empty")
    private String name;

    @NotBlank(message = "The device brand cannot be empty")
    private String brand;

    @NotNull(message = "The category cannot be empty")
    private Category category;

    @Size(max = 500, message = "Description should not exceed 500 characters")
    private String description;

    @PastOrPresent(message = "Release date cannot be in the future")
    private LocalDate releaseDate;

    @NotNull(message = "Rating cannot be null")
    @DecimalMin(value = "0.0", inclusive = true, message = "Rating must be at least 0.0")
    @DecimalMax(value = "10.0", inclusive = true, message = "Rating must be at most 10.0")
    @Digits(integer = 2, fraction = 1, message = "Rating must have up to 1 decimal place")
    private BigDecimal averageRating;
}
