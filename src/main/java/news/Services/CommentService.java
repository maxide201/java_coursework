package news.Services;

import news.Models.Comment;
import news.Repositories.ICommentRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * Класс сервиса для работы с сущностью комментариев
 */
@Service
@Transactional
public class CommentService {
    private final ICommentRepository commentRepository;

    public CommentService(ICommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    /**
     * Добавление комментария
     * @param comment Новый комментарий
     */
    public void AddComment(Comment comment) { commentRepository.save(comment); }

    /**
     * Получение комментария по идентификатору
     * @param id Идентификатор комментария
     * @return Комментарий
     */
    public Comment FindById(int id) { return commentRepository.findById(id);}

    /**
     * Удаление комментария
     * @param comment Удаляемый комментарий
     */
    public void DeleteComment(Comment comment) {commentRepository.delete(comment);}
}
