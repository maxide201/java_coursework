package news.Repositories;

import news.Models.Section;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ISectionRepository extends JpaRepository<Section, Integer> {
    Section findByName(String name);
    Section findById(int id);
}
