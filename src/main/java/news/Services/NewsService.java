package news.Services;

import news.Models.News;
import news.Repositories.INewsRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.File;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Класс сервиса для работы с сущностью новостей
 */
@Service
@Transactional
public class NewsService {
    @Value("${upload.path}")
    private String uploadPath;
    private final INewsRepository newsRepository;

    public NewsService(INewsRepository newsRepository) {
        this.newsRepository = newsRepository;
    }

    /**
     * Добавление новостей
     * @param news Доабвляемые новости
     */
    public void AddNews(News news) {
        newsRepository.save(news);
    }

    /**
     * Полученеи новости по идентификатору
     * @param id Идентификатор новости
     * @return Новостной пост
     */
    public News FindNewsById(int id) { return newsRepository.findById(id);}

    /**
     * Удаление нвоости
     * @param news Удаляемая новость
     */
    public void DeleteNews(News news) {
        DeleteNewsImage(news);
        newsRepository.delete(news);
    }

    /**
     * Получение популярных новостей
     * @return Список популярных новостей
     */
    public List<News> FindTopNews() {
        return newsRepository.findAll().stream().sorted(Comparator.comparingInt(n -> -n.getLikes().size())).limit(10).collect(Collectors.toList());
    }

    /**
     * Получение последних новостей
     * @return Спсиок последних новости
     */
    public List<News> FindLastNews() {
        return newsRepository.findLast();
    }

    private void DeleteNewsImage(News news){
        try {
            File image = new File(new File(uploadPath).getAbsolutePath() + "/" + news.getImage_name());
            image.delete();
        } catch (NullPointerException e)
        {

        }
    }
}
