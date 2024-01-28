package team.luckyturkey.danceservice.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import team.luckyturkey.danceservice.controller.requestdto.PatchSourceRequest;
import team.luckyturkey.danceservice.controller.requestdto.PostSourceRequest;
import team.luckyturkey.danceservice.entity.Source;
import team.luckyturkey.danceservice.service.SourceService;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/source")
public class SourceController {

    @Value("${test.environment.userId}")
    private Long TEST_USER_ID;


    private final SourceService sourceService;

    @GetMapping
    public ResponseEntity<List<Source>> searchSourceList(
            @Param("keyword") String keyword
    ){
        List<Source> sourceList = sourceService.searchSource(keyword);
        return ResponseEntity.ok(sourceList);
    }

    @PostMapping
    public ResponseEntity<Source> postSource(
            @RequestPart PostSourceRequest postSourceRequest,
            @RequestPart MultipartFile sourceVideo
    ){
        Source source = sourceService.saveSource(postSourceRequest, sourceVideo, TEST_USER_ID);
        try {
            return ResponseEntity.created(new URI(source.getSourceUrl())).body(source);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/{sourceId}")
    public ResponseEntity<Source> getSourceById(
            @PathVariable Long sourceId
    ){
        Source source = sourceService.getSource(sourceId);
        return ResponseEntity.ok(source);
    }

    @PatchMapping("/{sourceId}")
    public ResponseEntity<Long> patchSource(
            @PathVariable Long sourceId,
            @RequestBody PatchSourceRequest patchSourceRequest
    ){
        Long updatedSource = sourceService.updateSource(sourceId, patchSourceRequest);
        return ResponseEntity.ok().body(updatedSource);
    }

    @DeleteMapping("/{sourceId}")
    public ResponseEntity<Long> deleteSource(
            @PathVariable Long sourceId
    ){
        sourceService.deleteSource(sourceId);
        return ResponseEntity.ok(sourceId);
    }

    @GetMapping("/mine")
    public ResponseEntity<List<Source>> getMySourceList(){
        List<Source> sourceList = sourceService.getMySourceList(TEST_USER_ID);
        return ResponseEntity.ok(sourceList);
    }

}