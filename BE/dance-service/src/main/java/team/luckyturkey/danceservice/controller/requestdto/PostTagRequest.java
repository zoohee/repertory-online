package team.luckyturkey.danceservice.controller.requestdto;

import lombok.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class PostTagRequest {
    private String tagName;
}
