package news.Services;

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



}