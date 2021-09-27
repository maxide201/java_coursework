package news.Repositories;

import news.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Интерфейс репозитория для работы с таблицой "Пользователи" в базе данных
 */
@Repository
public interface IUserRepository extends JpaRepository<User, Integer> {
    /**
     * Получение пользователя по логину
     * @param username Логин пользователя
     * @return Пользователь
     */
    User findByUsername(String username);

    /**
     * Получение пользователя по идентификатору
     * @param id Идентификатор пользователя
     * @return Пользователь
     */
    User findById(int id);
}