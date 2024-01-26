package team.luckyturkey.projectservice.repository.project;

import team.luckyturkey.projectservice.document.Project;

import java.util.List;

public interface CustomProjectRepository {
    Project saveWithSequence(Project project);

    //소켓 통신에 활용됨. 소스 순서 변경
    Project findAndUpdateSourceList(Long projectId, List<Long> sourceIdList);

    //일부 설정 변경 (현재는 이름만)
    Project findAndUpdateDetails(Project project);

}
