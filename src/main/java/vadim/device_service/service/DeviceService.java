package vadim.device_service.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import vadim.device_service.dto.DeviceRequestDTO;
import vadim.device_service.dto.DeviceResponseDTO;
import vadim.device_service.entity.Category;
import vadim.device_service.entity.Device;
import vadim.device_service.exception.DeviceNotFoundException;
import vadim.device_service.mapper.DeviceMapper;
import vadim.device_service.repository.DeviceRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DeviceService {

    private final DeviceRepository deviceRepository;
    private final DeviceMapper deviceMapper;

    @Cacheable(value = "devices", key = "'page=' + #pageable.pageNumber + '&size=' + #pageable.pageSize")
    public Page<DeviceResponseDTO> getAllDevices(Pageable pageable) {
        return deviceRepository.findAll(pageable).map(deviceMapper::toDto);
    }

    @Cacheable(value = "devices", key = "'page=' + #pageable.pageNumber + '&size=' + #pageable.pageSize + '&name=' + #name + '&brand=' + #brand + '&category=' + #category + '&minDate=' + #minReleaseDate + '&maxDate=' + #maxReleaseDate + '&minRating=' + #minRating + '&maxRating=' + #maxRating")
    public Page<DeviceResponseDTO> getAllDevices(Pageable pageable, String name, String brand, Category category,
                                                 LocalDate minReleaseDate, LocalDate maxReleaseDate,
                                                 BigDecimal minRating, BigDecimal maxRating) {

        List<Specification<Device>> specs = new ArrayList<>();
        if (name != null && !name.isBlank()) {
            specs.add(DeviceSpecification.nameFilter(name));
        }
        if (brand != null && !brand.isBlank()) {
            specs.add(DeviceSpecification.brandFilter(brand));
        }
        if (category != null) {
            specs.add(DeviceSpecification.categoryFilter(category));
        }
        if (minReleaseDate != null) {
            specs.add(DeviceSpecification.minReleaseDate(minReleaseDate));
        }
        if (maxReleaseDate != null) {
            specs.add(DeviceSpecification.maxReleaseDate(maxReleaseDate));
        }
        if (minRating != null) {
            specs.add(DeviceSpecification.minRating(minRating));
        }
        if (maxRating != null) {
            specs.add(DeviceSpecification.maxRating(maxRating));
        }
        Specification<Device> finalSpec = null;
        for (Specification<Device> spec : specs) {
            if (finalSpec == null) {
                finalSpec = spec;
            } else {
                finalSpec = finalSpec.and(spec);
            }
        }
        return deviceRepository.findAll(finalSpec, pageable).map(deviceMapper::toDto);
    }

    @Cacheable(value = "devices", key = "#id")
    public DeviceResponseDTO getDeviceById(Long id) {
        return deviceRepository.findById(id)
                .map(deviceMapper::toDto)
                .orElseThrow(() -> new DeviceNotFoundException("Device not found with id " + id));
    }

    @CacheEvict(value = "devices", allEntries = true)
    public DeviceResponseDTO createDevice(DeviceRequestDTO deviceRequestDTO) {
        Device device = deviceMapper.toEntity(deviceRequestDTO);
        device = deviceRepository.save(device);
        return deviceMapper.toDto(device);
    }

    @Transactional
    @CacheEvict(value = "devices", key = "#id")
    public DeviceResponseDTO updateDevice(Long id, DeviceRequestDTO updatedDeviceDTO) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Invalid device id " + id);
        }
        if (updatedDeviceDTO == null) {
            throw new IllegalArgumentException("Invalid device data");
        }
        Device device = deviceRepository.findById(id)
                .orElseThrow(() -> new DeviceNotFoundException("No device found with id " + id));
        device.setName(updatedDeviceDTO.getName());
        device.setBrand(updatedDeviceDTO.getBrand());
        device.setCategory(updatedDeviceDTO.getCategory());
        device.setDescription(updatedDeviceDTO.getDescription());
        device.setReleaseDate(updatedDeviceDTO.getReleaseDate());
        Device savedDevice = deviceRepository.save(device);
        return deviceMapper.toDto(savedDevice);
    }

    @CacheEvict(value = "devices", key = "#id")
    public void deleteDevice(Long id) {
        if (!deviceRepository.existsById(id)) {
            throw new DeviceNotFoundException("No device found with id " + id);
        }
        deviceRepository.deleteById(id);
    }

    void updateDeviceRating(Long id, BigDecimal newRating) {
        Device device = deviceRepository.findById(id)
                .orElseThrow(() -> new DeviceNotFoundException("No device found with id " + id));
        device.setAverageRating(newRating);
        deviceRepository.save(device);
    }
}
