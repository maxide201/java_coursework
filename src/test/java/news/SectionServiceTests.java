package news;


import news.Models.News;
import news.Models.Section;
import news.Repositories.ISectionRepository;
import news.Services.SectionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class SectionServiceTests {
    @InjectMocks
    private SectionService sectionService;
    @Mock
    private ISectionRepository sectionRepository;
    @Captor
    private ArgumentCaptor<Section> captor;

    private Section section1, section2, section3;

    @BeforeEach
    public void init() {
        section1 = new Section();
        section1.setId(1);
        section1.setName("section");

        section2 = new Section();
        section2.setId(2);

        section3 = new Section();
        section3.setId(3);
    }

    @Test
    public void AddSection() {
        sectionService.AddSection(section1);
        Mockito.verify(sectionRepository).save(captor.capture());
        Section captured = captor.getValue();
        assertEquals(1, captured.getId());
    }

    @Test
    public void DeleteSection(){
        sectionService.DeleteSection(section1);
        Mockito.verify(sectionRepository).delete(captor.capture());
    }

    @Test
    public void FindSectionById(){
        Mockito.when(sectionRepository.findById(1)).thenReturn(section1);
        Section n = sectionService.FindById(1);
        assertEquals(1, n.getId());
    }

    @Test
    public void FindByName() {
        Mockito.when(sectionRepository.findByName("section")).thenReturn(section1);
        Section n = sectionService.FindByName("section");
        assertEquals("section", n.getName());
    }

    @Test
    public void FindAllSections() {
        Mockito.when(sectionRepository.findAll()).thenReturn(List.of(section1, section2, section3));
        var list = sectionService.GetAllSections();
        assertEquals(section1.getId(), list.get(0).getId());
        assertEquals(section2.getId(), list.get(1).getId());
        assertEquals(section3.getId(), list.get(2).getId());
    }
}