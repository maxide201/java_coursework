package news;


import news.Models.Like;
import news.Models.News;
import news.Repositories.INewsRepository;
import news.Services.NewsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class NewsServiceTests {
    @InjectMocks
    private NewsService newsService;
    @Mock
    private INewsRepository newsRepository;
    @Captor
    private ArgumentCaptor<News> captor;

    private News news1, news2, news3;

    @BeforeEach
    public void init() {
        news1 = new News();
        news1.setId(1);
        news1.setImage_name("image");
        var likes1 = new ArrayList<Like>();
        likes1.add(new Like());
        likes1.add(new Like());
        likes1.add(new Like());
        likes1.add(new Like());
        news1.setLikes(likes1);

        news2 = new News();
        news2.setId(2);
        var likes2 = new ArrayList<Like>();
        likes2.add(new Like());
        likes2.add(new Like());
        likes2.add(new Like());
        news2.setLikes(likes2);

        news3 = new News();
        news3.setId(3);
        var likes3 = new ArrayList<Like>();
        likes3.add(new Like());
        news3.setLikes(likes3);

    }

    @Test
    public void AddNews() {
        newsService.AddNews(news1);
        Mockito.verify(newsRepository).save(captor.capture());
        News captured = captor.getValue();
        assertEquals(1, captured.getId());
    }

    @Test
    public void DeleteNews(){
        newsService.DeleteNews(news1);
        Mockito.verify(newsRepository).delete(captor.capture());
    }

    @Test
    public void FindNewsById(){
        Mockito.when(newsRepository.findById(1)).thenReturn(news1);
        News n = newsService.FindNewsById(1);
        assertEquals(news1, n);
    }

    @Test
    public void FindTopNews() {
        Mockito.when(newsRepository.findAll()).thenReturn(List.of(news2, news3, news1));
        var top_list = newsService.FindTopNews();
        assertEquals(news1.getId(), top_list.get(0).getId());
        assertEquals(news2.getId(), top_list.get(1).getId());
        assertEquals(news3.getId(), top_list.get(2).getId());
    }

    @Test
    public void FindLastNews() {
        Mockito.when(newsRepository.findLast()).thenReturn(List.of(news1, news2, news3));
        var last_list = newsService.FindLastNews();
        assertEquals(news1.getId(), last_list.get(0).getId());
        assertEquals(news2.getId(), last_list.get(1).getId());
        assertEquals(news3.getId(), last_list.get(2).getId());
    }
}