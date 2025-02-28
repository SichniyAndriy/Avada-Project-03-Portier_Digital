package s_lab.sichniy_andriy.portier_digital.repository;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import s_lab.sichniy_andriy.portier_digital.model.Company;


public interface CompaniesRepository extends JpaRepository<Company, Long> {

    @Override @NonNull
    List<Company> findAll(@NonNull Sort sort);

    @NonNull
    Page<Company> findAll(@NonNull Pageable pageable);

    @Override @NonNull
    <S extends Company> S saveAndFlush(@NonNull S entity);

    @Override
    boolean existsById(@NonNull Long aLong);

    @Override
    void deleteById(@NonNull Long aLong);

}
