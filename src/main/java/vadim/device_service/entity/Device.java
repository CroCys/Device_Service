package vadim.device_service.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "devices")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Device {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "The device name cannot be empty")
    @Column(nullable = false)
    private String name;

    @NotBlank(message = "The device brand cannot be empty")
    @Column(nullable = false)
    private String brand;

    @NotNull(message = "The category cannot be empty")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Category category;

    @Size(max = 500, message = "Description should not exceed 500 characters")
    @Column(length = 500)
    private String description;

    @NotNull(message = "Price cannot be empty")
    @DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than 0")
    @Column(nullable = false)
    private BigDecimal price;

    @PastOrPresent(message = "Release date cannot be in the future")
    private LocalDate releaseDate;

//    @Pattern(regexp = "^(http|https)://.*\\.(png|jpg|jpeg)$", message = "Image URL must be a valid link to a PNG, JPG, or JPEG file")
    private String imageUrl;

    @DecimalMin(value = "0.0", message = "Rating must be at least 0")
    @DecimalMax(value = "10.0", message = "Rating cannot be more than 10")
    private BigDecimal averageRating;
}
