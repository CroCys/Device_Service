package vadim.device_service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vadim.device_service.entity.Category;

import java.io.Serializable;
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
}
