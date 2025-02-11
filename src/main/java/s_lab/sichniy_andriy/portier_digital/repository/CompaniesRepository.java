package s_lab.sichniy_andriy.portier_digital.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import s_lab.sichniy_andriy.portier_digital.model.Companies;


public interface CompaniesRepository extends JpaRepository<Companies, Long> {

    @Override
    List<Companies> findAll();

}