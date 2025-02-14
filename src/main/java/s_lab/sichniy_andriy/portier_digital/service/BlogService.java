package s_lab.sichniy_andriy.portier_digital.service;

import java.util.List;
import s_lab.sichniy_andriy.portier_digital.model.dto.ArticleDto;
import s_lab.sichniy_andriy.portier_digital.model.dto.SubscriberDto;


public interface BlogService {

    List<ArticleDto> getAllArticles();

    List<SubscriberDto> getAllSubscribers();

}
