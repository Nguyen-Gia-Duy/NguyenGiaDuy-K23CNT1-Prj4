package G7_TTN.controller.admin;

import G7_TTN.entity.Product;
import G7_TTN.entity.ProductSale;
import G7_TTN.reponsitory.CategoryRepository;
import G7_TTN.reponsitory.ProductRepository;
import G7_TTN.reponsitory.ProductSaleRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Controller
@RequestMapping("/admin/products")
public class AdminProductController {

    private final ProductRepository productRepo;
    private final CategoryRepository categoryRepo;
    private final ProductSaleRepository saleRepo;

    public AdminProductController(ProductRepository productRepo,
                                  CategoryRepository categoryRepo,
                                  ProductSaleRepository saleRepo) {

        this.productRepo = productRepo;
        this.categoryRepo = categoryRepo;
        this.saleRepo = saleRepo;
    }

    // ================= LIST =================
    @GetMapping
    public String index(Model model) {

        model.addAttribute("products",
                productRepo.findAll());

        return "admin/product/index";
    }

    // ================= CREATE =================
    @GetMapping("/create")
    public String create(Model model) {

        model.addAttribute("product",
                new Product());

        model.addAttribute("categories",
                categoryRepo.findAll());

        return "admin/product/create";
    }

    // ================= STORE =================
    @PostMapping("/store")
    public String store(
            @ModelAttribute Product product,

            @RequestParam("file")
            MultipartFile file,

            @RequestParam(required = false)
            Integer discountpercent
    ) throws IOException {

        // ================= UPLOAD IMAGE =================

        if (!file.isEmpty()) {

            String uploadDir =
                    System.getProperty("user.dir")
                            + "/src/main/resources/static/images/";

            File dir = new File(uploadDir);

            if (!dir.exists()) {

                dir.mkdirs();
            }

            String fileName =
                    UUID.randomUUID()
                            + "_"
                            + file.getOriginalFilename();

            file.transferTo(
                    new File(uploadDir + fileName));

            product.setImage(fileName);
        }

        Product saved =
                productRepo.save(product);

        // ================= SAVE SALE =================

        if (discountpercent != null
                && discountpercent > 0) {

            ProductSale sale =
                    new ProductSale();

            sale.setProduct(saved);

            sale.setDiscountpercent(
                    discountpercent);

            saleRepo.save(sale);
        }

        return "redirect:/admin/products";
    }

    // ================= EDIT =================
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Long id,
                       Model model) {

        Product product =
                productRepo.findById(id)
                        .orElse(null);

        model.addAttribute("product",
                product);

        model.addAttribute("categories",
                categoryRepo.findAll());

        return "admin/product/edit";
    }

    // ================= UPDATE =================
    @PostMapping("/update/{id}")
    public String update(
            @PathVariable Long id,

            @ModelAttribute Product product,

            @RequestParam("file")
            MultipartFile file,

            @RequestParam(required = false)
            Integer discountpercent
    ) throws IOException {

        Product old =
                productRepo.findById(id)
                        .orElse(null);

        if (old != null) {

            old.setName(product.getName());

            old.setDescription(
                    product.getDescription());

            old.setBrand(product.getBrand());

            old.setMaterial(
                    product.getMaterial());

            old.setPrice(product.getPrice());

            old.setQuantity(
                    product.getQuantity());

            old.setSlug(product.getSlug());

            old.setCategory(
                    product.getCategory());

            // ================= UPDATE IMAGE =================

            if (!file.isEmpty()) {

                String uploadDir =
                        System.getProperty("user.dir")
                                + "/src/main/resources/static/images/";

                File dir = new File(uploadDir);

                if (!dir.exists()) {

                    dir.mkdirs();
                }

                String fileName =
                        UUID.randomUUID()
                                + "_"
                                + file.getOriginalFilename();

                file.transferTo(
                        new File(uploadDir + fileName));

                old.setImage(fileName);
            }

            productRepo.save(old);

            // ================= SALE =================

            ProductSale sale =
                    old.getSale();

            if (discountpercent != null
                    && discountpercent > 0) {

                if (sale == null) {

                    sale = new ProductSale();

                    sale.setProduct(old);
                }

                sale.setDiscountpercent(
                        discountpercent);

                saleRepo.save(sale);

            } else {

                if (sale != null) {

                    saleRepo.delete(sale);
                }
            }
        }

        return "redirect:/admin/products";
    }

    // ================= DELETE =================
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {

        Product product =
                productRepo.findById(id)
                        .orElse(null);

        if (product != null) {

            if (product.getSale() != null) {

                saleRepo.delete(
                        product.getSale());
            }

            productRepo.delete(product);
        }

        return "redirect:/admin/products";
    }

}