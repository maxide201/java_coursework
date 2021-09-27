package news.Repositories;

import news.Models.Section;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Интерфейс репозитория для работы с таблицой "Разделы" в базе данных
 */
@Repository
public interface ISectionRepository extends JpaRepository<Section, Integer> {
    /**
     * Поулчение раздела по названию
     * @param name Название раздела
     * @return Раздел
     */
    Section findByName(String name);

    /**
     * Получение раздела по идентификатору
     * @param id Идентификатор раздела
     * @return Раздел
     */
    Section findById(int id);
}
