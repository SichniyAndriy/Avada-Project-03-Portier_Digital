package s_lab.sichniy_andriy.portier_digital.controller.admin;

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
import org.springframework.web.servlet.ModelAndView;
import s_lab.sichniy_andriy.portier_digital.model.dto.SubscriberDto;
import s_lab.sichniy_andriy.portier_digital.service.BlogService;

@Controller
@RequestMapping("/admin")
public class SubscribersController {

    private final BlogService blogService;

    public SubscribersController(
            @Autowired BlogService blogService
    ) {
        this.blogService = blogService;
    }


    @GetMapping( { "/subscribers" } )
    public ModelAndView getSortedSubscribers(
            ModelAndView modelAndView,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String col
    ) {
        Page<SubscriberDto> subscriberDtoList = blogService.getSubscriberPage(page, size, col);
        modelAndView.setViewName("admin/subscribers");
        modelAndView.addObject("subscribers", subscriberDtoList);
        return modelAndView;
    }

    @PostMapping("/subscribers/save")
    public ResponseEntity<Long> saveSubscriber(
            @ModelAttribute SubscriberDto subscriberDto
    ) {
        long id = blogService.saveSubscriber(subscriberDto);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(id);
    }

    @DeleteMapping( { "/subscribers/delete/{id}", "/subscribers/delete/{id}/" } )
    public ResponseEntity<SubscriberDto> deleteSubscriber(
            @PathVariable long id
    ) {
        if (blogService.deleteSubscriber(id)) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
