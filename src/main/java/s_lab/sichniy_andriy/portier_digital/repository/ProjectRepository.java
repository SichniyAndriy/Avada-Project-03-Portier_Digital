package s_lab.sichniy_andriy.portier_digital.repository;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.lang.NonNull;
import s_lab.sichniy_andriy.portier_digital.model.Project;

public interface ProjectRepository extends JpaRepository<Project, Long>, JpaSpecificationExecutor<Project> {

    @Override @NonNull
    List<Project> findAll(@NonNull Sort sort);

 @NonNull
    Page<Project> findAll(@NonNull Pageable pageable);

    long count();

    List<Project> findAllBy(PageRequest pageRequest);

    boolean existsById(@NonNull Long id);

    void deleteById(@NonNull Long id);

    @Override @NonNull
    <S extends Project> S save(@NonNull S entity);

}
