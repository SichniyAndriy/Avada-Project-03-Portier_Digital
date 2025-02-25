package s_lab.sichniy_andriy.portier_digital.repository;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import s_lab.sichniy_andriy.portier_digital.model.Project;

public interface ProjectRepository extends JpaRepository<Project, Long>, JpaSpecificationExecutor<Project> {

    @Override
    List<Project> findAll(Sort sort);

    Page<Project> findAll(Pageable pageable);

    long count();

    List<Project> findAllBy(PageRequest pageRequest);

    boolean existsById(Long id);

    void deleteById(Long id);

    @Override
    <S extends Project> S save(S entity);

}
