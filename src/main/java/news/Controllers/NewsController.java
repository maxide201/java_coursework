package news.Controllers;

import news.Models.Comment;
import news.Models.News;
import news.Models.Section;
import news.Services.NewsService;
import news.Services.SectionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
@RequestMapping("/news")
public class NewsController {
    private final NewsService newsService;
    private final SectionService sectionService;
    private String status;

    private void setStatus(String newStatus) {
        this.status = newStatus;
    }
    private void reloadStatus() {
        this.status = "";
    }

    public NewsController(NewsService newsService, SectionService sectionService) {
        this.newsService = newsService;
        this.sectionService = sectionService;
    }
    @GetMapping("/create")
    public String CreatingNews(Model model) {
        model.addAttribute("sections", sectionService.GetAllSections());
        return "NewsController/create_news";
    }

    @PostMapping("/create")
    public String AddNews(@RequestParam String title,
                          @RequestParam String content,
                          @RequestParam String section_name,
                          Model model)
    {
        if(!title.equals("") && !content.equals("") && !section_name.equals("")) {
            News news = new News();
            news.setTitle(title);
            news.setContent(content);
            Section section = sectionService.FindByName(section_name);
            news.setSection(section);
            news.setDate(new Date(System.currentTimeMillis()));
            newsService.AddNews(news);
            model.addAttribute("section", section);
        }
        return "redirect:/sections";
    }

    @PostMapping("/delete")
    public String DeleteNews(@RequestParam int id,
                             Model model)
    {
        News news = newsService.FindNewsById(id);
        if(news != null) {
            newsService.DeleteNews(news);
            return "redirect:/sections/" + news.getSection().getId();
        }
        else
            return "redirect:/sections";
    }
}
