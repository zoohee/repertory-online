package team.luckyturkey.danceservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import team.luckyturkey.danceservice.controller.requestdto.PostCloneRequest;
import team.luckyturkey.danceservice.controller.responsedto.CloneSourceResponse;
import team.luckyturkey.danceservice.service.CloneService;

import java.util.List;

@RestController
@RequestMapping("/clone")
@RequiredArgsConstructor
public class CloneController {

    private final CloneService cloneService;

    //todo: should get memberId from token
    @Value("${test.environment.memberId}")
    private Long TEST_MEMBER_ID;

    @PostMapping
    public ResponseEntity<Void> cloneSource(
            @RequestBody PostCloneRequest postCloneRequest
    ){
        cloneService.clone(postCloneRequest);
        return ResponseEntity.ok(null);
    }

    @GetMapping
    public ResponseEntity<List<CloneSourceResponse>> getCloneSources (){
        Long memberId = TEST_MEMBER_ID;

        List<CloneSourceResponse> response = cloneService.getCloneSourceList(memberId);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{cloneSourceId}")
public ResponseEntity<Void> deleteCloneSource(
        @PathVariable Long cloneSourceId
    ){
        cloneService.deleteCloneSource(cloneSourceId);
        return ResponseEntity.ok(null);
    }
}
