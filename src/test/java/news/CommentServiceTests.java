package news;

import news.Models.Comment;
import news.Repositories.ICommentRepository;
import news.Services.CommentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;


import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class CommentServiceTests {
    @InjectMocks
    private CommentService commentService;
    @Mock
    private ICommentRepository commentRepository;
    @Captor
    private ArgumentCaptor<Comment> captor;

    private Comment comment1, comment2;

    @BeforeEach
    public void init(){
        comment1 = new Comment();
        comment2 = new Comment();
        comment1.setId(1);
        comment2.setId(2);

    }

    @Test
    public void AddComment(){
        commentService.AddComment(comment1);
        Mockito.verify(commentRepository).save(captor.capture());
        Comment captured = captor.getValue();
        assertEquals(1, captured.getId());
    }

    @Test
    public void DeleteComment(){
        commentService.DeleteComment(comment1);
        Mockito.verify(commentRepository).delete(captor.capture());
    }
}
