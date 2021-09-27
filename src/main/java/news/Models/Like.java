package news.Models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * Лайки на постах
 */
@Entity
@Getter
@Setter
@Table(name = "likes")
public class Like {
    /**
     * Идентификатор
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne
/**
 * Пользователь, оставвиший лайк
 */
    private User user;
    @ManyToOne
/**
 * Новость, которой поставили лайк
 */
    private News news;
}
