package news;

import news.Models.User;
import news.Repositories.IUserRepository;
import news.Services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTests {
    @InjectMocks
    private UserService userService;
    @Mock
    private IUserRepository userRepository;
    @Captor
    private ArgumentCaptor<User> captor;

    private User user1, user2, user3;
    @BeforeEach
    public void init() {
        user1 = new User();
        user1.setId(1);
        user1.setUsername("user1");
        user1.setPassword("password");
        user1.setRole("role");

        user2 = new User();
        user2.setId(2);
        user2.setUsername("user2");
        user2.setPassword("password");
        user2.setRole("role");

        user3 = new User();
        user3.setId(3);
        user3.setUsername("user3");
        user3.setPassword("password");
        user3.setRole("role");
    }
    @Test
    public void LoadUserByUsername() {
        Mockito.when(userRepository.findByUsername("user1")).thenReturn(user1);
        assertEquals(user1, userService.loadUserByUsername("user1"));
    }

    @Test
    public void AddUser() {
        userService.createUser( "login", "password");
        Mockito.verify(userRepository).save(captor.capture());
        User captured = captor.getValue();
        assertEquals("login", captured.getUsername());
    }

    @Test
    public void GetAllUsers() {
        Mockito.when(userRepository.findAll()).thenReturn(List.of(user1, user2, user3));
        assertEquals(List.of(user1, user2, user3), userRepository.findAll());
    }

    @Test
    public void FindUserById() {
        Mockito.when(userRepository.findById(1)).thenReturn(user1);
        assertEquals(user1, userService.FindUserById(1));
    }

    @Test
    public void UpdateUser() {
        userService.UpdateUser(user1);
        Mockito.verify(userRepository).save(user1);
    }
}