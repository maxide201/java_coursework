package news.Controllers;

import news.Models.Comment;
import news.Models.News;
import news.Models.User;
import news.Services.CommentService;
import news.Services.NewsService;
import news.Services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@Controller
@RequestMapping( "/comments")
public class CommentsController {
    private final CommentService commentService;
    private final NewsService newsService;
    private final UserService userService;

    public CommentsController(CommentService commentService, NewsService newsService, UserService userService) {
        this.commentService = commentService;
        this.newsService = newsService;
        this.userService = userService;
    }

    @PostMapping
    public String AddComment(@RequestParam String content,
                             @RequestParam int user_id,
                             @RequestParam int news_id,
                             Model model)
    {
        News news = null;
        if(!content.equals("")) {
            news = newsService.FindNewsById(news_id);
            User user = userService.FindUserById(user_id);
            if(news != null && user != null){
                Comment comment = new Comment();
                comment.setContent(content);
                comment.setNews(news);
                comment.setUser(user);
                Date date = new Date(System.currentTimeMillis());
                comment.setDate(new Date(System.currentTimeMillis()));
                commentService.AddComment(comment);
            }
        }
        if(news != null)
            return "redirect:/news/"+news.getId();
        else
            return "redirect:/sections";
    }

    @PostMapping("/delete")
    public String DeleteComment(@RequestParam int id,
                                @RequestParam int news_id,
                                Model model)
    {
        News news = newsService.FindNewsById(news_id);
        if(news != null) {
            Comment comment = commentService.FindById(id);
            if(comment != null)
                commentService.DeleteComment(comment);
            return "redirect:/news/" + news.getId();
        }
        else
            return "redirect:/sections";
    }
}
