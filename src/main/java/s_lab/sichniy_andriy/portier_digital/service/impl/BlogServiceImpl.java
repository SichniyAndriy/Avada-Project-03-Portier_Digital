package s_lab.sichniy_andriy.portier_digital.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
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


    @Override
    public List<ArticleDto> getAllArticles() {
        List<Article> articles =
                articlesRepository.findAll(Sort.by(Direction.DESC, "id"));
        return articleMapper.toDto(articles);
    }

    @Override
    public List<SubscriberDto> getAllSubscribers() {
        List<Subscriber> subscribers = subscribersRepository.findAll(Sort.by(Direction.ASC, "email"));
        return subscriberMapper.toDto(subscribers);
    }

}
