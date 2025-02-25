package s_lab.sichniy_andriy.portier_digital.controller.admin;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import s_lab.sichniy_andriy.portier_digital.model.dto.SkillDto;
import s_lab.sichniy_andriy.portier_digital.service.AboutService;

@Controller
@RequestMapping("/admin")
public class SkillsController {

    private final AboutService aboutService;

    public SkillsController(
            @Autowired AboutService aboutService
    ) {
        this.aboutService = aboutService;
    }


    @GetMapping({"/skills", "/skills"})
    public ModelAndView skills(ModelAndView modelAndView) {
        List<SkillDto> skillDtoList = aboutService.getAllSkills();

        modelAndView.addObject("skills", skillDtoList);
        modelAndView.setViewName("admin/skills");
        return modelAndView;
    }
}
