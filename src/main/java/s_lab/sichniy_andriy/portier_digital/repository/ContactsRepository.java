package s_lab.sichniy_andriy.portier_digital.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import s_lab.sichniy_andriy.portier_digital.model.Contacts;


public interface ContactsRepository extends JpaRepository<Contacts, Long> {

    @Override
    List<Contacts> findAll();

}