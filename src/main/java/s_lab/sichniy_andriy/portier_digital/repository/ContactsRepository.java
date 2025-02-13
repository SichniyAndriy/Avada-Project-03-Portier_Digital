package s_lab.sichniy_andriy.portier_digital.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import s_lab.sichniy_andriy.portier_digital.model.Contact;


public interface ContactsRepository extends JpaRepository<Contact, Long> {}