package vadim.device_service.controller;

import io.minio.errors.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import vadim.device_service.dto.ImageResponseDTO;
import vadim.device_service.service.ImageService;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

@RestController
@RequestMapping("/api/images")
@RequiredArgsConstructor
public class ImageController {

    private final ImageService imageService;

    @GetMapping("/getAll/{deviceId}")
    public ResponseEntity<List<ImageResponseDTO>> findAllByDevice(@PathVariable Long deviceId) {
        return ResponseEntity.ok(imageService.findAllByDevice(deviceId));
    }

    @GetMapping("/getById/{imageId}")
    public ResponseEntity<ImageResponseDTO> findById(@PathVariable Long imageId) {
        return ResponseEntity.ok(imageService.findById(imageId));
    }

    @PostMapping("/upload/{deviceId}")
    public ResponseEntity<ImageResponseDTO> uploadImage(@RequestParam("file") MultipartFile file, @PathVariable Long deviceId) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        return ResponseEntity.ok(imageService.uploadImage(file, deviceId));
    }

    @DeleteMapping("delete/{imageId}")
    public ResponseEntity<Void> deleteImage(@PathVariable Long imageId) {
        imageService.deleteImage(imageId);
        return ResponseEntity.noContent().build();
    }
}
