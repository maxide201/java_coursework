package news.Models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

/**
 * Комментарий к новостному посту
 */

@Entity
@Getter
@Setter
@Table(name = "comments")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Comment {
    /** Индектификатор
     *
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    /**
     * Текстовое содержание
     */
    @Column(name = "content")
    private String content;
    /**
     * Время размещения комментария
     */
    @Column(name = "date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;
    /**
     * Пользователь, оставивший комментарий
     */
    @ManyToOne
    private User user;
    @ManyToOne
    /**
     * Пост, к которому оставили комментарий
     */
    private News news;
}
