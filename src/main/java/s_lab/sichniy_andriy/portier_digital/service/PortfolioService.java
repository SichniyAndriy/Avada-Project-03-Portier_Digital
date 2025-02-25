package s_lab.sichniy_andriy.portier_digital.service;

import java.util.List;
import org.springframework.data.domain.Page;
import s_lab.sichniy_andriy.portier_digital.model.dto.ProjectDto;

public interface PortfolioService {

    List<ProjectDto> getAllProjects();

    Page<ProjectDto> getProjectPage(int page, int size, String col);

    List<ProjectDto> getLastProjects(int limit);

    ProjectDto getProjectById(long id);

    boolean deleteById(long id);

    long saveProject(ProjectDto projectDto);

}
