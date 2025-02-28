package s_lab.sichniy_andriy.portier_digital.controller.admin;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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


    @GetMapping({"/skills/", "/skills"})
    public ModelAndView skills(ModelAndView modelAndView) {
        List<SkillDto> skillDtoList = aboutService.getAllSkills();
        modelAndView.addObject("skills", skillDtoList);
        modelAndView.setViewName("admin/skills");
        return modelAndView;
    }

    @SuppressWarnings("SpringMVCViewInspection")
    @GetMapping({"/skills/sort"})
    public String sortedSkills(
            Model model,
            @RequestParam String sort
    ) {
        List<SkillDto> sortedSkillDtoList = aboutService.getSortedSkills(sort);
        model.addAttribute("skills", sortedSkillDtoList);
        return "admin/skills :: skillsTable";
    }

    @DeleteMapping("/skills/delete/{id}")
    public ResponseEntity<HttpStatus> deleteSkill(
            @PathVariable long id
    ) {
        boolean res = aboutService.deleteSkillById(id);
        return ResponseEntity.status(res ? HttpStatus.OK : HttpStatus.NOT_FOUND).build();
    }

    @PostMapping("/skills/save")
    public ResponseEntity<SkillDto> saveSkill(
            @ModelAttribute SkillDto skillDto
    ) {
        SkillDto newSkillDto = aboutService.saveSkill(skillDto);
        return ResponseEntity.ok(newSkillDto);
    }

}
