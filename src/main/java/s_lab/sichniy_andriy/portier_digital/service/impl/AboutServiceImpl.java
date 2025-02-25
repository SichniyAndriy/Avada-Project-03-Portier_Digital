package s_lab.sichniy_andriy.portier_digital.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import s_lab.sichniy_andriy.portier_digital.model.Company;
import s_lab.sichniy_andriy.portier_digital.model.Contact;
import s_lab.sichniy_andriy.portier_digital.model.Skill;
import s_lab.sichniy_andriy.portier_digital.model.dto.CompanyDto;
import s_lab.sichniy_andriy.portier_digital.model.dto.ContactDto;
import s_lab.sichniy_andriy.portier_digital.model.dto.SkillDto;
import s_lab.sichniy_andriy.portier_digital.model.mapper.CompanyMapper;
import s_lab.sichniy_andriy.portier_digital.model.mapper.ContactMapper;
import s_lab.sichniy_andriy.portier_digital.model.mapper.SkillMapper;
import s_lab.sichniy_andriy.portier_digital.repository.CompaniesRepository;
import s_lab.sichniy_andriy.portier_digital.repository.ContactsRepository;
import s_lab.sichniy_andriy.portier_digital.repository.SkillsRepository;
import s_lab.sichniy_andriy.portier_digital.service.AboutService;


@Service
public class AboutServiceImpl implements AboutService {

    private final CompanyMapper companyMapper;
    private final ContactMapper contactMapper;
    private final SkillMapper skillMapper;

    private final CompaniesRepository companiesRepository;
    private final ContactsRepository contactsRepository;
    private final SkillsRepository skillsRepository;

    public AboutServiceImpl(
            @Autowired CompanyMapper companyMapper,
            @Autowired ContactMapper contactMapper,
            @Autowired SkillMapper skillMapper,
            @Autowired CompaniesRepository companiesRepository,
            @Autowired ContactsRepository contactsRepository,
            @Autowired SkillsRepository skillsRepository
    ) {
        this.companyMapper = companyMapper;
        this.contactMapper = contactMapper;
        this.skillMapper = skillMapper;
        this.companiesRepository = companiesRepository;
        this.contactsRepository = contactsRepository;
        this.skillsRepository = skillsRepository;
    }


    @Override
    public List<CompanyDto> getAllCompanies() {
        List<Company> companies =
                companiesRepository.findAll(Sort.by(Direction.ASC, "id"));
        return companyMapper.toDto(companies);
    }

    @Override
    public List<ContactDto> getAllContacts() {
        List<Contact> contacts =
                contactsRepository.findAll(Sort.by(Direction.ASC, "id"));
        return contactMapper.toDto(contacts);
    }

    // ============================== SKILLS ==============================\\
    @Override
    public List<SkillDto> getAllSkills() {
        List<Skill> skills =
                skillsRepository.findAll(Sort.by(Direction.ASC, "id"));
        return skillMapper.toDto(skills);
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public boolean deleteSkillsById(long id) {
        boolean res = skillsRepository.existsSkillById(id);
        if (res) {
            skillsRepository.deleteById(id);
        }
        return res;
    }

    @Override
    public SkillDto save(SkillDto skillDto) {
        Skill skill = skillMapper.toEntity(skillDto);
        Skill saved = skillsRepository.saveAndFlush(skill);
        return skillMapper.toDto(saved);
    }

    @Override
    public List<SkillDto> getSortedSkills(String sort) {
        List<Skill> skills = skillsRepository.findAll(Sort.by(Direction.ASC, sort));
        return skillMapper.toDto(skills);
    }
    // ============================== \SKILLS ==============================\\

}
