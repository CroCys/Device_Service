package vadim.device_service.service;

import io.minio.GetPresignedObjectUrlArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.errors.*;
import io.minio.http.Method;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import vadim.device_service.dto.ImageResponseDTO;
import vadim.device_service.entity.Image;
import vadim.device_service.exception.DeviceNotFoundException;
import vadim.device_service.exception.ImageNotFoundException;
import vadim.device_service.mapper.ImageMapper;
import vadim.device_service.repository.DeviceRepository;
import vadim.device_service.repository.ImageRepository;

import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.UUID;

@Service
public class ImageService {

    private final MinioClient minioClient;
    private final ImageRepository imageRepository;
    private final ImageMapper imageMapper;
    private final DeviceRepository deviceRepository;

    @Value("${minio.bucket}")
    private String bucket;

    public ImageService(MinioClient minioClient, ImageRepository imageRepository, ImageMapper imageMapper, DeviceRepository deviceRepository) {
        this.minioClient = minioClient;
        this.imageRepository = imageRepository;
        this.imageMapper = imageMapper;
        this.deviceRepository = deviceRepository;
    }

    public List<ImageResponseDTO> findAllByDevice(Long deviceId) {
        List<Image> images = imageRepository.findAllByDeviceId(deviceId);
        return imageMapper.toDTOSet(images);
    }

    public ImageResponseDTO findById(Long imageId) {
        Image image = imageRepository.findById(imageId).orElseThrow(() -> new ImageNotFoundException("Image not found with id " + imageId));
        return imageMapper.toDTO(image);
    }

    public ImageResponseDTO uploadImage(MultipartFile image, Long deviceId) throws IOException,
            ServerException, InsufficientDataException,
            ErrorResponseException, NoSuchAlgorithmException,
            InvalidKeyException, InvalidResponseException,
            XmlParserException, InternalException {
        String fileName = UUID.randomUUID().toString();

        InputStream inputStream = image.getInputStream();

        minioClient.putObject(
                PutObjectArgs.builder()
                        .bucket(bucket)
                        .object(fileName)
                        .stream(inputStream, image.getSize(), -1)
                        .contentType(image.getContentType())
                        .build()
        );

        String url = getImageUrl(fileName);

        Image uploadedImage = new Image();
        uploadedImage.setName(fileName);
        uploadedImage.setUrl(url);
        uploadedImage.setDevice(deviceRepository.findById(deviceId)
                .orElseThrow(() -> new DeviceNotFoundException("Device not found with id " + deviceId)));

        Image savedImage = imageRepository.save(uploadedImage);

        return imageMapper.toDTO(savedImage);
    }

    private String getImageUrl(String imageName) throws ServerException,
            InsufficientDataException, ErrorResponseException,
            IOException, NoSuchAlgorithmException,
            InvalidKeyException, InvalidResponseException,
            XmlParserException, InternalException {
        return minioClient.getPresignedObjectUrl(
                GetPresignedObjectUrlArgs.builder()
                        .method(Method.GET)
                        .bucket(bucket)
                        .object(imageName)
                        .build()
        );
    }
}
