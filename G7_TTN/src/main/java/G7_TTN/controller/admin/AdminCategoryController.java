package G7_TTN.controller.admin;

import G7_TTN.entity.Category;
import G7_TTN.reponsitory.CategoryRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/admin/category")
public class AdminCategoryController {

    @Autowired
    private CategoryRepository categoryRepo;

    // ================= INDEX =================

    @GetMapping
    public String index(Model model){

        List<Category> categories =
                categoryRepo.findAll();

        model.addAttribute("categories",
                categories);

        return "admin/category/index";
    }

    // ================= CREATE =================

    @GetMapping("/create")
    public String create(Model model){

        model.addAttribute("category",
                new Category());

        model.addAttribute("categories",
                categoryRepo.findAll());

        return "admin/category/create";
    }

    // ================= SAVE =================

    @PostMapping("/save")
    public String save(@ModelAttribute Category category){

        // ADD

        if(category.getId() == null){

            category.setCreateddate(
                    new Date()
            );

            category.setIsdelete(0);

            if(category.getIsactive() == null){

                category.setIsactive(1);
            }
        }

        // UPDATE DATE

        category.setUpdateddate(
                new Date()
        );

        categoryRepo.save(category);

        return "redirect:/admin/category";
    }

    // ================= EDIT =================

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Long id,
                       Model model){

        Category category =
                categoryRepo.findById(id)
                        .orElse(null);

        if(category == null){

            return "redirect:/admin/category";
        }

        model.addAttribute("category",
                category);

        model.addAttribute("categories",
                categoryRepo.findAll());

        return "admin/category/edit";
    }

    // ================= DELETE =================

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Long id){

        categoryRepo.deleteById(id);

        return "redirect:/admin/category";
    }
}