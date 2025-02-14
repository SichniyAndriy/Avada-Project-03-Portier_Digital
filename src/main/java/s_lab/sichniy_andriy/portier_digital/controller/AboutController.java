package s_lab.sichniy_andriy.portier_digital.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import s_lab.sichniy_andriy.portier_digital.model.dto.CompanyDto;
import s_lab.sichniy_andriy.portier_digital.model.dto.ContactDto;
import s_lab.sichniy_andriy.portier_digital.model.dto.SkillDto;
import s_lab.sichniy_andriy.portier_digital.service.AboutService;

@Controller
@RequestMapping( "/about" )
public class AboutController {

    private final AboutService aboutService;

    public AboutController(
            @Autowired AboutService aboutService
    ) {
        this.aboutService = aboutService;
    }


    @GetMapping( { "", "/" } )
    public ModelAndView about(ModelAndView modelAndView) {
        List<CompanyDto> companyDtoList = aboutService.getAllCompanies();
        List<ContactDto> contactDtoList = aboutService.getAllContacts();
        List<SkillDto> skillDtoList = aboutService.getAllSkills();

        modelAndView.addObject("companies", companyDtoList);
        modelAndView.addObject("contacts", contactDtoList);
        modelAndView.addObject("skills", skillDtoList);
        modelAndView.setViewName("about");

        return modelAndView;
    }

}
