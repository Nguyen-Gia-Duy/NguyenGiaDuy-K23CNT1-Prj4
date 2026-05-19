package G7_TTN.controller.admin;

import G7_TTN.entity.Advertisement;
import G7_TTN.reponsitory.AdvertisementRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/admin/advertisement")
public class AdminAdvertisementController {

    @Autowired
    private AdvertisementRepository advertisementRepo;

    // ================= INDEX =================

    @GetMapping
    public String index(Model model){

        List<Advertisement> ads =
                advertisementRepo.findAllByOrderByIdDesc();

        model.addAttribute("ads", ads);

        return "admin/advertisement/index";
    }

    // ================= CREATE =================

    @GetMapping("/create")
    public String create(Model model){

        model.addAttribute(
                "ad",
                new Advertisement()
        );

        return "admin/advertisement/create";
    }

    // ================= SAVE =================

    @PostMapping("/save")
    public String save(@ModelAttribute Advertisement ad){

        // create mới
        if(ad.getId() == null){

            ad.setCreateddate(new Date());

            if(ad.getIsactive() == null){

                ad.setIsactive(1);
            }
        }

        advertisementRepo.save(ad);

        return "redirect:/admin/advertisement";
    }

    // ================= EDIT =================

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Long id,
                       Model model){

        Advertisement ad =
                advertisementRepo.findById(id)
                        .orElse(null);

        if(ad == null){

            return "redirect:/admin/advertisement";
        }

        model.addAttribute("ad", ad);

        return "admin/advertisement/edit";
    }

    // ================= DELETE =================

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Long id){

        Advertisement ad =
                advertisementRepo.findById(id)
                        .orElse(null);

        if(ad != null){

            // soft delete
            ad.setIsactive(0);

            advertisementRepo.save(ad);
        }

        return "redirect:/admin/advertisement";
    }

}