package team.luckyturkey.projectservice.service;

import team.luckyturkey.projectservice.document.Project;

import java.util.List;

public interface ProjectService {
    void updateProject(Long projectId, List<Long> sourceIdList);
    Project findProject(Long projectId);
}
