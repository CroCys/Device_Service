package vadim.room_service.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class RoomResponseDTO {
    private Long id;
    private String name;
    private Integer sleepingPlaces;
    private String description;
    private BigDecimal price;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public RoomResponseDTO() {
    }

    public RoomResponseDTO(Long id, String name, Integer sleepingPlaces, String description, BigDecimal price, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.name = name;
        this.sleepingPlaces = sleepingPlaces;
        this.description = description;
        this.price = price;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getSleepingPlaces() {
        return sleepingPlaces;
    }

    public void setSleepingPlaces(Integer sleepingPlaces) {
        this.sleepingPlaces = sleepingPlaces;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
