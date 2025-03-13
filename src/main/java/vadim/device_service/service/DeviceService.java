package vadim.device_service.service;

import jakarta.transaction.Transactional;
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

@Service
public class DeviceService {

    private final DeviceRepository deviceRepository;
    private final DeviceMapper deviceMapper;

    public DeviceService(DeviceRepository deviceRepository, DeviceMapper deviceMapper) {
        this.deviceRepository = deviceRepository;
        this.deviceMapper = deviceMapper;
    }

    @Cacheable(value = "devices", key = "#pageable.pageNumber + '-' + #pageable.pageSize")
    public Page<DeviceResponseDTO> getAllDevices(Pageable pageable) {
        return deviceRepository.findAll(pageable).map(deviceMapper::toDto);
    }

    public Page<DeviceResponseDTO> getAllDevices(Pageable pageable, String name, String brand, Category category,
                                                 LocalDate minReleaseDate, LocalDate maxReleaseDate,
                                                 BigDecimal minRating, BigDecimal maxRating) {
        Specification<Device> spec = Specification.where(null);

        if (name != null && !name.isBlank()) {
            spec = spec.and(DeviceSpecification.nameFilter(name));
        }
        if (brand != null && !brand.isBlank()) {
            spec = spec.and(DeviceSpecification.brandFilter(brand));
        }
        if (category != null) {
            spec = spec.and(DeviceSpecification.categoryFilter(category));
        }
        if (minReleaseDate != null) {
            spec = spec.and(DeviceSpecification.minReleaseDate(minReleaseDate));
        }
        if (maxReleaseDate != null) {
            spec = spec.and(DeviceSpecification.maxReleaseDate(maxReleaseDate));
        }
        if (minRating != null) {
            spec = spec.and(DeviceSpecification.minRating(minRating));
        }
        if (maxRating != null) {
            spec = spec.and(DeviceSpecification.maxRating(maxRating));
        }

        return deviceRepository.findAll(spec, pageable).map(deviceMapper::toDto);
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
        Device savedDevice = deviceRepository.save(device);

        return deviceMapper.toDto(savedDevice);
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

        if (updatedDeviceDTO.getName() != null && !updatedDeviceDTO.getName().isBlank()) {
            device.setName(updatedDeviceDTO.getName());
        }
        if (updatedDeviceDTO.getBrand() != null && !updatedDeviceDTO.getBrand().isBlank()) {
            device.setBrand(updatedDeviceDTO.getBrand());
        }
        if (updatedDeviceDTO.getCategory() != null) {
            device.setCategory(updatedDeviceDTO.getCategory());
        }
        if (updatedDeviceDTO.getDescription() != null && !updatedDeviceDTO.getDescription().isBlank()) {
            device.setDescription(updatedDeviceDTO.getDescription());
        }
        if (updatedDeviceDTO.getReleaseDate() != null && !updatedDeviceDTO.getReleaseDate().isAfter(LocalDate.now())) {
            device.setReleaseDate(updatedDeviceDTO.getReleaseDate());
        }

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
}
