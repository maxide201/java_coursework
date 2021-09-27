package news.Repositories;

import news.Models.News;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Интерфейс репозитория для работы с таблицой "Новости" в базе данных
 */
@Repository
public interface INewsRepository extends JpaRepository<News, Integer> {
    /**
     * Получение новости по идентифкатору
     * @param id Идентификатор
     * @return Новость
     */
    News findById(int id);

    /**
     * Получение новостей за последний день
     * @return Список новостей за последний день
     */
    @Query(value = "SELECT * FROM news WHERE date > current_date - interval '1' day", nativeQuery = true)
    List<News> findLast();
}
