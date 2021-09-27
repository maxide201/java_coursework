package news.Services;

import news.Models.User;
import news.Repositories.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Класс сервиса для работы с сущностью пользователей
 */
@Service
@Transactional
public class UserService implements UserDetailsService {
    private final IUserRepository userRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
    @Autowired
    public UserService(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Получение пользваотеля по логину
     * @param s Логин
     * @return Пользователь
     * @throws UsernameNotFoundException Пользователь не найден
     */
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return userRepository.findByUsername(s);
    }

    /**
     * Создавние пользователя
     * @param username Логин
     * @param password Пароль
     */
    public void createUser(String username, String password) {
        User u = new User();
        u.setUsername(username);
        u.setPassword(bCryptPasswordEncoder.encode(password));
        u.setRole("USER");
        userRepository.save(u);
    }

    /**
     * Получение всех пользователей
     * @return Список пользователей
     */
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    /**
     * Получение пользователя по идентификатору
     * @param id Идентификатор пользователя
     * @return Пользователь
     */
    public User FindUserById(int id){ return userRepository.findById(id);}

    /**
     * Обновление данных пользователя
     * @param user Новый пользователь
     */
    public void UpdateUser(User user) { userRepository.save(user);}
}