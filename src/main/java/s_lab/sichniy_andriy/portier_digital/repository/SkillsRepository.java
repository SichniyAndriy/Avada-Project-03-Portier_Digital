package s_lab.sichniy_andriy.portier_digital.repository;

import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import s_lab.sichniy_andriy.portier_digital.model.Skill;


public interface SkillsRepository extends JpaRepository<Skill, Long> {

    @Override @NonNull
    List<Skill> findAll(@NonNull Sort sort);

    boolean existsById(long id);

    void deleteById(long id);

    @Override @NonNull
    <S extends Skill> S saveAndFlush(@NonNull S entity);

}
