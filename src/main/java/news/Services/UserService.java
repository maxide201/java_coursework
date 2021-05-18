package news.Services;

import news.Models.User;
import news.Repositories.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class UserService implements UserDetailsService {
    private final IUserRepository userRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
    @Autowired
    public UserService(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return userRepository.findByUsername(s);
    }
    public void createUser(String username, String password) {
        User u = new User();
        u.setUsername(username);
        u.setPassword(bCryptPasswordEncoder.encode(password));
        u.setRole("USER");
        userRepository.save(u);
    }
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
    public User FindUserById(int id){ return userRepository.findById(id);}

    public void UpdateUser(User user) { userRepository.save(user);  }
}