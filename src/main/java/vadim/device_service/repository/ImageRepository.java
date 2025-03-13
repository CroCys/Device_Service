package vadim.device_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vadim.device_service.entity.Image;

import java.util.List;

public interface ImageRepository extends JpaRepository<Image, Long> {

    List<Image> findAllByDeviceId(Long id);
}
