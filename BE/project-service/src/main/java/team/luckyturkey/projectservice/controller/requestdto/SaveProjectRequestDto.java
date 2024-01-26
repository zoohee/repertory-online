package team.luckyturkey.projectservice.controller.requestdto;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@ToString
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SaveProjectRequestDto {
    private String projectName;
    private MultipartFile projectThumbnail;
}
