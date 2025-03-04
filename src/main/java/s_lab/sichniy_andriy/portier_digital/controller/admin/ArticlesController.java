package s_lab.sichniy_andriy.portier_digital.controller.admin;


import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import s_lab.sichniy_andriy.portier_digital.model.dto.ArticleDto;
import s_lab.sichniy_andriy.portier_digital.service.BlogService;
import s_lab.sichniy_andriy.portier_digital.service.ServiceUtils;


@Controller
@RequestMapping("/admin")
public class ArticlesController {

    private final BlogService blogService;

    public ArticlesController(
            @Autowired BlogService blogService
    ) {
        this.blogService = blogService;
    }


    @GetMapping( { "/articles" } )
    public ModelAndView getSortedArticles(
            ModelAndView modelAndView,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String col
    ) {
        Page<ArticleDto> articleDtoList = blogService.getArticlePage(page, size, col);
        modelAndView.setViewName("admin/articles");
        modelAndView.addObject("articles", articleDtoList);
        return modelAndView;
    }

    @GetMapping( { "/articles/{id}", "/articles/{id}/" } )
    public ResponseEntity<ArticleDto> articleById(
            @PathVariable int id
    ) {
        ArticleDto articleById = blogService.getArticleById(id);
        if (articleById == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(articleById);
    }

    @DeleteMapping( { "/articles/delete/{id}", "/articles/delete/{id}/" } )
    public ResponseEntity<ArticleDto> deleteArticleById(
            @PathVariable long id
    ) {
        if (blogService.deleteArticle(id)) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/articles/save")
    public ResponseEntity<Long> saveArticle(
            @ModelAttribute ArticleDto articleDto
    ) {
        long id = blogService.saveArticle(articleDto);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(id);
    }

    @PostMapping("/articles/image/save")
    public ResponseEntity<String> saveArticleImage(
            @RequestParam MultipartFile file,
            @RequestParam String ext
    ) throws IOException {
        String res = ServiceUtils.saveImage(file, ext, "articles");
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

}
