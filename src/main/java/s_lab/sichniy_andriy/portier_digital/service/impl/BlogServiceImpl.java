package s_lab.sichniy_andriy.portier_digital.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import s_lab.sichniy_andriy.portier_digital.model.Article;
import s_lab.sichniy_andriy.portier_digital.model.dto.ArticleDto;
import s_lab.sichniy_andriy.portier_digital.model.mapper.ArticleMapper;
import s_lab.sichniy_andriy.portier_digital.repository.ArticlesRepository;
import s_lab.sichniy_andriy.portier_digital.service.BlogService;


@Service
public class BlogServiceImpl implements BlogService {

    private final ArticlesRepository articlesRepository;
    private final ArticleMapper articleMapper;

    public BlogServiceImpl(
            @Autowired ArticlesRepository articlesRepository,
            @Autowired ArticleMapper articleMapper
    ) {
        this.articlesRepository = articlesRepository;
        this.articleMapper = articleMapper;
    }


    @Override
    public List<ArticleDto> getAllArticles() {
        List<Article> articles =
                articlesRepository.findAll(Sort.by(Direction.DESC, "id"));
        return articleMapper.toDto(articles);
    }

}
