package G7_TTN.controller.admin;

import G7_TTN.reponsitory.CategoryRepository;
import G7_TTN.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final ProductService productService;
    private final CategoryRepository categoryRepo;

    public AdminController(ProductService productService,
                           CategoryRepository categoryRepo) {

        this.productService = productService;
        this.categoryRepo = categoryRepo;
    }

    // ================= DASHBOARD =================
    @GetMapping
    public String dashboard(Model model) {

        model.addAttribute("totalProducts",
                productService.count());

        model.addAttribute("categories",
                categoryRepo.findAll());

        return "admin/index";
    }

}