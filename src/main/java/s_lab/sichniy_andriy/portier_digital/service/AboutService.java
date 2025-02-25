package s_lab.sichniy_andriy.portier_digital.service;

import java.util.List;
import s_lab.sichniy_andriy.portier_digital.model.dto.CompanyDto;
import s_lab.sichniy_andriy.portier_digital.model.dto.ContactDto;
import s_lab.sichniy_andriy.portier_digital.model.dto.SkillDto;

public interface AboutService {

    List<CompanyDto> getAllCompanies();

    List<ContactDto> getAllContacts();

    List<SkillDto> getAllSkills();

    boolean deleteSkillsById(long id);

    SkillDto save(SkillDto skillDto);

    List<SkillDto> getSortedSkills(String sort);

}
