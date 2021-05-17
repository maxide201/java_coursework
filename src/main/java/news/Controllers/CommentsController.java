package news.Controllers;

import news.Models.Comment;
import news.Models.News;
import news.Services.CommentService;
import news.Services.NewsService;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping( "/comments")
public class CommentsController {
    private final CommentService commentService;
    private final NewsService newsService;
    private String status;

    public CommentsController(CommentService commentService, NewsService newsService) {
        this.commentService = commentService;
        this.newsService = newsService;
    }

    private void setStatus(String newStatus) {
        this.status = newStatus;
    }
    private void reloadStatus() {
        this.status = "";
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
            if(news != null){
                Comment comment = new Comment();
                comment.setContent(content);
                comment.setNews(news);
                commentService.AddComment(comment);
            }
        }
        return "redirect:/sections/"+news.getSection().getId();
    }
}
