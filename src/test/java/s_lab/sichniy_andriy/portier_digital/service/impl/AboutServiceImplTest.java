package s_lab.sichniy_andriy.portier_digital.service.impl;

import java.util.ArrayList;
import java.util.List;
import net.datafaker.Faker;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Sort;
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

@ExtendWith(MockitoExtension.class)
@DisplayName("Testing AboutServiceImpl class")
class AboutServiceImplTest {

    private AboutServiceImpl aboutService;

    @Mock private CompaniesRepository companiesRepository;
    @Mock private ContactsRepository contactsRepository;
    @Mock private SkillsRepository skillsRepository;

    private final Faker faker = new Faker();
    private final List<Company> companies = new ArrayList<>();
    private final List<Contact> contacts = new ArrayList<>();
    private final List<Skill> skills = new ArrayList<>();


    @BeforeEach
    void setUp() {
        initCompanies();
        initContacts();
        initSkills();

        aboutService = new AboutServiceImpl(
                companiesRepository,
                contactsRepository,
                skillsRepository,
                Mappers.getMapper(CompanyMapper.class),
                Mappers.getMapper(ContactMapper.class),
                Mappers.getMapper(SkillMapper.class)
        );
    }

    @AfterEach
    void tearDown() {
        companies.clear();
        contacts.clear();
        skills.clear();
    }

    @Test @DisplayName("Test getAllCompanies")
    void test_getAllCompanies_Ok() {
        Mockito.when(companiesRepository.findAll(Mockito.any(Sort.class))).thenReturn(companies);
        List<CompanyDto> companyDtos = aboutService.getAllCompanies();
        Assertions.assertNotNull(companyDtos);
        Assertions.assertEquals(companies.size(), companyDtos.size());
        Assertions.assertEquals(companies.get(0).getTitle(), companyDtos.get(0).title());
        Assertions.assertEquals(companies.get(4).getTitle(), companyDtos.get(4).title());
        Mockito.verify(companiesRepository, Mockito.times(1)).findAll(Mockito.any(Sort.class));
    }

    @TestFactory @DisplayName("Test getSortedCompanies")
    Iterable<DynamicTest> getSortedCompanies() {
        return List.of(
                DynamicTest.dynamicTest("Sorting by Id. Ok", () -> {
                    Mockito.when(companiesRepository.findAll(Mockito.any(Sort.class ))).thenReturn(companies);
                    List<CompanyDto> companyDtos = aboutService.getSortedCompanies("id");
                    Assertions.assertAll(
                        () -> Assertions.assertNotNull(companyDtos),
                        () -> Assertions.assertEquals(companies.size(), companyDtos.size()),
                        () -> Assertions.assertEquals(CompanyDto.class, companyDtos.get(0).getClass())
                    );
                    Mockito.verify(companiesRepository, Mockito.times(1)).findAll(Mockito.any(Sort.class));
                }),
                DynamicTest.dynamicTest("Sorting by Title. Ok", () -> {
                    Mockito.when(companiesRepository.findAll(Mockito.any(Sort.class ))).thenReturn(companies);
                    List<CompanyDto> companyDtos = aboutService.getSortedCompanies("title");
                    Assertions.assertAll(
                            () -> Assertions.assertNotNull(companyDtos),
                            () -> Assertions.assertEquals(companies.size(), companyDtos.size()),
                            () -> Assertions.assertEquals(CompanyDto.class, companyDtos.get(0).getClass())
                    );
                    Mockito.verify(companiesRepository, Mockito.times(2)).findAll(Mockito.any(Sort.class));
                }),
                DynamicTest.dynamicTest("With empty string. NotOk", () -> {
                    Assertions.assertThrows(
                            IllegalArgumentException.class,
                            () -> aboutService.getSortedCompanies("")
                    );
                    Mockito.verify(companiesRepository, Mockito.times(2)).findAll(Mockito.any(Sort.class));
                }),
                DynamicTest.dynamicTest("With null. NotOk", () -> {
                    Assertions.assertThrows(
                            IllegalArgumentException.class,
                            () -> aboutService.getSortedCompanies(null)
                    );
                    Mockito.verify(companiesRepository, Mockito.times(2)).findAll(Mockito.any(Sort.class));
                })

        );
    }

    @TestFactory @DisplayName("Test saveCompany")
    Iterable<DynamicTest> testSaveCompany() {
        return List.of(
                DynamicTest.dynamicTest("Save Company With Wright Parameter. Ok", () -> {
                    Mockito.when(companiesRepository.saveAndFlush(Mockito.any(Company.class))).thenReturn(companies.get(0));
                    CompanyDto companyDto = aboutService.saveCompany(
                            new CompanyDto(null, faker.company().name(), faker.company().profession())
                    );
                    Assertions.assertNotNull(companyDto);
                    Assertions.assertEquals(companyDto.id(), companies.get(0).getId());
                    Assertions.assertEquals(companyDto.title(), companies.get(0).getTitle());
                    Assertions.assertEquals(companyDto.position(), companies.get(0).getPosition());
                    Mockito.verify(companiesRepository, Mockito.times(1)).saveAndFlush(Mockito.any(Company.class));
                }),
                DynamicTest.dynamicTest("Save Company With NULL. NotOk", () -> {
                    Mockito.when(companiesRepository.saveAndFlush(Mockito.any())).thenReturn(null);
                    CompanyDto companyDto = aboutService.saveCompany(null);
                    Assertions.assertNull(companyDto);
                    Mockito.verify(companiesRepository, Mockito.times(2)).saveAndFlush(Mockito.any());
                })
        );
    }

