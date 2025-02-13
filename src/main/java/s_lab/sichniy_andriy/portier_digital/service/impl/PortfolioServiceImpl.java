package s_lab.sichniy_andriy.portier_digital.service.impl;

import java.util.List;
import org.springframework.stereotype.Service;
import s_lab.sichniy_andriy.portier_digital.model.Project;
import s_lab.sichniy_andriy.portier_digital.model.dto.ProjectDto;
import s_lab.sichniy_andriy.portier_digital.model.mapper.ProjectMapper;
import s_lab.sichniy_andriy.portier_digital.repository.ProjectRepository;
import s_lab.sichniy_andriy.portier_digital.service.PortfolioService;


@Service
public class PortfolioServiceImpl implements PortfolioService {

    private ProjectRepository projectRepository;
    private ProjectMapper projectMapper;


    @Override
    public List<ProjectDto> getAllProjects() {
        List<Project> projectList = projectRepository.findAll();
        return projectMapper.toDto(projectList);
    }

}
