package news.Services;

import news.Models.Like;
import news.Models.News;
import news.Models.User;
import news.Repositories.ILikeRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * Класс сервиса для работы с сущностью лайков
 */
@Service
@Transactional
public class LikeService {
    private final ILikeRepository likeRepository;

    public LikeService(ILikeRepository likeRepository) {
        this.likeRepository = likeRepository;
    }

    /**
     * Доабвление лайка
     * @param like лайк
     */
    public void AddLike(Like like) { likeRepository.save(like); }

    /**
     * Получение лайка по пользователю и нвоостному посту
     * @param user Пользовтаель
     * @param news Новостнйо пост
     * @return Лайк
     */
    public Like FindByUserAndNews(User user, News news) { return likeRepository.findByUserAndNews(user, news);}

    /**
     * Удаление лайка
     * @param like Удаляемый лайк
     */
    public void DeleteLike(Like like) {likeRepository.delete(like);}
}