    @TestFactory @DisplayName("Test deleteCompanyById")
    Iterable<DynamicTest> test_deleteCompanyById() {
        return List.of(
                DynamicTest.dynamicTest("Exist company. Ok", () -> {
                    Mockito.when(companiesRepository.existsById(Mockito.anyLong())).thenReturn(true);
                    Assertions.assertTrue(aboutService.deleteCompanyById(1L));
                    Mockito.verify(companiesRepository, Mockito.times(1)).deleteById(Mockito.anyLong());
                }),
                DynamicTest.dynamicTest("Not Exist company. NotOk", () -> {
                    Mockito.when(companiesRepository.existsById(Mockito.anyLong())).thenReturn(false);
                    Assertions.assertFalse(aboutService.deleteCompanyById(0L));
                    Mockito.verify(companiesRepository, Mockito.times(1)).deleteById(Mockito.anyLong());
                })
        );
    }

    @Test @DisplayName("Not implemented yet")
    void getAllContacts() {
        Mockito.when(contactsRepository.findAll(Mockito.any(Sort.class))).thenReturn(contacts);
        List<ContactDto> contactDtos = aboutService.getAllContacts();
        Assertions.assertAll(
                () -> Assertions.assertNotNull(contactDtos),
                () -> Assertions.assertEquals(contacts.size(), contactDtos.size()),
                () -> Assertions.assertEquals(ContactDto.class, contactDtos.get(0).getClass()),
                () -> Assertions.assertEquals(contactDtos.get(0).id(), contacts.get(0).getId()),
                () -> Assertions.assertEquals(contactDtos.get(0).type(), contacts.get(0).getType()),
                () -> Assertions.assertEquals(contactDtos.get(0).address(), contacts.get(0).getAddress())
        );
        Mockito.verify(contactsRepository, Mockito.times(1)).findAll(Mockito.any(Sort.class));
    }

    @Test @DisplayName("Test getAllSkills")
    void getAllSkills() {
        Mockito.when(skillsRepository.findAll(Mockito.any(Sort.class))).thenReturn(skills);
        List<SkillDto> skillDtos = aboutService.getAllSkills();
        Assertions.assertAll(
                () -> Assertions.assertNotNull(skillDtos),
                () -> Assertions.assertEquals(skills.size(), skillDtos.size()),
                () -> Assertions.assertEquals(SkillDto.class, skillDtos.get(0).getClass()),
                () -> Assertions.assertEquals(skillDtos.get(0).id(), skills.get(0).getId()),
                () -> Assertions.assertEquals(skillDtos.get(0).title(), skills.get(0).getTitle()),
                () -> Assertions.assertEquals(skillDtos.get(0).description(), skills.get(0).getDescription())
        );
        Mockito.verify(skillsRepository, Mockito.times(1)).findAll(Mockito.any(Sort.class));
    }

    @TestFactory @DisplayName("Test getSortedSkills")
    Iterable<DynamicTest> getSortedSkills() {
        return List.of(
                DynamicTest.dynamicTest( "Sort By Id. Ok", () -> {
                    Mockito.when(skillsRepository.findAll(Mockito.any(Sort.class))).thenReturn(skills);
                    List<SkillDto> sortedById = aboutService.getSortedSkills("id");
                    Assertions.assertAll(
                    () -> Assertions.assertNotNull(sortedById),
                    () -> Assertions.assertEquals(skills.size(), sortedById.size()),
                    () -> Assertions.assertEquals(SkillDto.class, sortedById.get(0).getClass()),
                    () -> Assertions.assertEquals(sortedById.get(0).id(), skills.get(0).getId()),
                    () -> Assertions.assertEquals(sortedById.get(0).title(), skills.get(0).getTitle()),
                    () -> Assertions.assertEquals(sortedById.get(0).description(), skills.get(0).getDescription())
                    );
                    Mockito.verify(skillsRepository, Mockito.times(1)).findAll(Mockito.any(Sort.class));
                } ),
                DynamicTest.dynamicTest("Sort By Title. Ok", () -> {
                    Mockito.when(skillsRepository.findAll(Mockito.any(Sort.class))).thenReturn(skills);
                    List<SkillDto> sortedById = aboutService.getSortedSkills("title");
                    Assertions.assertAll(
                    () -> Assertions.assertNotNull(sortedById),
                    () -> Assertions.assertEquals(skills.size(), sortedById.size()),
                    () -> Assertions.assertEquals(SkillDto.class, sortedById.get(0).getClass()),
                    () -> Assertions.assertEquals(sortedById.get(0).id(), skills.get(0).getId()),
                    () -> Assertions.assertEquals(sortedById.get(0).title(), skills.get(0).getTitle()),
                    () -> Assertions.assertEquals(sortedById.get(0).description(), skills.get(0).getDescription())
                    );
                    Mockito.verify(skillsRepository, Mockito.times(2)).findAll(Mockito.any(Sort.class));
                }),
                DynamicTest.dynamicTest("With empty string. NotOk", () -> {
                    Assertions.assertThrows(
                            IllegalArgumentException.class,
                            () -> aboutService.getSortedSkills(""));
                    Mockito.verify(skillsRepository, Mockito.times(2)).findAll(Mockito.any(Sort.class));
                }),
                DynamicTest.dynamicTest("With NULL. NotOk", () -> {
                    Assertions.assertThrows(
                            IllegalArgumentException.class,
                            () -> aboutService.getSortedSkills(null));
                    Mockito.verify(skillsRepository, Mockito.times(2)).findAll(Mockito.any(Sort.class));
                })
        );
    }

