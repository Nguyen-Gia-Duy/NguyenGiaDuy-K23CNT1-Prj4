package G7_TTN.controller.admin;

import G7_TTN.entity.Orders;
import G7_TTN.reponsitory.*;
import G7_TTN.service.ProductService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final ProductService productService;

    private final CategoryRepository categoryRepo;

    private final OrdersReponsitory ordersRepository;

    private final UserReponsitory userRepository;

    private final AdvertisementRepository advertisementRepository;

    public AdminController(
            ProductService productService,
            CategoryRepository categoryRepo,
            OrdersReponsitory ordersRepository,
            UserReponsitory userRepository,
            AdvertisementRepository advertisementRepository
    ) {

        this.productService = productService;

        this.categoryRepo = categoryRepo;

        this.ordersRepository = ordersRepository;

        this.userRepository = userRepository;

        this.advertisementRepository = advertisementRepository;
    }

    // ================= DASHBOARD =================

    @GetMapping
    public String dashboard(Model model) {

        // ================= TOTAL PRODUCT =================

        model.addAttribute(
                "totalProducts",
                productService.count()
        );

        // ================= TOTAL CATEGORY =================

        model.addAttribute(
                "totalCategory",
                categoryRepo.count()
        );

        // ================= TOTAL USERS =================

        model.addAttribute(
                "totalUsers",
                userRepository.count()
        );

        // ================= TOTAL ADS =================

        model.addAttribute(
                "totalAds",
                advertisementRepository.count()
        );

        // ================= ORDERS =================

        List<Orders> orders =
                ordersRepository.findByIsdelete(0);

        model.addAttribute(
                "totalOrders",
                orders.size()
        );

        // ================= TOTAL REVENUE =================

        BigDecimal totalRevenue =
                BigDecimal.ZERO;

        for (Orders order : orders) {

            // bỏ qua đơn huỷ
            if ("CANCELLED".equals(order.getStatus())) {
                continue;
            }

            // tránh null
            if (order.getTotalmoney() != null) {

                totalRevenue = totalRevenue.add(
                        order.getTotalmoney()
                );
            }
        }

        model.addAttribute(
                "totalRevenue",
                totalRevenue
        );

        // ================= REVENUE BY MONTH =================

        double[] monthlyRevenue =
                new double[12];

        for (Orders order : orders) {

            // bỏ qua đơn huỷ
            if ("CANCELLED".equals(order.getStatus())) {
                continue;
            }

            // tránh lỗi null date
            if (order.getOrdersdate() == null) {
                continue;
            }

            Calendar calendar =
                    Calendar.getInstance();

            calendar.setTime(
                    order.getOrdersdate()
            );

            int month =
                    calendar.get(Calendar.MONTH);

            if (order.getTotalmoney() != null) {

                monthlyRevenue[month] +=
                        order.getTotalmoney()
                                .doubleValue();
            }
        }

        // ================= SEND MONTH TO VIEW =================

        model.addAttribute(
                "month1",
                monthlyRevenue[0]
        );

        model.addAttribute(
                "month2",
                monthlyRevenue[1]
        );

        model.addAttribute(
                "month3",
                monthlyRevenue[2]
        );

        model.addAttribute(
                "month4",
                monthlyRevenue[3]
        );

        model.addAttribute(
                "month5",
                monthlyRevenue[4]
        );

        model.addAttribute(
                "month6",
                monthlyRevenue[5]
        );

        model.addAttribute(
                "month7",
                monthlyRevenue[6]
        );

        model.addAttribute(
                "month8",
                monthlyRevenue[7]
        );

        model.addAttribute(
                "month9",
                monthlyRevenue[8]
        );

        model.addAttribute(
                "month10",
                monthlyRevenue[9]
        );

        model.addAttribute(
                "month11",
                monthlyRevenue[10]
        );

        model.addAttribute(
                "month12",
                monthlyRevenue[11]
        );

        return "admin/index";
    }
}