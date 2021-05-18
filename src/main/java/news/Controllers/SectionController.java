package news.Controllers;

import news.Models.Section;
import news.Models.User;
import news.Services.SectionService;
import news.Services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.List;

@Controller
@RequestMapping("/sections")
public class SectionController {
    private final SectionService sectionService;
    private final UserService userService;
    private String status;

    private void setStatus(String newStatus) {
        this.status = newStatus;
    }
    private void reloadStatus() {
        this.status = "";
    }

    public SectionController(SectionService sectionService, UserService userService) {
        this.sectionService = sectionService;
        this.userService = userService;
    }
    @GetMapping
    public String GetSections(Model model,
                              Authentication authentication){
        model.addAttribute("sections", sectionService.GetAllSections());
        model.addAttribute("status", status);
        model.addAttribute("user", GetUser(authentication));
        reloadStatus();
        return "SectionController/sections";
    }

    @GetMapping("/{id}")
    public String GetSectionNews(@PathVariable("id") int id,
                                 Authentication authentication,
                                 Model model){
        List<Section> aaa = sectionService.GetAllSections();
        Section section = sectionService.FindById(id);
        if(section != null) {
            model.addAttribute("formatForDate", new SimpleDateFormat("yyyy.MM.dd "));
            model.addAttribute("formatForTime", new SimpleDateFormat("hh:mm"));
            model.addAttribute("section", section);
            model.addAttribute("user", GetUser(authentication));
            return "SectionController/section";
        }
       return "redirect:/sections";
    }

    @PostMapping("/create")
    public String AddSection(@RequestParam String name,
                             Model model){
        if(sectionService.FindByName(name) == null) {
            Section section = new Section();
            section.setName(name);
            sectionService.AddSection(section);
        }
        else setStatus("section_exist");
        return "redirect:/sections";
    }

    @PostMapping("/delete")
    public String DeleteSection(@RequestParam int id,
                                Model model){
        Section section = sectionService.FindById(id);
        if(section != null) {
            sectionService.DeleteSection(section);
        }
        return "redirect:/sections";
    }

    private User GetUser(Authentication authentication) {
        User user;
        if(authentication != null)
            user = (User)authentication.getPrincipal();
        else {
            user = new User();
            user.setRole("UNKNOWN");
        }
        return user;
    }
}
