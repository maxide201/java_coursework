package news.Models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.Collections;

/**
 * Пользователь
 */
@Getter
@Setter
@Entity
@Table(name = "users")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class User implements UserDetails {
    /**
     * Идентификатор
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    /**
     * Логин пользователя
     */
    @Column(name="username")
    private String username;
    /**
     * Пароль пользователя
     */
    @Column(name="password")
    private String password;
    /**
     * Роль, определяющая возможности пользователя в приложении
     */
    @Column(name="role")
    private String role;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority(role));
    }

    /**
     * Переопределение метода проверки - не истекла ли ауентификация пользователя
     * @return Всега true - ауентификация пользователя не истекла
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
    /**
     * Переопределение метода проверки - не заблокирвоан ли пользователь
     * @return Всега true - пользователь не заблокирован
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }
    /**
     * Переопределение метода проверки - не истекла ли данные ауентификации пользователя
     * @return Всега true - данные ауентификации не истекли
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
    /**
     * Переопределение метода проверки - доступен ли пользователь
     * @return Всега true - пользователь доступен
     */
    @Override
    public boolean isEnabled() {
        return true;
    }
}
