package news.Controllers;

import news.Models.Section;
import news.Services.SectionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/sections")
public class SectionController {
    private final SectionService sectionService;
    private String status;

    private void setStatus(String newStatus) {
        this.status = newStatus;
    }
    private void reloadStatus() {
        this.status = "";
    }

    public SectionController(SectionService sectionService) {
        this.sectionService = sectionService;
    }
    @GetMapping
    public String GetSections(Model model){
        model.addAttribute("sections", sectionService.GetAllSections());
        model.addAttribute("status", status);
        reloadStatus();
        return "SectionController/sections";
    }

    @GetMapping("/{name}")
    public String GetSectionNews(@PathVariable("name") String name, Model model){
        Section section = sectionService.FindByName(name);
        if(section != null) {
            model.addAttribute("section", section);
            return "SectionController/section";
        }
       return "redirect:/sections";
    }

    @PostMapping
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
    public String DeleteSection(@RequestParam String name,
                             Model model){
        Section section = sectionService.FindByName(name);
        if(section != null) {
            sectionService.DeleteSection(section);
        }
        else setStatus("section_not_exist");
        return "redirect:/sections";
    }
}
