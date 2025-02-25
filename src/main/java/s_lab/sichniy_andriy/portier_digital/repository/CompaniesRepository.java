package s_lab.sichniy_andriy.portier_digital.repository;

import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import s_lab.sichniy_andriy.portier_digital.model.Company;


public interface CompaniesRepository extends JpaRepository<Company, Long> {

    @Override
    List<Company> findAll(Sort sort);

    @Override
    <S extends Company> S saveAndFlush(S entity);

    @Override
    boolean existsById(Long aLong);

    @Override
    void deleteById(Long aLong);

}
