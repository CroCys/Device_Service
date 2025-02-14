package vadim.room_service.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;

@Entity
@Table(name = "rooms")
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "The room name cannot be empty")
    @Column(nullable = false)
    private String name;

    @NotNull(message = "Sleeping places is required")
    @Min(value = 1, message = "Sleeping places cannot be 0 or below")
    @Column(name = "sleeping_places", nullable = false)
    private Integer sleepingPlaces;

    @Size(max = 500, message = "Description should not exceed 500 characters")
    @Column(length = 500)
    private String description;

    @NotNull(message = "Price is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than 0")
    @Column(nullable = false)
    private BigDecimal price;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public @NotBlank(message = "The room name cannot be empty") String getName() {
        return name;
    }

    public void setName(@NotBlank(message = "The room name cannot be empty") String name) {
        this.name = name;
    }

    @NotNull(message = "Sleeping places is required")
    @Min(value = 1, message = "Sleeping places cannot be 0 or below")
    public Integer getSleepingPlaces() {
        return sleepingPlaces;
    }

    public void setSleepingPlaces(@NotNull(message = "Sleeping places is required") @Min(value = 1, message = "Sleeping places cannot be 0 or below") Integer sleepingPlaces) {
        this.sleepingPlaces = sleepingPlaces;
    }

    public @Size(max = 500, message = "Description should not exceed 500 characters") String getDescription() {
        return description;
    }

    public void setDescription(@Size(max = 500, message = "Description should not exceed 500 characters") String description) {
        this.description = description;
    }

    public @NotNull(message = "Price is required") @DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than 0") BigDecimal getPrice() {
        return price;
    }

    public void setPrice(@NotNull(message = "Price is required") @DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than 0") BigDecimal price) {
        this.price = price;
    }
}
