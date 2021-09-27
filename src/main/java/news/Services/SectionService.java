package news.Services;

import news.Models.News;
import news.Models.Section;
import news.Repositories.ISectionRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.File;
import java.util.List;

/**
 * Класс сервиса для работы с сущностью разделов
 */
@Service
@Transactional
public class SectionService {
    @Value("${upload.path}")
    private String uploadPath;
    private final ISectionRepository sectionRepository;

    public SectionService(ISectionRepository sectionRepository) {
        this.sectionRepository = sectionRepository;
    }

    /**
     * Добавление раздела
     * @param section Добавляемый раздел
     */
    public void AddSection(Section section) { sectionRepository.save(section); }

    /**
     * Получение раздела по названию
     * @param name Название раздела
     * @return Раздел
     */
    public Section FindByName(String name) {
        return sectionRepository.findByName(name);
    }

    /**
     * Поулчение всех разделов
     * @return Список разделов
     */
    public List<Section> GetAllSections(){
        return sectionRepository.findAll();
    }

    /**
     * Удаление раздела
     * @param section Раздел
     */
    public void DeleteSection(Section section) {
        DeleteImages(section);
        sectionRepository.delete(section);
    }

    /**
     * Поулченеи раздела по идентификатору
     * @param id Идентификатор раздела
     * @return Раздел
     */
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
