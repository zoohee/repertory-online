package team.luckyturkey.danceservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import team.luckyturkey.danceservice.controller.requestdto.PostCloneRequest;
import team.luckyturkey.danceservice.service.CloneService;

@RestController
@RequestMapping("/clone")
@RequiredArgsConstructor
public class CloneController {

    private final CloneService cloneService;
    @PostMapping
    public ResponseEntity<Void> cloneSource(
            @RequestBody PostCloneRequest postCloneRequest
    ){
        cloneService.clone(postCloneRequest);
        return ResponseEntity.ok(null);
    }
}
