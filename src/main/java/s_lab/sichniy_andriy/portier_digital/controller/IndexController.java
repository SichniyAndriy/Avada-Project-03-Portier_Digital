package s_lab.sichniy_andriy.portier_digital.controller;


import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import s_lab.sichniy_andriy.portier_digital.model.dto.ProjectDto;
import s_lab.sichniy_andriy.portier_digital.service.PortfolioService;

@Controller
@RequestMapping( { "/", "/index" } )
public class IndexController {

    private final static int AMOUNT_LAST_PROJECTS = 4;
    private final PortfolioService portfolioService;

    public IndexController(
            @Autowired PortfolioService portfolioService
    ) {
        this.portfolioService = portfolioService;
    }


    @GetMapping( { "", "/" } )
    public ModelAndView index(ModelAndView modelAndView) {
        List<ProjectDto> projectDtoList = portfolioService.getLastProjects(AMOUNT_LAST_PROJECTS);

        modelAndView.setViewName("index");
        modelAndView.addObject("projects", projectDtoList);

        return modelAndView;
    }

}
