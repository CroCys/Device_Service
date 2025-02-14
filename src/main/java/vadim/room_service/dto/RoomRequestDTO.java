package vadim.room_service.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class RoomRequestDTO implements Serializable {
    private String name;
    private Integer sleepingPlaces;
    private String description;
    private BigDecimal price;

    public RoomRequestDTO() {
    }

    public RoomRequestDTO(String name, Integer sleepingPlaces, String description, BigDecimal price) {
        this.name = name;
        this.sleepingPlaces = sleepingPlaces;
        this.description = description;
        this.price = price;
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
}