    @TestFactory @DisplayName("Test saveSkill")
    Iterable<DynamicTest> saveSkill() {
        return List.of(
                DynamicTest.dynamicTest( "With wright parameter. Ok", () -> {
                    Mockito.when(skillsRepository.saveAndFlush(Mockito.any(Skill.class))).thenReturn(skills.get(0));
                    SkillDto skillDto = aboutService.saveSkill(
                            new SkillDto(null, skills.get(0).getTitle(), skills.get(0).getDescription())
                    );
                    Assertions.assertAll(
                            () -> Assertions.assertNotNull(skillDto),
                            () -> Assertions.assertEquals(SkillDto.class, skillDto.getClass()),
                            () -> Assertions.assertEquals(skills.get(0).getId(), skillDto.id()),
                            () -> Assertions.assertEquals(skills.get(0).getTitle(), skillDto.title()),
                            () -> Assertions.assertEquals(skills.get(0).getDescription(), skillDto.description())
                    );
                    Mockito.verify(skillsRepository, Mockito.times(1)).saveAndFlush(Mockito.any(Skill.class));
                }),
                DynamicTest.dynamicTest("With NULL. NotOk", () -> {
                    Mockito.when(skillsRepository.saveAndFlush(Mockito.any())).thenReturn(null);
                    SkillDto skillDto = aboutService.saveSkill(null);
                    Assertions.assertNull(skillDto);
                    Mockito.verify(skillsRepository, Mockito.times(2)).saveAndFlush(Mockito.any());
                })
        );

    }

    @TestFactory @DisplayName("Test deleteSkills")
    Iterable<DynamicTest> deleteSkillsById() {
        return List.of(
                DynamicTest.dynamicTest( "With existed record. Ok", () -> {
                    Mockito.when(skillsRepository.existsById(Mockito.anyLong())).thenReturn(true);
                    Assertions.assertTrue(aboutService.deleteSkillById(1L));
                    Mockito.verify(skillsRepository, Mockito.times(1)).deleteById(Mockito.anyLong());
                }),
                DynamicTest.dynamicTest( "With not existed record. NotOk", () -> {
                    Mockito.when(skillsRepository.existsById(Mockito.anyLong())).thenReturn(false);
                    Assertions.assertFalse(aboutService.deleteSkillById(0L));
                    Mockito.verify(skillsRepository, Mockito.times(1)).deleteById(Mockito.anyLong());
                })
        );
    }

    private void initCompanies(){
        for (int i = 1; i < 6; ++i) {
            Company company = new Company();
            company.setId((long) i);
            company.setTitle(faker.company().name());
            company.setPosition(faker.company().profession());
            companies.add(company);
        }
    }

    private void initContacts(){
        Contact contact1 = new Contact();
        contact1.setType("Instagram");
        contact1.setAddress(faker.internet().domainName());
        contacts.add(contact1);
        Contact contact2 = new Contact();
        contact2.setType("Email");
        contact2.setAddress(faker.internet().emailAddress());
        contacts.add(contact2);
        Contact contact3 = new Contact();
        contact3.setType("Twitter");
        contact3.setAddress(faker.twitter().userName());
        contacts.add(contact3);
        Contact contact4 = new Contact();
        contact4.setType("Facebook");
        contact4.setAddress(faker.twitter().userName());
        contacts.add(contact4);
    }

    private void initSkills(){
        for (int i = 1; i < 6; ++i) {
            Skill skill = new Skill();
            skill.setId((long) i);
            skill.setTitle(faker.job().keySkills());
            skill.setDescription(faker.dune().quote());
            skills.add(skill);
        }
    }

}
