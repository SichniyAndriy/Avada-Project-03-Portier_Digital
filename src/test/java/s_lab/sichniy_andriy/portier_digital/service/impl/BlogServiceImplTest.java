package s_lab.sichniy_andriy.portier_digital.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import net.datafaker.Faker;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import s_lab.sichniy_andriy.portier_digital.model.Article;
import s_lab.sichniy_andriy.portier_digital.model.Subscriber;
import s_lab.sichniy_andriy.portier_digital.model.dto.ArticleDto;
import s_lab.sichniy_andriy.portier_digital.model.dto.SubscriberDto;
import s_lab.sichniy_andriy.portier_digital.model.mapper.ArticleMapper;
import s_lab.sichniy_andriy.portier_digital.model.mapper.SubscriberMapper;
import s_lab.sichniy_andriy.portier_digital.repository.ArticlesRepository;
import s_lab.sichniy_andriy.portier_digital.repository.SubscribersRepository;

@ExtendWith(MockitoExtension.class)
class BlogServiceImplTest {

    private BlogServiceImpl blogService;

    @Mock private ArticlesRepository articlesRepository;
    @Mock private SubscribersRepository subscribersRepository;

    private final static int ARRAY_SIZE = 5;
    private final List<Article> articles = new ArrayList<>();
    private final List<Subscriber> subscribers = new ArrayList<>();
    private final Faker faker = new Faker();


    @BeforeEach
    void setUp() {
        initArticles();
        initSubscribers();
        blogService = new BlogServiceImpl(
                articlesRepository,
                subscribersRepository,
                Mappers.getMapper(ArticleMapper.class),
                Mappers.getMapper(SubscriberMapper.class)
        );
    }

    @AfterEach
    void tearDown() {
        subscribers.clear();
        articles.clear();
    }

    @Test @DisplayName("Test getAllArticles. Ok")
    void getAllArticles() {
        Mockito.when(articlesRepository.findAll(Mockito.any(Sort.class))).thenReturn(articles);
        List<ArticleDto> articleDtos = blogService.getAllArticles();
        MatcherAssert.assertThat(articleDtos, Matchers.hasSize(articles.size()));
        MatcherAssert.assertThat(articleDtos.get(0), Matchers.instanceOf(ArticleDto.class));
        MatcherAssert.assertThat(articleDtos.get(1), Matchers.instanceOf(ArticleDto.class));
        MatcherAssert.assertThat(articleDtos.get(2), Matchers.instanceOf(ArticleDto.class));
        MatcherAssert.assertThat(articleDtos.get(3), Matchers.instanceOf(ArticleDto.class));
        MatcherAssert.assertThat(articleDtos.get(4), Matchers.instanceOf(ArticleDto.class));
        Assertions.assertAll(
                () -> Assertions.assertEquals(articles.get(0).getId(), articleDtos.get(0).id()),
                () -> Assertions.assertEquals(articles.get(0).getTitle(), articleDtos.get(0).title())
        );
        Mockito.verify(articlesRepository, Mockito.times(1)).findAll(Mockito.any(Sort.class));
    }

