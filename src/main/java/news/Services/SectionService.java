package news.Services;

import news.Models.Section;
import news.Repositories.ISectionRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class SectionService {
    private final ISectionRepository sectionRepository;

    public SectionService(ISectionRepository sectionRepository) {
        this.sectionRepository = sectionRepository;
    }

    public void AddSection(Section section) {
        sectionRepository.save(section);

    }

    public Section FindByName(String name) {
        return sectionRepository.findByName(name);
    }

    public List<Section> GetAllSections(){
        return sectionRepository.findAll();
    }
}
