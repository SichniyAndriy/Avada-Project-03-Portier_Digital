package s_lab.sichniy_andriy.portier_digital.service;

import java.util.List;
import s_lab.sichniy_andriy.portier_digital.model.dto.CompanyDto;
import s_lab.sichniy_andriy.portier_digital.model.dto.ContactDto;
import s_lab.sichniy_andriy.portier_digital.model.dto.SkillDto;

public interface AboutService {

    List<CompanyDto> getAllCompanies();

    List<CompanyDto> getSortedCompanies(String sort);

    CompanyDto saveCompany(CompanyDto companyDto);

    boolean deleteCompanyById(long id);

    //===============================================================\\

    List<ContactDto> getAllContacts();

    //===============================================================\\
    List<SkillDto> getAllSkills();

    List<SkillDto> getSortedSkills(String sort);

    SkillDto saveSkill(SkillDto skillDto);

    boolean deleteSkillsById(long id);

}
