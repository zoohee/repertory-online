package team.luckyturkey.projectservice.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;

import static net.bytebuddy.matcher.ElementMatchers.is;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class ProjectIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    // MessageMapping 테스트는 복잡하므로 여기서는 생략하겠습니다.

    @Test
    public void getProjectTest() throws Exception {
        Long projectId = 1L; // 테스트할 projectId를 입력하세요.

        mockMvc.perform(get("/{projectId}", projectId))
                .andExpect(status().isOk())
                .andExpect((ResultMatcher) jsonPath("$.id", is(projectId.intValue())));
    }

//    @Test
//    public void patchProjectTest() throws Exception {
//        Long projectId = 1L; // 테스트할 projectId를 입력하세요.
//        String projectName = "newProjectName"; // 테스트할 projectName를 입력하세요.
//
//        PatchProjectRequestDto patchProjectRequestDto = PatchProjectRequestDto.builder()
//                .projectName(projectName)
//                .build();
//
//        mockMvc.perform(patch("/{projectId}", projectId)
//                .contentType(MediaType.APPLICATION_JSON)
//                .body(objectMapper.writeValueAsString(patchProjectRequestDto)))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$", is(projectId.intValue())));
//    }
}