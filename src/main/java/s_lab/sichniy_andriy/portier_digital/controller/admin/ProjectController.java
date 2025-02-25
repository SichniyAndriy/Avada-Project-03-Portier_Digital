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
import s_lab.sichniy_andriy.portier_digital.model.dto.ProjectDto;
import s_lab.sichniy_andriy.portier_digital.service.PortfolioService;
import s_lab.sichniy_andriy.portier_digital.service.ServiceUtils;

@Controller
@RequestMapping("/admin")
public class ProjectController {

    private final PortfolioService portfolioService;

    public ProjectController(
            @Autowired PortfolioService portfolioService
    ) {
        this.portfolioService = portfolioService;
    }


    @GetMapping( { "", "/","/projects", "/projects/" } )
    public ModelAndView index(
            ModelAndView modelAndView,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String col
    ) {
        Page<ProjectDto> projectDtoList = portfolioService.getProjectPage(page, size, col);
        modelAndView.setViewName("admin/projects");
        modelAndView.addObject("projects", projectDtoList);
        return modelAndView;
    }

    @GetMapping( { "/projects/{id}", "/projects/{id}/" } )
    public ResponseEntity<ProjectDto> projectById(
            @PathVariable int id
    ) {
        ProjectDto projectById = portfolioService.getProjectById(id);
        if (projectById == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(projectById);
    }

    @DeleteMapping( { "/projects/delete/{id}", "/projects/delete/{id}/" } )
    public ResponseEntity<ProjectDto> deleteProjectById(
            @PathVariable long id
    ) {
        if (portfolioService.deleteById(id)) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/projects/save")
    public ResponseEntity<Long> saveProject(
            @ModelAttribute ProjectDto projectDto
    ) {
        long id = portfolioService.saveProject(projectDto);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(id);
    }

    @PostMapping("/projects/image/save")
    public ResponseEntity<String> saveProjectImage(
            @RequestParam MultipartFile file,
            @RequestParam String timestamp,
            @RequestParam String ext
    ) throws IOException {
        String res = ServiceUtils.uploadImageOnServer(file, file.getName(), timestamp, ext, "projects");
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

}