    @TestFactory @DisplayName("Test getArticlePage")
    Iterable<DynamicTest> getArticlePage() {
        return List.of(
                DynamicTest.dynamicTest("With wright parameters 5 elems", () -> {
                    Mockito.when(articlesRepository.count()).thenReturn((long) ARRAY_SIZE);
                    Mockito.when(articlesRepository.findAll(Mockito.any(Pageable.class)))
                            .thenReturn(new PageImpl<>(articles));
                    Page<ArticleDto> articleDtoPage = blogService.getArticlePage(1, ARRAY_SIZE, "id");
                    MatcherAssert.assertThat(articleDtoPage, Matchers.notNullValue());
                    MatcherAssert.assertThat(articleDtoPage, Matchers.instanceOf(Page.class));
                    MatcherAssert.assertThat(articleDtoPage.getTotalElements(), Matchers.is((long) ARRAY_SIZE));
                    Mockito.verify(articlesRepository, Mockito.times(1)).findAll(Mockito.any(Pageable.class));
                }),
                DynamicTest.dynamicTest("With wright parameters 6 elems", () -> {
                    Mockito.when(articlesRepository.count()).thenReturn((long) ARRAY_SIZE);
                    Mockito.when(articlesRepository.findAll(Mockito.any(Pageable.class)))
                            .thenReturn(new PageImpl<>(articles));
                    Page<ArticleDto> articleDtoPage = blogService.getArticlePage(1, ARRAY_SIZE + 1, "id");
                    MatcherAssert.assertThat(articleDtoPage, Matchers.notNullValue());
                    MatcherAssert.assertThat(articleDtoPage, Matchers.instanceOf(Page.class));
                    MatcherAssert.assertThat(articleDtoPage.getTotalElements(), Matchers.is((long) ARRAY_SIZE));
                    Mockito.verify(articlesRepository, Mockito.times(2)).findAll(Mockito.any(Pageable.class));
                }),
                DynamicTest.dynamicTest("With higher number of page", () -> {
                    Mockito.when(articlesRepository.count()).thenReturn((long) ARRAY_SIZE);
                    Mockito.when(articlesRepository.findAll(Mockito.any(Pageable.class)))
                            .thenReturn(new PageImpl<>(articles));
                    Page<ArticleDto> articleDtoPage = blogService.getArticlePage(10, ARRAY_SIZE, "id");
                    MatcherAssert.assertThat(articleDtoPage, Matchers.notNullValue());
                    MatcherAssert.assertThat(articleDtoPage, Matchers.instanceOf(Page.class));
                    MatcherAssert.assertThat(articleDtoPage.getTotalElements(), Matchers.is((long) ARRAY_SIZE));
                    Mockito.verify(articlesRepository, Mockito.times(3)).findAll(Mockito.any(Pageable.class));
                }),
                DynamicTest.dynamicTest("With not existed page 0", () -> {
                    Assertions.assertThrows(IllegalArgumentException.class, () -> {
                        blogService.getArticlePage(0, 5, "id");
                    });
                    Mockito.verify(articlesRepository, Mockito.times(3)).findAll(Mockito.any(Pageable.class));
                }),
                DynamicTest.dynamicTest("With not existed negative page -1", () -> {
                    Assertions.assertThrows(IllegalArgumentException.class, () -> {
                        blogService.getArticlePage(-1, 5, "id");
                    });
                    Mockito.verify(articlesRepository, Mockito.times(3)).findAll(Mockito.any(Pageable.class));
                })
        );
    }

    @TestFactory @DisplayName("Test getArticleById")
    Iterable<DynamicTest> getArticleById() {
        return List.of(
                DynamicTest.dynamicTest("With wright parameter id 1", () -> {
                    Mockito.when(articlesRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(articles.get(0)));

                    ArticleDto articleDtoById = blogService.getArticleById(1L);

                    MatcherAssert.assertThat(articleDtoById, Matchers.notNullValue());
                    MatcherAssert.assertThat(articleDtoById, Matchers.instanceOf(ArticleDto.class));
                    MatcherAssert.assertThat(articleDtoById.id(), Matchers.is(articles.get(0).getId()));
                    MatcherAssert.assertThat(articleDtoById.title(), Matchers.is(articles.get(0).getTitle()));
                    Mockito.verify(articlesRepository, Mockito.times(1)).findById(Mockito.anyLong());
                }),
                DynamicTest.dynamicTest("With wrong parameter id 0", () -> {
                    Mockito.when(articlesRepository.findById(Mockito.anyLong())).thenReturn(Optional.empty());
                    ArticleDto articleDtoById = blogService.getArticleById(0L);
                    MatcherAssert.assertThat(articleDtoById, Matchers.nullValue());
                    Mockito.verify(articlesRepository, Mockito.times(2)).findById(Mockito.anyLong());
                })
        );
    }

    @TestFactory @DisplayName("Test saveArticle")
    Iterable<DynamicTest> saveArticle() {
        return List.of(
                DynamicTest.dynamicTest("With wright parameter", () -> {
                    Mockito.when(articlesRepository.save(Mockito.any(Article.class))).thenReturn(articles.get(0));
                    long savedArticleId = blogService.saveArticle(new ArticleDto(
                            null,
                            articles.get(0).getTitle(),
                            articles.get(0).getContent(),
                            articles.get(0).getImagePath())
                    );
                    MatcherAssert.assertThat(savedArticleId, Matchers.is(articles.get(0).getId()));
                    Mockito.verify(articlesRepository, Mockito.times(1)).save(Mockito.any(Article.class));
                }),
                DynamicTest.dynamicTest("With wrong parameter null", () -> {
                    Assertions.assertThrows(NullPointerException.class, () -> {
                        blogService.saveArticle(null);
                    });
                    Mockito.verify(articlesRepository, Mockito.times(2)).save(Mockito.any());
                })
        );
    }

