package s_lab.sichniy_andriy.portier_digital.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import s_lab.sichniy_andriy.portier_digital.model.dto.ArticleDto;
import s_lab.sichniy_andriy.portier_digital.model.dto.SubscriberDto;
import s_lab.sichniy_andriy.portier_digital.service.BlogService;

@Controller
@RequestMapping( "/blog" )
public class BlogController {

    private final BlogService blogService;

    public BlogController(
            @Autowired BlogService blogService
    ) {
        this.blogService = blogService;
    }


    @GetMapping( { "", "/" } )
    public ModelAndView blog(ModelAndView modelAndView) {
        List<ArticleDto> articleDtoList = blogService.getAllArticles();
        List<SubscriberDto> subscriberDtoList = blogService.getAllSubscribers();

        modelAndView.addObject("articles", articleDtoList);
        modelAndView.addObject("subscribers", subscriberDtoList);
        modelAndView.setViewName("blog");

        return modelAndView;
    }

}
