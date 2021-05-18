package news.Services;

import news.Models.Comment;
import news.Repositories.ICommentRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class CommentService {
    private final ICommentRepository commentRepository;

    public CommentService(ICommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    public void AddComment(Comment comment) { commentRepository.save(comment); }

    public Comment FindById(int id) { return commentRepository.findById(id);}

    public void DeleteComment(Comment comment) {commentRepository.delete(comment);}
}
