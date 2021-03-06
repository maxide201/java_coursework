package news.Controllers;

import news.Models.Comment;
import news.Models.News;
import news.Models.Section;
import news.Models.User;
import news.Services.NewsService;
import news.Services.SectionService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Контроллер для обработки запросов, связанных с разделами новостей
 */
@Controller
@RequestMapping("/sections")
public class SectionController {
    private final SectionService sectionService;
    private final NewsService newsService;
    private String status;

    private void setStatus(String newStatus) {
        this.status = newStatus;
    }
    private void reloadStatus() {
        this.status = "";
    }

    public SectionController(SectionService sectionService, NewsService newsService) {
        this.sectionService = sectionService;
        this.newsService = newsService;
    }

    /**
     * Обработка GET-запроса на получение главной страницы с разделами и новостями
     * @param model html-модель, которая будет отправлена в ответ пользователю
     * @return html документ в виде строки
     */
    @GetMapping
    public String GetSections(Model model,
                              Authentication authentication){
        model.addAttribute("sections", sectionService.GetAllSections());
        model.addAttribute("status", status);
        model.addAttribute("user", GetUser(authentication));
        model.addAttribute("top_news", newsService.FindTopNews());
        model.addAttribute("last_news", newsService.FindLastNews());
        model.addAttribute("formatForDate", new SimpleDateFormat("yyyy.MM.dd "));
        model.addAttribute("formatForTime", new SimpleDateFormat("hh:mm"));
        reloadStatus();
        return "SectionController/sections";
    }

    /**
     * Обработка GET-запроса на получение всех новостей определенного раздела
     * @param id Идентификатор раздела
     * @param authentication Данные ауентификации
     * @param model html-модель, которая будет отправлена в ответ пользователю
     * @return html документ в виде строки
     */
    @GetMapping("/{id}")
    public String GetSectionNews(@PathVariable("id") int id,
                                 Authentication authentication,
                                 Model model){
        sectionService.GetAllSections();
        Section section = sectionService.FindById(id);
        if(section != null) {
            sortNewsAndComments(section.getNews());
            model.addAttribute("formatForDate", new SimpleDateFormat("yyyy.MM.dd "));
            model.addAttribute("formatForTime", new SimpleDateFormat("hh:mm"));
            model.addAttribute("section", section);
            model.addAttribute("user", GetUser(authentication));
            return "SectionController/section";
        }
       return "redirect:/sections";
    }

    /**
     * Обработка POST-запроса на создание нового раздела
     * @param name Название раздела
     * @param model html-модель, которая будет отправлена в ответ пользователю
     * @return html документ в виде строки
     */
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

    /**
     * Обработка POST-запроса на удаление раздела
     * @param id Идентификатор раздела
     * @param model html-модель, которая будет отправлена в ответ пользователю
     * @return html документ в виде строки
     */
    @PostMapping("/delete")
    public String DeleteSection(@RequestParam int id,
                                Model model){
        Section section = sectionService.FindById(id);
        if(section != null) {
            sectionService.DeleteSection(section);
        }
        return "redirect:/sections";
    }

    /**
     * Получение пользователя по ауентификационным данным
     * @param authentication Данные ауентификации
     * @return Пользователь. Либо новый, елси не авторизован, либо имеющийся в базе данных
     */
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

    /**
     * Сортировка новостей и комментариеав по дате их публикации
     * @param news Список новостных постов
     */
    private void sortNewsAndComments(List<News> news) {
        news.sort(Comparator.comparing(News::getDate));
        Collections.reverse(news);
        for (News elem: news) {
            List<Comment> comments = elem.getComments();
            comments.sort(Comparator.comparing(Comment::getDate));
        }
    }
}
