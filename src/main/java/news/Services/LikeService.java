package news.Services;

import news.Models.Like;
import news.Models.News;
import news.Models.User;
import news.Repositories.ILikeRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class LikeService {
    private final ILikeRepository likeRepository;

    public LikeService(ILikeRepository likeRepository) {
        this.likeRepository = likeRepository;
    }

    public void AddLike(Like like) { likeRepository.save(like); }

    public Like FindByUserAndNews(User user, News news) { return likeRepository.findByUserAndNews(user, news);}

    public void DeleteLike(Like like) {likeRepository.delete(like);}
}
