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
import s_lab.sichniy_andriy.portier_digital.model.dto.CompanyDto;
import s_lab.sichniy_andriy.portier_digital.service.AboutService;

@Controller
@RequestMapping("/admin")
public class CompaniesController {

    private final AboutService aboutService;

    public CompaniesController(
            @Autowired AboutService aboutService
    ) {
        this.aboutService = aboutService;
    }

    @GetMapping({"/companies/", "/companies"})
    public ModelAndView skills(ModelAndView modelAndView) {
        List<CompanyDto> companyDtoList = aboutService.getAllCompanies();
        modelAndView.addObject("companies", companyDtoList);
        modelAndView.setViewName("admin/companies");
        return modelAndView;
    }

    @GetMapping({"/companies/sort"})
    public String sortedSkills(
            Model model,
            @RequestParam String sort
    ) {
        List<CompanyDto> sortedCompanyDtoList = aboutService.getSortedCompanies(sort);
        model.addAttribute("companies", sortedCompanyDtoList);
        return "admin/companies :: companiesTable";
    }

    @DeleteMapping("/companies/delete/{id}")
    public ResponseEntity<HttpStatus> deleteSkill(
            @PathVariable long id
    ) {
        boolean res = aboutService.deleteCompanyById(id);
        return ResponseEntity.status(res ? HttpStatus.OK : HttpStatus.NOT_FOUND).build();
    }

    @PostMapping("/companies/save")
    public ResponseEntity<CompanyDto> saveSkill(
            @ModelAttribute CompanyDto companyDto
    ) {
        CompanyDto newCompanyDto = aboutService.saveCompany(companyDto);
        return ResponseEntity.ok(newCompanyDto);
    }
}
