package s_lab.sichniy_andriy.portier_digital.repository;

import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import s_lab.sichniy_andriy.portier_digital.model.Skill;


public interface SkillsRepository extends JpaRepository<Skill, Long> {

    @Override
    List<Skill> findAll(Sort sort);

}
