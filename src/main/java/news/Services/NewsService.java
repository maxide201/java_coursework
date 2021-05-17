package news.Services;

import news.Models.News;
import news.Repositories.INewsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class NewsService {
    private final INewsRepository newsRepository;

    public NewsService(INewsRepository newsRepository) {
        this.newsRepository = newsRepository;
    }


    public void AddNews(News news) {
        newsRepository.save(news);
    }
    public News FindNewsById(int id) { return newsRepository.findById(id);}
}
