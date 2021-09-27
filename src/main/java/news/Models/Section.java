package news.Models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

/**
 * Новостной раздел. Все новости подразделяются на некоторые разделы
 */
@Entity
@Getter
@Setter
@Table(name = "sections")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Section {
    /**
     * Идентификатор
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    /**
     * Название раздела
     */
    @Column(name = "name")
    private String name;
    /**
     * Новости, которые содержит раздел
     */
    @OneToMany(mappedBy = "section", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<News> news;
}
