package news.Models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "news")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class News {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "title")
    private String title;
    @Column(name = "content")
    private String content;
    @Column(name = "date")
    @Temporal(TemporalType.DATE)
    private Date date;
    @OneToMany(mappedBy = "news", fetch = FetchType.EAGER)
    private List<Comment> comments;
    @OneToMany(mappedBy = "news", fetch = FetchType.LAZY)
    private List<Like> likes;
    @ManyToOne
    private Section section;
}
