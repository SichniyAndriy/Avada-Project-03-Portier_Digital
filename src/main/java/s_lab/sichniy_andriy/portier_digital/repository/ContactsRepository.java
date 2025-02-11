package s_lab.sichniy_andriy.portier_digital.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import s_lab.sichniy_andriy.portier_digital.model.Contact;


public interface ContactsRepository extends JpaRepository<Contact, Long> {

    @Override
    List<Contact> findAll();

}