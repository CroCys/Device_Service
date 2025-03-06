package vadim.device_service.controller;

import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vadim.device_service.dto.DeviceRequestDTO;
import vadim.device_service.dto.DeviceResponseDTO;
import vadim.device_service.entity.Category;
import vadim.device_service.service.DeviceService;

import java.math.BigDecimal;
import java.time.LocalDate;

@RestController
@RequestMapping("/api/v1")
public class DeviceController {

    private final DeviceService deviceService;

    public DeviceController(DeviceService deviceService) {
        this.deviceService = deviceService;
    }

    @GetMapping("/devices")
    public ResponseEntity<Page<DeviceResponseDTO>> getAllDevices(Pageable pageable) {
        Page<DeviceResponseDTO> devices = deviceService.getAllDevices(pageable);
        return ResponseEntity.ok(devices);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<DeviceResponseDTO>> searchDevices(
            Pageable pageable,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String brand,
            @RequestParam(required = false) Category category,
            @RequestParam(name = "minprice", required = false) BigDecimal minPrice,
            @RequestParam(name = "maxprice", required = false) BigDecimal maxPrice,
            @RequestParam(name = "minrelease", required = false) LocalDate minReleaseDate,
            @RequestParam(name = "maxrelease", required = false) LocalDate maxReleaseDate,
            @RequestParam(name = "minrating", required = false) BigDecimal minRating,
            @RequestParam(name = "maxrating", required = false) BigDecimal maxRating) {

        Page<DeviceResponseDTO> devices = deviceService.getAllDevices(pageable, name, brand, category, minPrice, maxPrice, minReleaseDate, maxReleaseDate, minRating, maxRating);
        return ResponseEntity.ok(devices);
    }

    @GetMapping("/device/{id}")
    public ResponseEntity<DeviceResponseDTO> getDeviceById(@PathVariable Long id) {
        DeviceResponseDTO device = deviceService.getDeviceById(id);
        return ResponseEntity.ok(device);
    }

    @PostMapping("/create")
    public ResponseEntity<DeviceResponseDTO> createDevice(@Valid @RequestBody DeviceRequestDTO deviceRequestDTO) {
        DeviceResponseDTO savedDevice = deviceService.createDevice(deviceRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedDevice);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<DeviceResponseDTO> updateDevice(@PathVariable Long id, @Valid @RequestBody DeviceRequestDTO updatedDeviceDTO) {
        DeviceResponseDTO updatedDevice = deviceService.updateDevice(id, updatedDeviceDTO);
        return ResponseEntity.ok(updatedDevice);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteDevice(@PathVariable Long id) {
        deviceService.deleteDevice(id);
        return ResponseEntity.noContent().build();
    }
}
