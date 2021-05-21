package news.Repositories;

import news.Models.Like;
import news.Models.News;
import news.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ILikeRepository extends JpaRepository<Like, Integer> {
    Like findByUserAndNews(User user, News news);
}
