package s_lab.sichniy_andriy.portier_digital.repository;

import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import s_lab.sichniy_andriy.portier_digital.model.Article;

public interface ArticlesRepository extends JpaRepository<Article, Long>, JpaSpecificationExecutor<Article> {

    @Override
    List<Article> findAll(Sort sort);

}
