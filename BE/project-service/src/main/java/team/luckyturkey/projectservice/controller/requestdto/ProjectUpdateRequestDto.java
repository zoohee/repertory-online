package team.luckyturkey.projectservice.controller.requestdto;

import lombok.*;

import java.util.List;

@ToString
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProjectUpdateRequestDto {
    private List<Long> sourceIdList;
}