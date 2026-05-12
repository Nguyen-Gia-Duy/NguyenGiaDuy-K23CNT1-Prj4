package G7_TTN.controller.user;

import G7_TTN.entity.Product;
import G7_TTN.reponsitory.CategoryRepository;
import G7_TTN.reponsitory.ProductRepository;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {

    private final ProductRepository productRepo;

    private final CategoryRepository categoryRepo;

    public UserController(
            ProductRepository productRepo,
            CategoryRepository categoryRepo
    ) {

        this.productRepo = productRepo;
        this.categoryRepo = categoryRepo;
    }

    // ================= HOME =================

    @GetMapping
    public String home(Model model) {

        List<Product> products =
                productRepo.findAll();

        model.addAttribute(
                "products",
                products
        );

        model.addAttribute(
                "categories",
                categoryRepo.findAll()
        );

        return "user/index";
    }
}