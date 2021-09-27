package news.Models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * Новостной пост
 */
@Entity
@Getter
@Setter
@Table(name = "news")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class News {
    /**
     * Идентификатор
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    /**
     * Заголовок новости
     */
    @Column(name = "title")
    private String title;
    /**
     * Текстовое содержание новостного поста
     */
    @Column(name = "content")
    private String content;
    /**
     * Дата опубликования поста
     */
    @Column(name = "date")
    @Temporal(TemporalType.DATE)
    private Date date;
    /**
     * Название фотографии, прилагаймой к новости. Затем по этому названию фотографии подтягиваются с базы данных
     */
    @Column(name = "image_name")
    private String image_name;
    /**
     * Комментарии новостного поста
     */
    @OneToMany(mappedBy = "news", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Comment> comments;
    /**
     * Поставленные лайки
     */
    @OneToMany(mappedBy = "news", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Like> likes;
    /**
     * Раздел, к которому относится новостной пост
     */
    @ManyToOne
    private Section section;
}
