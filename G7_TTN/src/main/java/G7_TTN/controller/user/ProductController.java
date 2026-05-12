package G7_TTN.controller.user;

import G7_TTN.entity.Category;
import G7_TTN.entity.Product;

import G7_TTN.reponsitory.CategoryRepository;

import G7_TTN.service.ProductService;
import G7_TTN.service.ProductVariantService;

import org.springframework.data.domain.Page;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class ProductController {

    private final ProductService productService;

    private final ProductVariantService variantService;

    private final CategoryRepository categoryRepo;

    public ProductController(
            ProductService productService,
            ProductVariantService variantService,
            CategoryRepository categoryRepo
    ) {

        this.productService = productService;
        this.variantService = variantService;
        this.categoryRepo = categoryRepo;
    }

    // ================= LIST =================

    @GetMapping("/products")
    public String list(

            @RequestParam(defaultValue = "")
            String keyword,

            @RequestParam(required = false)
            Long category,

            @RequestParam(defaultValue = "0")
            int page,

            @RequestParam(defaultValue = "latest")
            String sort,

            Model model
    ) {

        Category cate = null;

        if (category != null) {

            cate = categoryRepo
                    .findById(category)
                    .orElse(null);
        }

        Page<Product> result =
                productService.getAll(
                        keyword,
                        cate,
                        page,
                        sort
                );

        model.addAttribute(
                "products",
                result.getContent()
        );

        model.addAttribute(
                "currentPage",
                page
        );

        model.addAttribute(
                "totalPages",
                result.getTotalPages()
        );

        model.addAttribute(
                "categories",
                categoryRepo.findAll()
        );

        model.addAttribute(
                "keyword",
                keyword
        );

        model.addAttribute(
                "selectedCategory",
                category
        );

        model.addAttribute(
                "sort",
                sort
        );

        return "user/product/list";
    }

    // ================= DETAIL =================

    @GetMapping("/product/{id}")
    public String detail(

            @PathVariable Long id,
            Model model
    ) {

        Product product =
                productService.findById(id);

        if (product == null) {

            return "redirect:/products";
        }

        model.addAttribute(
                "product",
                product
        );

        // CHỈ DÙNG product.image
        // KHÔNG DÙNG G7_product_images

        model.addAttribute(
                "variants",
                variantService.getByProduct(product)
        );

        return "user/product/detail";
    }

    // ================= SALE =================

    @GetMapping("/sale")
    public String sale(

            @RequestParam(defaultValue = "0")
            int page,

            Model model
    ) {

        Page<Product> result =
                productService.getSaleProducts(page);

        model.addAttribute(
                "products",
                result.getContent()
        );

        model.addAttribute(
                "currentPage",
                page
        );

        model.addAttribute(
                "totalPages",
                result.getTotalPages()
        );

        model.addAttribute(
                "categories",
                categoryRepo.findAll()
        );

        return "user/product/sale";
    }
}