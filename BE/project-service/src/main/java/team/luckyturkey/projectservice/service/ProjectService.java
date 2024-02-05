package team.luckyturkey.projectservice.service;

import org.springframework.web.multipart.MultipartFile;
import team.luckyturkey.projectservice.document.Project;

import java.util.List;

public interface ProjectService {
    void updateProjectSourceList(Long projectId, List<Long> sourceIdList);
    Project findProject(Long projectId);

    void updateProjectSetting(Project project);

    Long saveProject(Project project, MultipartFile projectThumbnail);

    void deleteProject(Long projectId);

    List<Project> getProjectList(Long userId);
}
