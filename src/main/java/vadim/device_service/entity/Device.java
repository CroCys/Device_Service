package vadim.device_service.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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

    @PastOrPresent(message = "Release date cannot be in the future")
    private LocalDate releaseDate;

    @OneToMany(mappedBy = "device", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<Image> images = new ArrayList<>();

    @DecimalMin(value = "0.0", message = "Rating must be at least 0")
    @DecimalMax(value = "10.0", message = "Rating cannot be more than 10")
    private BigDecimal averageRating;
}
