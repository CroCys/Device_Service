package vadim.device_service.service;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import vadim.device_service.dto.RatingUpdateDTO;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class RatingKafkaConsumer {

    private final DeviceService deviceService;

    @KafkaListener(topics = "rating_updates", groupId = "device-rating-consumers")
    public void consumeRating(RatingUpdateDTO ratingUpdateDTO) {
        Long deviceId = ratingUpdateDTO.getDeviceId();
        BigDecimal newRating = ratingUpdateDTO.getNewAverageRating();
        deviceService.updateDeviceRating(deviceId, newRating);
        System.out.println("Consumed rating update for device " + deviceId + " new rating: " + newRating);
    }
}
