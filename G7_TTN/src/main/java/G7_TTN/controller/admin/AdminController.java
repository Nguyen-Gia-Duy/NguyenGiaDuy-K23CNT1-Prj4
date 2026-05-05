package G7_TTN.controller.admin;

import G7_TTN.reponsitory.ProductRepository;
import G7_TTN.reponsitory.UserReponsitory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final ProductRepository productRepo;
    private final UserReponsitory userRepo;

    public AdminController(ProductRepository productRepo,
                           UserReponsitory userRepo) {
        this.productRepo = productRepo;
        this.userRepo = userRepo;
    }

    // Dashboard
    @GetMapping
    public String dashboard(Model model) {

        model.addAttribute("totalUsers", userRepo.count());
        model.addAttribute("totalProducts", productRepo.count());

        return "admin/index";
    }
}