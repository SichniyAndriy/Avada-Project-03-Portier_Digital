package s_lab.sichniy_andriy.portier_digital.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import net.datafaker.Faker;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import s_lab.sichniy_andriy.portier_digital.model.Project;
import s_lab.sichniy_andriy.portier_digital.model.dto.ProjectDto;
import s_lab.sichniy_andriy.portier_digital.model.mapper.ProjectMapper;
import s_lab.sichniy_andriy.portier_digital.repository.ProjectRepository;

@ExtendWith(MockitoExtension.class)
class PortfolioServiceImplTest {

    private PortfolioServiceImpl portfolioService;

    @Mock private ProjectRepository projectRepository;

    private final List<Project> projects = new ArrayList<>();
    private final Faker faker = new Faker();

    @BeforeEach
    void setUp() {
        portfolioService = new PortfolioServiceImpl(
                projectRepository,
                Mappers.getMapper(ProjectMapper.class)
        );
        initProjects();
    }

    @AfterEach
    void tearDown() {
        portfolioService = null;
        projects.clear();
    }

    @Test @DisplayName("Test getAllProjects")
    void getAllProjects() {
        Mockito.when(projectRepository.findAll(Mockito.any(Sort.class))).thenReturn(projects);
        List<ProjectDto> projectDtos = portfolioService.getAllProjects();
        Assertions.assertNotNull(projectDtos);
        Assertions.assertEquals(projects.size(), projectDtos.size());
        MatcherAssert.assertThat(projectDtos.get(0), Matchers.instanceOf(ProjectDto.class));
        MatcherAssert.assertThat(projectDtos.get(1), Matchers.instanceOf(ProjectDto.class));
        MatcherAssert.assertThat(projectDtos.get(2), Matchers.instanceOf(ProjectDto.class));
        MatcherAssert.assertThat(projectDtos.get(3), Matchers.instanceOf(ProjectDto.class));
        MatcherAssert.assertThat(projectDtos.get(4), Matchers.instanceOf(ProjectDto.class));
        Mockito.verify(projectRepository, Mockito.times(1)).findAll(Mockito.any(Sort.class));
    }

    @TestFactory @DisplayName("Test getProjectPage")
    Iterable<DynamicTest> getProjectPage() {
        return List.of(
                DynamicTest.dynamicTest("With wright page number", () -> {
                    Mockito.when(projectRepository.count()).thenReturn(Long.valueOf(5));
                    Mockito.when(projectRepository.findAll(Mockito.any(PageRequest.class)))
                            .thenReturn(new PageImpl<>(projects));
                    Page<ProjectDto> projectDtoPage = portfolioService.getProjectPage(1, 5, "id");
                    Assertions.assertNotNull(projectDtoPage);
                    Assertions.assertEquals(projects.size(), projectDtoPage.getTotalElements());
                    MatcherAssert.assertThat(projectDtoPage.get().findFirst().get(), Matchers.instanceOf(ProjectDto.class));
                    Mockito.verify(projectRepository, Mockito.times(1)).findAll(Mockito.any(PageRequest.class));
                }),
                DynamicTest.dynamicTest("With wrong page number", () -> {
                    Assertions.assertThrows(
                            IllegalArgumentException.class,
                            () -> portfolioService.getProjectPage(0, 5, "id"));
                    Mockito.verify(projectRepository, Mockito.times(1)).findAll(Mockito.any(PageRequest.class));
                }),
                DynamicTest.dynamicTest("With wrong page number", () -> {
                    Mockito.when(projectRepository.findAll(Mockito.any(PageRequest.class)))
                            .thenReturn(new PageImpl<>(projects));
                    Page<ProjectDto> projectDtoPage = portfolioService.getProjectPage(5, 10, "id");
                    Assertions.assertNotNull(projectDtoPage);
                    Assertions.assertEquals(projects.size(), projectDtoPage.getNumberOfElements());
                    Mockito.verify(projectRepository, Mockito.times(2)).findAll(Mockito.any(PageRequest.class));
                })
        );
    }

