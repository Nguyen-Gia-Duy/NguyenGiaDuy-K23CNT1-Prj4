package G7_TTN.controller.user;

import G7_TTN.entity.Advertisement;
import G7_TTN.reponsitory.AdvertisementRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/advertisement")
public class AdvertisementController {

    @Autowired
    private AdvertisementRepository advertisementRepo;

    @GetMapping
    public String advertisementPage(Model model){

        List<Advertisement> ads =
                advertisementRepo.findByIsactive(1);

        model.addAttribute(
                "ads",
                ads
        );

        return "user/advertisement/index";
    }
}