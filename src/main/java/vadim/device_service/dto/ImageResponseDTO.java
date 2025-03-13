package vadim.device_service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ImageResponseDTO implements Serializable {
    private Long id;
    private String name;
    private String url;
    private Long deviceId;
}
