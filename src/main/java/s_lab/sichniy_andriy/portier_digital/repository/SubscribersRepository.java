package s_lab.sichniy_andriy.portier_digital.repository;

import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import s_lab.sichniy_andriy.portier_digital.model.Subscriber;

public interface SubscribersRepository extends JpaRepository<Subscriber, Long> {

    @Override @NonNull
    List<Subscriber> findAll(@NonNull Sort sort);

    @Override
    boolean existsById(@NonNull Long aLong);

    @Override
    void deleteById(@NonNull Long aLong);

}
