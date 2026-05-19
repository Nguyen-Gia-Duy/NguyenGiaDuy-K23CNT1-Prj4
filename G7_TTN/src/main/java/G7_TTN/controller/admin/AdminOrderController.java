package G7_TTN.controller.admin;

import G7_TTN.entity.Order_tracking;
import G7_TTN.entity.Orders;
import G7_TTN.entity.Orders_details;
import G7_TTN.reponsitory.OrderTrackingRepository;
import G7_TTN.reponsitory.OrdersDetailsRepository;
import G7_TTN.reponsitory.OrdersReponsitory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/admin/orders")
public class AdminOrderController {

    @Autowired
    private OrdersReponsitory ordersRepository;

    @Autowired
    private OrdersDetailsRepository detailsRepository;

    @Autowired
    private OrderTrackingRepository trackingRepository;

    // ================= LIST =================

    @GetMapping
    public String index(Model model){

        List<Orders> orders =
                ordersRepository.findByIsdelete(0);

        model.addAttribute("orders", orders);

        return "admin/orders/index";
    }

    // ================= DETAIL =================

    @GetMapping("/{id}")
    public String detail(@PathVariable Long id,
                         Model model){

        Orders order =
                ordersRepository.findById(id)
                        .orElse(null);

        if(order == null || order.getIsdelete() == 1){

            return "redirect:/admin/orders";
        }

        List<Orders_details> details =
                detailsRepository.findByOrdersId(id);

        List<Order_tracking> tracking =
                trackingRepository
                        .findByOrdersIdOrderByUpdatedtimeDesc(id);

        model.addAttribute("order", order);
        model.addAttribute("details", details);
        model.addAttribute("tracking", tracking);

        return "admin/orders/detail";
    }

    // ================= UPDATE STATUS =================

    @PostMapping("/update-status/{id}")
    public String updateStatus(@PathVariable Long id,
                               @RequestParam String status,
                               @RequestParam(required = false)
                               String note){

        Orders order =
                ordersRepository.findById(id)
                        .orElse(null);

        if(order != null){

            order.setStatus(status);

            ordersRepository.save(order);

            Order_tracking tracking =
                    new Order_tracking();

            tracking.setOrders(order);

            tracking.setStatus(status);

            tracking.setLocation("G7 Fashion");

            tracking.setNote(note);

            tracking.setUpdatedtime(new Date());

            trackingRepository.save(tracking);
        }

        return "redirect:/admin/orders/" + id;
    }

    // ================= DELETE =================

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Long id){

        Orders order =
                ordersRepository.findById(id)
                        .orElse(null);

        if(order != null){

            order.setIsdelete(1);

            ordersRepository.save(order);

            Order_tracking tracking =
                    new Order_tracking();

            tracking.setOrders(order);

            tracking.setStatus("DELETED");

            tracking.setLocation("Admin");

            tracking.setNote("Đơn hàng đã bị xóa");

            tracking.setUpdatedtime(new Date());

            trackingRepository.save(tracking);
        }

        return "redirect:/admin/orders";
    }

}