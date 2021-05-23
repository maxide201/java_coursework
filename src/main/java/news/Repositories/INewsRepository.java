package news.Repositories;

import news.Models.News;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface INewsRepository extends JpaRepository<News, Integer> {
    News findById(int id);
    @Query(value = "SELECT * FROM news WHERE date > current_date - interval '1' day", nativeQuery = true)
    List<News> findLast();
}
