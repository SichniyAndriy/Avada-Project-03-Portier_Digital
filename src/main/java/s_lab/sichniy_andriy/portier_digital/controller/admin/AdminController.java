package s_lab.sichniy_andriy.portier_digital.controller.admin;

import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import s_lab.sichniy_andriy.portier_digital.model.dto.ProjectDto;
import s_lab.sichniy_andriy.portier_digital.service.PortfolioService;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final PortfolioService portfolioService;

    public AdminController(
            PortfolioService portfolioService
    ) {
        this.portfolioService = portfolioService;
    }


    @GetMapping( { "", "/","/projects", "/projects/" } )
    public ModelAndView index(ModelAndView modelAndView) {
        List<ProjectDto> projectDtoList = portfolioService.getAllProjects();
        modelAndView.setViewName("admin/projects");
        modelAndView.addObject("projects", projectDtoList);
        return modelAndView;
    }

    @GetMapping("/project/{id}")
    public ModelAndView projectDetail(
            ModelAndView modelAndView,
            @PathVariable int id
    ) {
        modelAndView.setViewName("admin/project");

        return modelAndView;
    }
}
