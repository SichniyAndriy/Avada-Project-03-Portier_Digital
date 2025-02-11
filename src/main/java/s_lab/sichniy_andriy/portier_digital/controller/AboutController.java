package s_lab.sichniy_andriy.portier_digital.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import s_lab.sichniy_andriy.portier_digital.service.AboutService;

@Controller
@RequestMapping( "/about" )
public class AboutController {

    private final AboutService aboutService;

    public AboutController(
            @Autowired AboutService aboutService
    ) {
        this.aboutService = aboutService;
    }

    @GetMapping( { "", "/" } )
    public String about() {
        return "about";
    }

}
