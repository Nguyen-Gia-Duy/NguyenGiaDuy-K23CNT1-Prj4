package G7_TTN.controller.auth;

import G7_TTN.entity.Users;
import G7_TTN.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String login() {
        return "auth/login";
    }

    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("user", new Users());
        return "auth/register";
    }

    @PostMapping("/register")
    public String registerPost(@ModelAttribute("user") Users user,
                               Model model) {

        String result = userService.register(user);

        if (!result.equals("OK")) {
            model.addAttribute("error", result);
            model.addAttribute("user", user); // ⭐ FIX QUAN TRỌNG
            return "auth/register";
        }

        return "redirect:/login?success";
    }
}