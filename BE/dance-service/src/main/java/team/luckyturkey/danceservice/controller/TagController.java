package team.luckyturkey.danceservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import team.luckyturkey.danceservice.controller.requestdto.PostTagRequest;
import team.luckyturkey.danceservice.entity.Tag;
import team.luckyturkey.danceservice.service.TagService;

import java.util.List;


@RestController
@RequestMapping("/tag")
@RequiredArgsConstructor
public class TagController {

    private final TagService tagService;

    /**
     * todo: get user id from jwt
     * */
    @Value("${test.environment.userId}")
    private Long TEST_USER_ID;

    @PostMapping
    public ResponseEntity<Long> postTag(
            @RequestBody PostTagRequest postTagRequest
    ){
        /**
         * todo: get user id from jwt
         * */
        Tag tag = Tag.builder()
                    .userId(TEST_USER_ID)
                    .tagName(postTagRequest.getTagName())
                    .build();
        Long tagId = tagService.saveTag(tag);
        return ResponseEntity.ok(tagId);
    }

    @GetMapping
    public ResponseEntity<List<Tag>> getTagList(

    ){
        /**
         * this is only for dummy
         * todo: get user id from jwt
         * */
        List<Tag> tagList = tagService.getTagList(TEST_USER_ID);
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
