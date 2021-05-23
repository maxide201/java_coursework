package news;

import news.Models.Like;
import news.Repositories.ILikeRepository;
import news.Services.LikeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;


import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class LikeServiceTests {
    @InjectMocks
    private LikeService likeService;
    @Mock
    private ILikeRepository likeRepository;
    @Captor
    private ArgumentCaptor<Like> captor;

    private Like like1, like2;

    @BeforeEach
    public void init(){
        like1 = new Like();
        like2 = new Like();
        like1.setId(1);
        like2.setId(2);
    }

    @Test
    public void AddLike(){
        likeService.AddLike(like1);
        Mockito.verify(likeRepository).save(captor.capture());
        Like captured = captor.getValue();
        assertEquals(1, captured.getId());
    }

    @Test
    public void DeleteLike(){
        likeService.DeleteLike(like1);
        Mockito.verify(likeRepository).delete(captor.capture());
    }
}
