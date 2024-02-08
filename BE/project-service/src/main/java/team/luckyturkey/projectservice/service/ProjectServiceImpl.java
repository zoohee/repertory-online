package team.luckyturkey.projectservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import team.luckyturkey.projectservice.document.Project;
import team.luckyturkey.projectservice.exception.EmptyResultException;
import team.luckyturkey.projectservice.repository.project.ProjectRepository;
import team.luckyturkey.projectservice.util.ErrorCode;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;

    @Override
    public void updateProjectSourceList(Long projectId, List<Long> sourceIdList) {
        projectRepository.findAndUpdateSourceList(projectId, sourceIdList);
    }

    @Override
    public Project findProject(Long projectId) {
        return projectRepository.findById(projectId).orElse(new Project());
    }

    @Override
    public void updateProjectSetting(Project project) {
        projectRepository.findAndUpdateDetails(project);
    }

    @Override
    public Long saveProject(Project project, MultipartFile projectThumbnail) {
        /**
         * todo: S3에 썸네일 업로드
         * maybe like
         * String url = S3Client.upload(projectThumbnail);
         * project.setProjectThumbnailUrl(url);
         * */

        //this is for test
        project.setProjectThumbnailUrl(projectThumbnail.getName());

        return projectRepository.saveWithSequence(project).getId();
    }

    @Override
    public void deleteProject(Long projectId) {
        try {
            projectRepository.deleteById(projectId);
        } catch (Exception e) {
            throw new EmptyResultException(ErrorCode.NOT_FOUND);
        }
    }

    @Override
    public List<Project> getProjectList(Long userId) {
        return projectRepository.findByUserId(userId);
    }
}
