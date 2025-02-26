package s_lab.sichniy_andriy.portier_digital.service.impl;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import s_lab.sichniy_andriy.portier_digital.model.Article;
import s_lab.sichniy_andriy.portier_digital.model.Subscriber;
import s_lab.sichniy_andriy.portier_digital.model.dto.ArticleDto;
import s_lab.sichniy_andriy.portier_digital.model.dto.SubscriberDto;
import s_lab.sichniy_andriy.portier_digital.model.mapper.ArticleMapper;
import s_lab.sichniy_andriy.portier_digital.model.mapper.SubscriberMapper;
import s_lab.sichniy_andriy.portier_digital.repository.ArticlesRepository;
import s_lab.sichniy_andriy.portier_digital.repository.SubscribersRepository;
import s_lab.sichniy_andriy.portier_digital.service.BlogService;


@Service
public class BlogServiceImpl implements BlogService {

    private final ArticlesRepository articlesRepository;
    private final SubscribersRepository subscribersRepository;

    private final ArticleMapper articleMapper;
    private final SubscriberMapper subscriberMapper;

    public BlogServiceImpl(
            @Autowired ArticlesRepository articlesRepository,
            @Autowired SubscribersRepository subscribersRepository,
            @Autowired ArticleMapper articleMapper,
            @Autowired SubscriberMapper subscriberMapper
    ) {
        this.articlesRepository = articlesRepository;
        this.subscribersRepository = subscribersRepository;
        this.articleMapper = articleMapper;
        this.subscriberMapper = subscriberMapper;
    }

 //                                 ARTICLES                                 \\
// ===========================================================================\\
    @Override
    public List<ArticleDto> getAllArticles() {
        List<Article> articles = articlesRepository.findAll(Sort.by(Direction.DESC, "id"));
        return articleMapper.toDto(articles);
    }

    @Override
    public Page<ArticleDto> getArticlePage(int page, int size, String col) {
        long amountAll = articlesRepository.count();
        long pages = amountAll / size + (amountAll % size == 0 ? 0 : 1);
        if (page > pages) {
            page = (int) pages;
        }
        Page<Article> articlePage =
                articlesRepository.findAll(PageRequest.of(--page, size, Sort.by(col)));
        return articlePage.map(articleMapper::toDto);
    }

    @Override
    public ArticleDto getArticleById(long id) {
        Optional<Article> articleOptional = articlesRepository.findById(id);
        return articleOptional.map(articleMapper::toDto).orElse(null);
    }

    @Override
    public long saveArticle(ArticleDto articleDto) {
        Article article = articleMapper.toEntity(articleDto);
        Article saved = articlesRepository.save(article);
        return saved.getId();
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public boolean deleteById(long id) {
        boolean res = articlesRepository.existsById(id);
        if (res) {
            articlesRepository.deleteById(id);
        }
        return res;
    }

// ===========================================================================\\
//                                  COMPANIES                                  \\
    @Override
    public List<SubscriberDto> getAllSubscribers() {
        List<Subscriber> subscribers = subscribersRepository.findAll(Sort.by(Direction.ASC, "email"));
        return subscriberMapper.toDto(subscribers);
    }

}
