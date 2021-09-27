package news.Controllers;

import news.Models.*;
import news.Services.LikeService;
import news.Services.NewsService;
import news.Services.SectionService;
import news.Services.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Контроллер для обработки запросов, связанных с новостными постами
 */
@Controller
@RequestMapping("/news")
public class NewsController {
    private final NewsService newsService;
    private final SectionService sectionService;
    private final UserService userService;
    private final LikeService likeService;
    @Value("${upload.path}")
    private String uploadPath;

    public NewsController(NewsService newsService, SectionService sectionService, UserService userService, LikeService likeService) {
        this.newsService = newsService;
        this.sectionService = sectionService;
        this.userService = userService;
        this.likeService = likeService;
    }

    /**
     * Обработка GET-запроса на предоставление формы дял создания новостного поста
     * @param model html-модель, которая будет отправлена в ответ пользователю
     * @return html документ в виде строки
     */
    @GetMapping("/create")
    public String CreatingNews(Model model) {
        model.addAttribute("sections", sectionService.GetAllSections());
        return "NewsController/create_news";
    }

    /**
     * Обработка GET-запроса на получение определенного новостного поста
     * @param id Идентификатор новости
     * @param authentication Данные ауентификация
     * @param model html-модель, которая будет отправлена в ответ пользователю
     * @return html документ в виде строки
     */
    @GetMapping("/{id}")
    public String GetNews(@PathVariable("id") int id,
                          Authentication authentication,
                          Model model) {
        News news = newsService.FindNewsById(id);
        if(news != null) {
            model.addAttribute("news_post", news);
            model.addAttribute("formatForDate", new SimpleDateFormat("yyyy.MM.dd "));
            model.addAttribute("formatForTime", new SimpleDateFormat("hh:mm"));
            model.addAttribute("user", GetUser(authentication));
            model.addAttribute("comments", news.getComments().stream()
                    .sorted(Comparator.comparing(Comment::getDate)).collect(Collectors.toList()));
            return "NewsController/news";
        }
        return "SectionController/sections";

    }

    /**
     * Обработка POST-запроса на создание новостного поста
     * @param title Заголвоок поста
     * @param content Текстовое содержание поста
     * @param section_name Название раздела
     * @param image Изображение, прилагаемое к посту
     * @param model html-модель, которая будет отправлена в ответ пользователю
     * @return html документ в виде строки
     * @throws IOException Ошибка при попытке сохранить изображение
     */
    @PostMapping("/create")
    public String AddNews(@RequestParam String title,
                          @RequestParam String content,
                          @RequestParam String section_name,
                          @RequestParam MultipartFile image,
                          Model model) throws IOException {
        if(!title.equals("") && !content.equals("") && !section_name.equals("") && image != null) {
            News news = new News();

            Section section = sectionService.FindByName(section_name);

            String uuidFile = UUID.randomUUID().toString();
            String resultFilename = uuidFile + "." + image.getOriginalFilename().split("\\.")[1];
            image.transferTo(new File( new File(uploadPath).getAbsolutePath() + "/" + resultFilename));

            news.setSection(section);
            news.setDate(new Date(System.currentTimeMillis()));
            news.setTitle(title);
            news.setContent(content);
            news.setImage_name(resultFilename);

            newsService.AddNews(news);
            model.addAttribute("section", section);
        }
        return "redirect:/sections";
    }

    /**
     * Обработка POST-запроса на удаление новостного поста
     * @param id Идентификатор новостоного поста
     * @param model html-модель, которая будет отправлена в ответ пользователю
     * @return html документ в виде строки
     */
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

    /**
     * Обработка POST-запроса на создание лайка
     * @param user_id Идентификатор прользователя, который оставил лайк
     * @param news_id Идентификатор поста, куда был поставлен лайк
     * @param model html-модель, которая будет отправлена в ответ пользователю
     * @return html документ в виде строки
     */
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
            return "redirect:/news/" + news.getId();
        }
        return "redirect:/sections";
    }

    /**
     * Обработка POST-запроса на удаление лайка
     * @param user_id Идентификатор прользователя, который удалил лайк
     * @param news_id Идентификатор поста, откуда был удален лайк
     * @param model html-модель, которая будет отправлена в ответ пользователю
     * @return html документ в виде строки
     */
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
            return "redirect:/news/" + news.getId();
        }
        return "redirect:/sections";
    }

    /**
     * Проверка на то, есть ли на ноаостном посте лайк от определенного пользователя
     * @param user Пользователь
     * @param news Новостной пост
     * @return Результат провреки
     */
    public static boolean isLikedByUser(User user, News news)
    {
        return news.getLikes().stream().anyMatch(l -> l.getUser().getId() == user.getId());
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
}
