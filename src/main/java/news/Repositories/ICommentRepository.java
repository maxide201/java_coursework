package news.Repositories;

import news.Models.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Интерфейс репозитория для работы с таблицой "Комментарии" в базе данных
 */
@Repository
public interface ICommentRepository extends JpaRepository<Comment, Integer> {
    /**
     * Получение комментария по идентификатору
     * @param id Идентификатор комментария
     * @return Комментарий
     */
    Comment findById(int id);
}
