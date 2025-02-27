package s_lab.sichniy_andriy.portier_digital.repository;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import s_lab.sichniy_andriy.portier_digital.model.Company;


public interface CompaniesRepository extends JpaRepository<Company, Long> {

    @Override
    List<Company> findAll(Sort sort);

    Page<Company> findAll(Pageable pageable);

    @Override
    <S extends Company> S saveAndFlush(S entity);

    @Override
    boolean existsById(Long aLong);

    @Override
    void deleteById(Long aLong);

}
