package news.Repositories;

import news.Models.Like;
import news.Models.News;
import news.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Интерфейс репозитория для работы с таблицой "Лайки" в базе данных
 */
@Repository
public interface ILikeRepository extends JpaRepository<Like, Integer> {
    /**
     * Получение лайка по пользователю и новостному посту
     * @param user Пользователь
     * @param news Новотсной пост
     * @return Лайк
     */
    Like findByUserAndNews(User user, News news);
}
