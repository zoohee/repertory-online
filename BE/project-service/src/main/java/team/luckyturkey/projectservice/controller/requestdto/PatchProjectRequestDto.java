package team.luckyturkey.projectservice.controller.requestdto;

import lombok.*;

@ToString
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PatchProjectRequestDto {
    private String projectName;

}