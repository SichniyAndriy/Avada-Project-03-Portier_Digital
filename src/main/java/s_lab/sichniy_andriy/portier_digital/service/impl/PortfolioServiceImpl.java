package s_lab.sichniy_andriy.portier_digital.service.impl;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
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
                projectRepository.findAll(Sort.by("id"));
        return projectMapper.toDto(projectList);
    }

    @Override
    public Page<ProjectDto> getProjectPage(int page, int size, String col) {
        long amountAll = projectRepository.count();
        long pages = amountAll / size + (amountAll % size == 0 ? 0 : 1);
        if (page > pages) {
            page = (int) pages;
        }
        Page<Project> projectPage =
                projectRepository.findAll(PageRequest.of(--page, size, Sort.by(col)));
        return projectPage.map(projectMapper::toDto);
    }

    @Override
    public List<ProjectDto> getLastProjects(int limit) {
        List<Project> topLimitByOrderByIdDesc =
                projectRepository.findAllBy(PageRequest.of(0, limit, Sort.by(Direction.DESC, "id")));
        return projectMapper.toDto(topLimitByOrderByIdDesc);
    }

    @Override
    public ProjectDto getProjectById(long id) {
        Optional<Project> projectOptional = projectRepository.findById(id);
        return projectOptional.map(projectMapper::toDto).orElse(null);
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public boolean deleteById(long id) {
        boolean res = projectRepository.existsById(id);
        if (res) {
            projectRepository.deleteById(id);
        }
        return res;
    }

    @Override
    public long saveProject(ProjectDto projectDto) {
        Project project = projectMapper.toEntity(projectDto);
        Project saved = projectRepository.save(project);
        return saved.getId();
    }

}
