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
@RequestMapping( "/portfolio" )
public class PortfolioController {

    private final PortfolioService portfolioService;

    public PortfolioController(
            @Autowired PortfolioService portfolioService
    ) {
        this.portfolioService = portfolioService;
    }


    @GetMapping( { "", "/" } )
    public ModelAndView portfolio(ModelAndView modelAndView) {
        modelAndView.setViewName("portfolio");
        List<ProjectDto> projectDtoList = portfolioService.getAllProjects();
        modelAndView.addObject("projects", projectDtoList);
        return modelAndView;
    }

}
