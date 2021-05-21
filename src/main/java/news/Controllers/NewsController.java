package news.Controllers;

import news.Models.Like;
import news.Models.News;
import news.Models.Section;
import news.Models.User;
import news.Services.LikeService;
import news.Services.NewsService;
import news.Services.SectionService;
import news.Services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Date;

@Controller
@RequestMapping("/news")
public class NewsController {
    private final NewsService newsService;
    private final SectionService sectionService;
    private final UserService userService;
    private final LikeService likeService;
    private String status;

    private void setStatus(String newStatus) {
        this.status = newStatus;
    }
    private void reloadStatus() {
        this.status = "";
    }

    public NewsController(NewsService newsService, SectionService sectionService, UserService userService, LikeService likeService) {
        this.newsService = newsService;
        this.sectionService = sectionService;
        this.userService = userService;
        this.likeService = likeService;
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

    @PostMapping("/like")
    public String CreateLike(@RequestParam int user_id,
                             @RequestParam int news_id,
                             Model model)
    {

        User user = userService.FindUserById(user_id);
        News news = newsService.FindNewsById(news_id);

        if(user!= null && news != null && likeService.FindByUserAndNews(user, news) == null){
            Like like = new Like();
            like.setUser(user);
            like.setNews(news);
            likeService.AddLike(like);
            return "redirect:/sections/" + news.getSection().getId();
        }
        return "redirect:/sections";
    }
    @PostMapping("/unlike")
    public String DeleteLike(@RequestParam int user_id,
                             @RequestParam int news_id,
                             Model model)
    {
        User user = userService.FindUserById(user_id);
        News news = newsService.FindNewsById(news_id);
        if(user!= null && news != null){
            Like like = likeService.FindByUserAndNews(user, news);
            if (like != null)
                likeService.DeleteLike(like);
            return "redirect:/sections/" + news.getSection().getId();
        }
        return "redirect:/sections";
    }

    public static boolean isLikedByUser(User user, News news)
    {
        return news.getLikes().stream().anyMatch(l -> l.getUser().getId() == user.getId());
    }
}