    @TestFactory @DisplayName("Test deleteArticle")
    Iterable<DynamicTest> deleteArticle() {
        return List.of(
                DynamicTest.dynamicTest("With wright parameter id", () -> {
                    Mockito.when(articlesRepository.existsById(Mockito.anyLong())).thenReturn(true);
                    Mockito.doNothing().when(articlesRepository).deleteById(Mockito.anyLong());
                    boolean res = blogService.deleteArticle(1L);
                    MatcherAssert.assertThat(res, Matchers.is(true));
                    Mockito.verify(articlesRepository, Mockito.times(1)).deleteById(Mockito.anyLong());
                }),
                DynamicTest.dynamicTest("With wrong parameter null", () -> {
                    Mockito.when(articlesRepository.existsById(Mockito.anyLong())).thenReturn(false);
                    boolean res = blogService.deleteArticle(0L);
                    MatcherAssert.assertThat(res, Matchers.is(false));
                    Mockito.verify(articlesRepository, Mockito.times(1)).deleteById(Mockito.anyLong());
                })
        );
    }

    @Test @DisplayName("Test getAllSubscribers")
    void getAllSubscribers() {
        Mockito.when(subscribersRepository.findAll(Mockito.any(Sort.class))).thenReturn(subscribers);
        List<SubscriberDto> subscriberDtoList = blogService.getAllSubscribers();
        MatcherAssert.assertThat(subscriberDtoList, Matchers.notNullValue());
        MatcherAssert.assertThat(subscriberDtoList.get(0), Matchers.instanceOf(SubscriberDto.class));
        MatcherAssert.assertThat(subscriberDtoList, Matchers.hasSize(subscribers.size()));
        Mockito.verify(subscribersRepository, Mockito.times(1)).findAll(Mockito.any(Sort.class));
    }

    @TestFactory @DisplayName("Test getSubscriberPage")
    Iterable<DynamicTest> getSubscriberPage() {
        return List.of(
                DynamicTest.dynamicTest("With wright parameters 5 elems", () -> {
                    Mockito.when(subscribersRepository.count()).thenReturn((long) ARRAY_SIZE);
                    Mockito.when(subscribersRepository.findAll(Mockito.any(Pageable.class)))
                            .thenReturn(new PageImpl<>(subscribers));
                    Page<SubscriberDto> subscriberDtoPage = blogService.getSubscriberPage(1, ARRAY_SIZE, "id");
                    MatcherAssert.assertThat(subscriberDtoPage, Matchers.notNullValue());
                    MatcherAssert.assertThat(subscriberDtoPage, Matchers.instanceOf(Page.class));
                    MatcherAssert.assertThat(subscriberDtoPage.getTotalElements(), Matchers.is((long) ARRAY_SIZE));
                    Mockito.verify(subscribersRepository, Mockito.times(1)).findAll(Mockito.any(Pageable.class));
                }),
                DynamicTest.dynamicTest("With wright parameters 6 elems", () -> {
                    Mockito.when(subscribersRepository.count()).thenReturn((long) ARRAY_SIZE);
                    Mockito.when(subscribersRepository.findAll(Mockito.any(Pageable.class)))
                            .thenReturn(new PageImpl<>(subscribers));
                    Page<SubscriberDto> subscriberDtoPage = blogService.getSubscriberPage(1, ARRAY_SIZE + 1, "id");
                    MatcherAssert.assertThat(subscriberDtoPage, Matchers.notNullValue());
                    MatcherAssert.assertThat(subscriberDtoPage, Matchers.instanceOf(Page.class));
                    MatcherAssert.assertThat(subscriberDtoPage.getTotalElements(), Matchers.is((long) ARRAY_SIZE));
                    Mockito.verify(subscribersRepository, Mockito.times(2)).findAll(Mockito.any(Pageable.class));
                }),
                DynamicTest.dynamicTest("With higher number of page", () -> {
                    Mockito.when(subscribersRepository.count()).thenReturn(5L);
                    Mockito.when(subscribersRepository.findAll(Mockito.any(Pageable.class)))
                            .thenReturn(new PageImpl<>(subscribers));
                    Page<SubscriberDto> subscriberPage = blogService.getSubscriberPage(10, ARRAY_SIZE, "id");
                    MatcherAssert.assertThat(subscriberPage, Matchers.notNullValue());
                    MatcherAssert.assertThat(subscriberPage, Matchers.instanceOf(Page.class));
                    MatcherAssert.assertThat(subscriberPage.getTotalElements(), Matchers.is((long) ARRAY_SIZE));
                    Mockito.verify(subscribersRepository, Mockito.times(3)).findAll(Mockito.any(Pageable.class));
                }),
                DynamicTest.dynamicTest("With not existed page 0", () -> {
                    Assertions.assertThrows(IllegalArgumentException.class, () -> {
                        blogService.getSubscriberPage(0, ARRAY_SIZE, "id");
                    });
                    Mockito.verify(subscribersRepository, Mockito.times(3)).findAll(Mockito.any(Pageable.class));
                }),
                DynamicTest.dynamicTest("With not existed negative page -1", () -> {
                    Assertions.assertThrows(IllegalArgumentException.class, () -> {
                        blogService.getSubscriberPage(-1, ARRAY_SIZE, "id");
                    });
                    Mockito.verify(subscribersRepository, Mockito.times(3)).findAll(Mockito.any(Pageable.class));
                })
        );
    }

