package news.Controllers;

import news.Models.User;
import news.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Контроллер для обработки запросов, связанных с пользователями
 */
@Controller
public class UserController {
    private final UserService userService;
    private String status = "";

    private void setStatus(String newStatus) {
        this.status = newStatus;
    }
    private void reloadStatus() {
        this.status = "";
    }

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Обработка GET-запроса на получение странциы входа
     * @return html документ в виде строки
     */
    @GetMapping("/sign")
    public String signIndex() {
        return "UserController/signup";
    }

    /**
     * Обработка POST-запроса на регистрацию пользователя
     * @param username Логин пользователя
     * @param password Пароль пользователя
     * @param model html-модель, которая будет отправлена в ответ пользователю
     * @return html документ в виде строки
     */
    @PostMapping("/sign")
    public String signUp(@RequestParam(name = "username") String username,
                         @RequestParam(name = "password") String password,
                         Model model) {
        if (userService.loadUserByUsername(username) != null) {
            model.addAttribute("status", "user_exists");
            return "UserController/signup";
        } else {
            userService.createUser(username, password);
            return "redirect:/sections";
        }
    }

    /**
     * Обработка GET-запроса на предоставления страницы изменения ролей пользователей
     * @param model html-модель, которая будет отправлена в ответ пользователю
     * @return html документ в виде строки
     */
    @GetMapping("/roles")
    public String ShowChangeRoles(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        model.addAttribute("status", status);
        reloadStatus();
        return "UserController/roles";
    }

    /**
     * Обработка POST-запроса на изменение роли пользователя
     * @param username Имя пользователя
     * @param role Роль пользователя
     * @return html документ в виде строки
     */
    @PostMapping("/roles")
    public String ChangeRoles(@RequestParam(name = "username") String username,
                              @RequestParam(name = "role") String role) {
        User user = (User) userService.loadUserByUsername(username);
        if(user != null)
            if(role.equals("USER") || role.equals("AUTHOR") || role.equals("ADMIN")) {
                user.setRole(role);
                userService.UpdateUser(user);
                setStatus("role_changed");
            }
        if(!status.equals("role_changed"))
            setStatus("role_not_changed");
        return "redirect:/roles";
    }
}
