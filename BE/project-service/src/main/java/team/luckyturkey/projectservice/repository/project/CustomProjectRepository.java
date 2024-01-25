package team.luckyturkey.projectservice.repository.project;

import team.luckyturkey.projectservice.document.Project;

import java.util.List;

public interface CustomProjectRepository {
    Project saveWithSequence(Project project);
    Project findAndModify(Long projectId, List<Long> sourceIdList);
}