    @TestFactory @DisplayName("Test saveSubscriber")
    Iterable<DynamicTest> saveSubscriber() {
        return List.of(
                DynamicTest.dynamicTest("With wright parameter", () -> {
                    Mockito.when(subscribersRepository.save(Mockito.any(Subscriber.class))).thenReturn(subscribers.get(0));
                    long saveSubscriberId = blogService.saveSubscriber(new SubscriberDto(
                            null, subscribers.get(0).getEmail()
                    ));
                    MatcherAssert.assertThat(saveSubscriberId, Matchers.is(subscribers.get(0).getId()));
                    Mockito.verify(subscribersRepository, Mockito.times(1)).save(Mockito.any(Subscriber.class));
                }),
                DynamicTest.dynamicTest("With wrong parameter null", () -> {
                    Assertions.assertThrows(NullPointerException.class, () -> {
                        blogService.saveSubscriber(null);
                    });
                    Mockito.verify(subscribersRepository, Mockito.times(2)).save(Mockito.any());
                })
        );
    }

    @TestFactory @DisplayName("Test deleteSubscriber")
    Iterable<DynamicTest> deleteSubscriber() {
        return List.of(
                DynamicTest.dynamicTest("With wright parameter id", () -> {
                    Mockito.when(subscribersRepository.existsById(Mockito.anyLong())).thenReturn(true);
                    Mockito.doNothing().when(subscribersRepository).deleteById(Mockito.anyLong());
                    boolean res = blogService.deleteSubscriber(1L);
                    MatcherAssert.assertThat(res, Matchers.is(true));
                    Mockito.verify(subscribersRepository, Mockito.times(1)).deleteById(Mockito.anyLong());
                }),
                DynamicTest.dynamicTest("With wrong parameter null", () -> {
                    Mockito.when(subscribersRepository.existsById(Mockito.anyLong())).thenReturn(false);
                    boolean res = blogService.deleteSubscriber(0L);
                    MatcherAssert.assertThat(res, Matchers.is(false));
                    Mockito.verify(subscribersRepository, Mockito.times(1)).deleteById(Mockito.anyLong());
                })
        );
    }

    private void initSubscribers() {
        for (int i = 1; i < 6; ++i) {
            Subscriber subscriber = new Subscriber();
            subscriber.setId((long) i);
            subscriber.setEmail(faker.internet().emailAddress());
            subscribers.add(subscriber);
        }
    }

    private void initArticles() {
        for (int i = 1; i < 6; ++i) {
            Article article = new Article();
            article.setId((long) i);
            article.setTitle(faker.book().title());
            article.setContent(faker.lorem().sentence(50, 50));
            articles.add(article);
        }
    }

}
