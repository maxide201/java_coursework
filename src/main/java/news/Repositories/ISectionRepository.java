package news.Repositories;

import news.Models.News;
import news.Models.Section;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ISectionRepository extends JpaRepository<Section, Integer> {
    Section findByName(String name);
}
