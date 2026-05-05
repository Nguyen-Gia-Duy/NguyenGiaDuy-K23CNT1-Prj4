package G7_TTN.controller.user;

import G7_TTN.entity.*;
import G7_TTN.reponsitory.CategoryRepository;
import G7_TTN.service.*;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class ProductController {

    private final ProductService productService;
    private final ProductImagesService imageService;
    private final ProductVariantService variantService;
    private final CategoryRepository categoryRepo;

    public ProductController(ProductService productService,
                             ProductImagesService imageService,
                             ProductVariantService variantService,
                             CategoryRepository categoryRepo) {

        this.productService = productService;
        this.imageService = imageService;
        this.variantService = variantService;
        this.categoryRepo = categoryRepo;
    }

    // ================= LIST =================
    @GetMapping("/products")
    public String list(
            @RequestParam(defaultValue = "") String keyword,
            @RequestParam(required = false) Long category,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "latest") String sort,
            Model model
    ) {

        Category cate = null;
        if (category != null) {
            cate = categoryRepo.findById(category).orElse(null);
        }

        Page<Product> result = productService.getAll(keyword, cate, page, sort);

        model.addAttribute("products", result.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", result.getTotalPages());

        model.addAttribute("categories", categoryRepo.findAll());
        model.addAttribute("keyword", keyword);
        model.addAttribute("selectedCategory", category);
        model.addAttribute("sort", sort);

        return "user/product/list";
    }

    // ================= DETAIL =================
    @GetMapping("/product/{id}")
    public String detail(@PathVariable Long id, Model model) {

        Product product = productService.findById(id);

        model.addAttribute("product", product);
        model.addAttribute("images", imageService.getByProduct(product));
        model.addAttribute("variants", variantService.getByProduct(product));

        return "user/product-detail";
    }

}