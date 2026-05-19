package G7_TTN.controller.admin;

import G7_TTN.entity.Users;
import G7_TTN.reponsitory.UserReponsitory;
import G7_TTN.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/users")
public class AdminUserController {

    private final UserReponsitory userRepo;
    private final UserService userService;

    public AdminUserController(UserReponsitory userRepo, UserService userService) {
        this.userRepo = userRepo;
        this.userService = userService;
    }

    @GetMapping
    public String index(Model model){
        model.addAttribute("users", userRepo.findByIsdelete(0));
        return "admin/users/index";
    }

    @GetMapping("/create")
    public String create(Model model){
        model.addAttribute("user", new Users());
        return "admin/users/create";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute("user") Users user){
        userService.save(user);
        return "redirect:/admin/users";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Long id, Model model){

        Users user = userRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        model.addAttribute("user", user);
        return "admin/users/edit";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Long id){
        userService.delete(id);
        return "redirect:/admin/users";
    }

    @PostMapping("/lock/{id}")
    public String lock(@PathVariable Long id){
        userService.lock(id);
        return "redirect:/admin/users";
    }

    @PostMapping("/unlock/{id}")
    public String unlock(@PathVariable Long id){
        userService.unlock(id);
        return "redirect:/admin/users";
    }
}