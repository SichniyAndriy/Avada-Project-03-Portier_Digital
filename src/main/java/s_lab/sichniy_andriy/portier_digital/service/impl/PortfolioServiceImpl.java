package s_lab.sichniy_andriy.portier_digital.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import s_lab.sichniy_andriy.portier_digital.model.Project;
import s_lab.sichniy_andriy.portier_digital.model.dto.ProjectDto;
import s_lab.sichniy_andriy.portier_digital.model.mapper.ProjectMapper;
import s_lab.sichniy_andriy.portier_digital.repository.ProjectRepository;
import s_lab.sichniy_andriy.portier_digital.service.PortfolioService;


@Service
public class PortfolioServiceImpl implements PortfolioService {

    private final ProjectRepository projectRepository;
    private final ProjectMapper projectMapper;

    public PortfolioServiceImpl(
            @Autowired ProjectRepository projectRepository,
            @Autowired ProjectMapper projectMapper
    ) {
        this.projectRepository = projectRepository;
        this.projectMapper = projectMapper;
    }


    @Override
    public List<ProjectDto> getAllProjects() {
        List<Project> projectList =
                projectRepository.findAll(Sort.by(Direction.DESC, "id"));
        return projectMapper.toDto(projectList);
    }

}
