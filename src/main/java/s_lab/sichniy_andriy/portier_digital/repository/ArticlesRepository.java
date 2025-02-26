package s_lab.sichniy_andriy.portier_digital.repository;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import s_lab.sichniy_andriy.portier_digital.model.Article;

public interface ArticlesRepository extends JpaRepository<Article, Long>, JpaSpecificationExecutor<Article> {

    @Override
    List<Article> findAll(Sort sort);

    Page<Article> findAll(Pageable pageable);

    long count();

    List<Article> findAllBy(PageRequest pageRequest);

    boolean existsById(Long id);

    void deleteById(Long id);

    @Override
    <S extends Article> S save(S entity);

}
