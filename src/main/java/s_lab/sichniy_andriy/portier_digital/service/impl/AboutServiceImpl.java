package s_lab.sichniy_andriy.portier_digital.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import s_lab.sichniy_andriy.portier_digital.model.mapper.CompaniesMapper;
import s_lab.sichniy_andriy.portier_digital.model.mapper.ContactsMapper;
import s_lab.sichniy_andriy.portier_digital.model.mapper.SkillMapper;
import s_lab.sichniy_andriy.portier_digital.service.AboutService;


@Service
public class AboutServiceImpl implements AboutService {

    private final CompaniesMapper companiesMapper;
    private final ContactsMapper contactsMapper;
    private final SkillMapper skillMapper;

    public AboutServiceImpl(
            @Autowired CompaniesMapper companiesMapper,
            @Autowired ContactsMapper contactsMapper,
            @Autowired SkillMapper skillMapper
    ) {
        this.companiesMapper = companiesMapper;
        this.contactsMapper = contactsMapper;
        this.skillMapper = skillMapper;
    }

}
