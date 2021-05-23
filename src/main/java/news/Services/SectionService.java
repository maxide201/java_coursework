package news.Services;

import news.Models.News;
import news.Models.Section;
import news.Repositories.ISectionRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.File;
import java.util.List;

@Service
@Transactional
public class SectionService {
    @Value("${upload.path}")
    private String uploadPath;
    private final ISectionRepository sectionRepository;

    public SectionService(ISectionRepository sectionRepository) {
        this.sectionRepository = sectionRepository;
    }

    public void AddSection(Section section) { sectionRepository.save(section); }

    public Section FindByName(String name) {
        return sectionRepository.findByName(name);
    }

    public List<Section> GetAllSections(){
        return sectionRepository.findAll();
    }

    public void DeleteSection(Section section) {
        DeleteImages(section);
        sectionRepository.delete(section);
    }

    public Section FindById(int id) { return sectionRepository.findById(id);  }

    private void DeleteImages(Section section)
    {
        try {


            for (News news : section.getNews()) {
                File image = new File(new File(uploadPath).getAbsolutePath() + "/" + news.getImage_name());
                image.delete();
            }
        }
        catch (NullPointerException e)
        {

        }
    }
}
