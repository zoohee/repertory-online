package team.luckyturkey.projectservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import team.luckyturkey.projectservice.document.Project;
import team.luckyturkey.projectservice.repository.project.ProjectRepository;

import java.util.List;

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
         *
         * String url = S3Client.upload(projectThumbnail);
         * project.setProjectThumbnailUrl(url);
         *
         *
         * */

        return projectRepository.saveWithSequence(project).getId();
    }
}
