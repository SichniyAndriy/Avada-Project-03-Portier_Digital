package s_lab.sichniy_andriy.portier_digital.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import s_lab.sichniy_andriy.portier_digital.model.Company;


public interface CompaniesRepository extends JpaRepository<Company, Long> {}