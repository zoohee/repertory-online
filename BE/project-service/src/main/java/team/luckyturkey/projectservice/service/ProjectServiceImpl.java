package team.luckyturkey.projectservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import team.luckyturkey.projectservice.document.Project;
import team.luckyturkey.projectservice.repository.project.ProjectRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;

    @Override
    public void updateProject(Long projectId, List<Long> sourceIdList) {
        projectRepository.findAndModify(projectId, sourceIdList);
    }

    @Override
    public Project findProject(Long projectId) {
        return projectRepository.findById(projectId).orElse(new Project());
    }
}
