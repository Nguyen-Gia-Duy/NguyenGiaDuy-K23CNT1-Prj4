package G7_TTN.controller.user;

import G7_TTN.entity.Orders;
import G7_TTN.entity.Orders_details;
import G7_TTN.service.OrderService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    // ================= LIST ORDERS =================
    @GetMapping
    public String list(Authentication auth, Model model) {

        String username = auth.getName();

        List<Orders> orders = orderService.getOrdersByUsername(username);

        model.addAttribute("orders", orders);

        return "user/orders/index";
    }

    // ================= ORDER DETAIL =================
    @GetMapping("/{id}")
    public String detail(@PathVariable Long id, Model model) {

        List<Orders_details> details = orderService.getOrderDetails(id);

        model.addAttribute("details", details);

        return "user/orders/detail";
    }
}