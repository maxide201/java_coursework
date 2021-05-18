package news.Controllers;

import news.Services.UserService;
import org.dom4j.rule.Mode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/sign")
    public String signIndex() {
        return "UserController/signup";
    }
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
}
