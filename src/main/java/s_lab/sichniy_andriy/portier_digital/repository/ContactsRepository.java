package s_lab.sichniy_andriy.portier_digital.repository;

import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import s_lab.sichniy_andriy.portier_digital.model.Contact;


public interface ContactsRepository extends JpaRepository<Contact, Long> {

    @Override @NonNull
    List<Contact> findAll(@NonNull Sort sort);

}
