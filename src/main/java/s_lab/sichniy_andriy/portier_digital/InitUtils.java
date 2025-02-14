package s_lab.sichniy_andriy.portier_digital;


import java.util.ArrayList;
import java.util.List;
import net.datafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import s_lab.sichniy_andriy.portier_digital.model.Article;
import s_lab.sichniy_andriy.portier_digital.model.Company;
import s_lab.sichniy_andriy.portier_digital.model.Contact;
import s_lab.sichniy_andriy.portier_digital.model.Project;
import s_lab.sichniy_andriy.portier_digital.model.Skill;
import s_lab.sichniy_andriy.portier_digital.model.Subscriber;
import s_lab.sichniy_andriy.portier_digital.repository.ArticlesRepository;
import s_lab.sichniy_andriy.portier_digital.repository.CompaniesRepository;
import s_lab.sichniy_andriy.portier_digital.repository.ContactsRepository;
import s_lab.sichniy_andriy.portier_digital.repository.ProjectRepository;
import s_lab.sichniy_andriy.portier_digital.repository.SkillsRepository;
import s_lab.sichniy_andriy.portier_digital.repository.SubscribersRepository;


@Component
public class InitUtils implements CommandLineRunner {

    private final CompaniesRepository companiesRepository;
    private final ContactsRepository contactsRepository;
    private final ArticlesRepository articlesRepository;
    private final ProjectRepository projectRepository;
    private final SkillsRepository skillsRepository;
    private final SubscribersRepository subscribersRepository;

    private final Faker faker;

    public InitUtils(
            @Autowired ArticlesRepository articlesRepository,
            @Autowired CompaniesRepository companiesRepository,
            @Autowired ContactsRepository contactsRepository,
            @Autowired ProjectRepository projectRepository,
            @Autowired SkillsRepository skillsRepository,
            @Autowired SubscribersRepository subscribersRepository
    ) {
        this.faker = new Faker();
        this.articlesRepository = articlesRepository;
        this.companiesRepository = companiesRepository;
        this.contactsRepository = contactsRepository;
        this.projectRepository = projectRepository;
        this.skillsRepository = skillsRepository;
        this.subscribersRepository = subscribersRepository;
    }


    @Override
    public void run(String... args) {
        initArticles(faker.number().numberBetween(15, 25));
        initCompanies(faker.number().numberBetween(5, 10));
        initContacts();
        initProjects(faker.number().numberBetween(5, 10));
        initSkills(faker.number().numberBetween(9, 12));
        initSubscribers(faker.number().numberBetween(20, 30));
    }

    private void initArticles(int n) {
        List<Article> articles = new ArrayList<>();
        for (int i = 0; i < n; ++i) {
            Article article = new Article();
            article.setTitle(faker.book().title());
            article.setContent(faker.text().text(500, 2000));
            articles.add(article);
        }
        articlesRepository.saveAllAndFlush(articles);
    }

    private void initCompanies(int n) {
        List<Company> companies = new ArrayList<>();
        for (int i = 0; i < n; ++i) {
            Company company = new Company();
            company.setTitle(faker.company().name());
            company.setPosition(faker.hacker().adjective() + " " + faker.hacker().noun() + " " + faker.hacker().verb());
            companies.add(company);
        }
        companiesRepository.saveAllAndFlush(companies);
    }

    private void initContacts() {
        List<Contact> contacts = new ArrayList<>();
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
        contactsRepository.saveAllAndFlush(contacts);
    }

    private void initProjects(int n) {
        List<Project> projects = new ArrayList<>();
        for (int i = 0; i < n; ++i) {
            Project project = new Project();
            project.setTitle(faker.commerce().brand());
            project.setDescription(faker.text().text(100, 200));
            projects.add(project);
        }
        projectRepository.saveAllAndFlush(projects);
    }

    private void initSkills(int n) {
        List<Skill> skills = new ArrayList<>();
        for (int i = 0; i < n; ++i) {
            Skill skill = new Skill();
            String tmpTitle;
            boolean flag;
            do {
                tmpTitle = faker.job().keySkills();
                String finalTmpTitle = tmpTitle;
                flag = skills.stream().anyMatch(item -> item.getTitle().equals(finalTmpTitle));
            } while (flag);
            skill.setTitle(tmpTitle);
            skill.setDescription(faker.yoda().quote());
            skills.add(skill);
        }
        skillsRepository.saveAllAndFlush(skills);
    }

    private void initSubscribers(int n) {
        List<Subscriber> subscribers = new ArrayList<>();
        for (int j = 0; j < n; ++j) {
            Subscriber subscriber = new Subscriber();
            subscriber.setEmail(faker.internet().emailAddress());
            subscribers.add(subscriber);
        }
        subscribersRepository.saveAllAndFlush(subscribers);
    }

}
