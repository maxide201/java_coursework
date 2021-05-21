package news.Services;

import news.Models.News;
import news.Repositories.INewsRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

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
    public void DeleteNews(News news) {newsRepository.delete(news);}

    public List<News> FindTopNews() {
        return newsRepository.findAll().stream().sorted(Comparator.comparingInt(n -> -n.getLikes().size())).limit(10).collect(Collectors.toList());

    }
}
