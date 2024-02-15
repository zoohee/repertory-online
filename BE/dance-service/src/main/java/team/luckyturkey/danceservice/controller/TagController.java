package team.luckyturkey.danceservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import team.luckyturkey.danceservice.controller.requestdto.PostTagRequest;
import team.luckyturkey.danceservice.controller.responsedto.StandardTagResponse;
import team.luckyturkey.danceservice.service.TagService;
import team.luckyturkey.danceservice.util.JwtUtil;

import java.util.List;


@RestController
@RequestMapping("/tag")
@RequiredArgsConstructor
public class TagController {

    private final TagService tagService;
    private final JwtUtil jwtUtil;

    /**
     * todo: get user id from jwt :done
     * */
//    @Value("${test.environment.memberId}")
//    private Long TEST_MEMBER_ID;

    @PostMapping
    public ResponseEntity<Long> postTag(
            @RequestBody PostTagRequest postTagRequest,
            @RequestHeader("Authorization") final String accessToken
    ){
        /**
         * todo: get user id from jwt :done
         * */
        String atc = accessToken.split(" ")[1];

        Long memberId = jwtUtil.getMemberId(atc);
        Long tagId = tagService.saveTag(postTagRequest, memberId);
        return ResponseEntity.ok(tagId);
    }

    @GetMapping
    public ResponseEntity<List<StandardTagResponse>> getTagList(
            @RequestHeader("Authorization") final String accessToken
    ){
        /**
         * this is only for dummy
         * todo: get user id from jwt :done
         * */
        String atc = accessToken.split(" ")[1];

        Long memberId = jwtUtil.getMemberId(atc);
        List<StandardTagResponse> tagList = tagService.getTagList(memberId);
        return ResponseEntity.ok(tagList);
    }

    @DeleteMapping("/{tagId}")
    public ResponseEntity<Long> deleteTag(
            @PathVariable Long tagId
    ){
        tagService.deleteTag(tagId);
        return ResponseEntity.ok(tagId);
    }

}
