package s_lab.sichniy_andriy.portier_digital.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import s_lab.sichniy_andriy.portier_digital.model.mapper.CompanyMapper;
import s_lab.sichniy_andriy.portier_digital.model.mapper.ContactMapper;
import s_lab.sichniy_andriy.portier_digital.model.mapper.SkillMapper;
import s_lab.sichniy_andriy.portier_digital.service.AboutService;


@Service
public class AboutServiceImpl implements AboutService {

    private final CompanyMapper companyMapper;
    private final ContactMapper contactMapper;
    private final SkillMapper skillMapper;

    public AboutServiceImpl(
            @Autowired CompanyMapper companyMapper,
            @Autowired ContactMapper contactMapper,
            @Autowired SkillMapper skillMapper
    ) {
        this.companyMapper = companyMapper;
        this.contactMapper = contactMapper;
        this.skillMapper = skillMapper;
    }

}
