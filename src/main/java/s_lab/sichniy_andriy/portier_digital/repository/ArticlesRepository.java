package s_lab.sichniy_andriy.portier_digital.repository;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.lang.NonNull;
import s_lab.sichniy_andriy.portier_digital.model.Article;

public interface ArticlesRepository extends JpaRepository<Article, Long>, JpaSpecificationExecutor<Article> {

    @Override @NonNull
    List<Article> findAll(@NonNull Sort sort);

    @NonNull
    Page<Article> findAll( @NonNull Pageable pageable);

    long count();

    boolean existsById(@NonNull Long id);

    void deleteById(@NonNull Long id);

    @Override @NonNull
    <S extends Article> S save(@NonNull S entity);

}