    @TestFactory @DisplayName("Test getProjectById")
    Iterable<DynamicTest> getLastProjects() {
        return List.of(
                DynamicTest.dynamicTest("With wright parameter", () -> {
                    Mockito.when(projectRepository.findAllBy(Mockito.any(PageRequest.class)))
                            .thenReturn(projects);
                    List<ProjectDto> lastProjects = portfolioService.getLastProjects(5);
                    Assertions.assertNotNull(lastProjects);
                    Assertions.assertEquals(projects.size(), lastProjects.size());
                    MatcherAssert.assertThat(lastProjects.get(0), Matchers.instanceOf(ProjectDto.class));
                    MatcherAssert.assertThat(lastProjects.get(1), Matchers.instanceOf(ProjectDto.class));
                    MatcherAssert.assertThat(lastProjects.get(2), Matchers.instanceOf(ProjectDto.class));
                    MatcherAssert.assertThat(lastProjects.get(3), Matchers.instanceOf(ProjectDto.class));
                    MatcherAssert.assertThat(lastProjects.get(4), Matchers.instanceOf(ProjectDto.class));
                    Mockito.verify(projectRepository, Mockito.times(1)).findAllBy(Mockito.any(PageRequest.class));
                }),
                DynamicTest.dynamicTest("With wrong parameter", () -> {
                    Assertions.assertThrows(
                            IllegalArgumentException.class,
                            () -> portfolioService.getLastProjects(0)
                    );
                    Mockito.verify(projectRepository, Mockito.times(1)).findAllBy(Mockito.any(PageRequest.class));
                })
        );
    }

    @TestFactory @DisplayName("Test getProjectById")
    Iterable<DynamicTest> getProjectById() {
        return List.of(
                DynamicTest.dynamicTest("With wright id", () -> {
                    Mockito.when(projectRepository.findById(Mockito.anyLong()))
                            .thenReturn(Optional.of(projects.get(0)));
                    ProjectDto projectById = portfolioService.getProjectById(1);
                    Assertions.assertNotNull(projectById);
                    Assertions.assertEquals(projects.get(0).getId(), projectById.id());
                    Assertions.assertEquals(projects.get(0).getTitle(), projectById.title());
                    Assertions.assertEquals(projects.get(0).getDescription(), projectById.description());
                    Assertions.assertEquals(projects.get(0).getImagePath(), projectById.imagePath());
                    Mockito.verify(projectRepository, Mockito.times(1)).findById(Mockito.anyLong());
                }),
                DynamicTest.dynamicTest("With wrong id", () -> {
                    Mockito.when(projectRepository.findById(Mockito.anyLong()))
                            .thenReturn(Optional.empty());
                    ProjectDto projectById = portfolioService.getProjectById(1);
                    Assertions.assertNull(projectById);
                    Mockito.verify(projectRepository, Mockito.times(2)).findById(Mockito.anyLong());
                })
        );
    }

    @TestFactory @DisplayName("Test saveProject")
    Iterable<DynamicTest> saveProject() {
        return List.of(
                DynamicTest.dynamicTest("With wright parameter", () -> {
                    Mockito.when(projectRepository.save(Mockito.any(Project.class)))
                            .thenReturn(projects.get(0));
                    long id = portfolioService.saveProject(new ProjectDto(
                            null,
                            projects.get(0).getTitle(),
                            projects.get(0).getDescription(),
                            projects.get(0).getImagePath()
                    ));
                    Assertions.assertEquals(id, projects.get(0).getId());
                    Mockito.verify(projectRepository, Mockito.times(1)).save(Mockito.any(Project.class));
                }),
                DynamicTest.dynamicTest("With wrong parameter null", () -> {
                    Mockito.when(projectRepository.save(Mockito.any())).thenReturn(null);
                    Assertions.assertThrows(
                            NullPointerException.class,
                            () -> portfolioService.saveProject(null)
                    );
                    Mockito.verify(projectRepository, Mockito.times(1)).save(Mockito.any(Project.class));
                })
        );
    }

    @TestFactory @DisplayName("Test deleteProject")
    Iterable<DynamicTest> deleteProject() {
        return List.of(
                DynamicTest.dynamicTest("With wright parameter", () -> {
                    Mockito.doNothing().when(projectRepository).deleteById(Mockito.anyLong());
                    Mockito.when(projectRepository.existsById(Mockito.anyLong())).thenReturn(true);
                    Assertions.assertTrue(portfolioService.deleteProject(1));
                    Mockito.verify(projectRepository, Mockito.times(1)).deleteById(Mockito.anyLong());
                }),
                DynamicTest.dynamicTest("With wright parameter", () -> {
                    Mockito.when(projectRepository.existsById(Mockito.anyLong())).thenReturn(false);
                    portfolioService.deleteProject(1);
                    Assertions.assertFalse(portfolioService.deleteProject(1));
                    Mockito.verify(projectRepository, Mockito.times(1)).deleteById(Mockito.anyLong());
                })
        );
    }

    private void initProjects() {
        for (int i = 1; i < 6; ++i) {
            Project project = new Project();
            project.setId((long) i);
            project.setTitle(faker.commerce().productName());
            project.setDescription(faker.lorem().sentence(50, 50));
            projects.add(project);
        }
    }
}