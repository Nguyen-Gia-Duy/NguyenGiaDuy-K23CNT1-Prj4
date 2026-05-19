package G7_TTN.controller.user;

import G7_TTN.entity.*;
import G7_TTN.reponsitory.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/checkout")
public class CheckoutController {

    @Autowired
    private UserReponsitory usersRepository;

    @Autowired
    private CartReponsitory cartRepository;

    @Autowired
    private CartDetailsRepository cartDetailsRepository;

    @Autowired
    private OrdersReponsitory ordersRepository;

    @Autowired
    private OrdersDetailsRepository detailsRepository;

    @Autowired
    private PaymentMethodRepository paymentRepository;

    @Autowired
    private TransportMethodRepository transportRepository;

    @Autowired
    private OrderTrackingRepository trackingRepository;

    // ================= CHECKOUT PAGE =================

    @GetMapping
    public String checkout(Model model,
                           Authentication authentication){

        String username =
                authentication.getName();

        Users user =
                usersRepository.findByUsername(username)
                        .orElse(null);

        Cart cart = cartRepository
                .findByUser(user)
                .orElseGet(() -> {

                    Cart newCart = new Cart();

                    newCart.setUser(user);
                    newCart.setCreateddate(new Date());
                    newCart.setUpdateddate(new Date());
                    newCart.setIsactive(1);

                    return cartRepository.save(newCart);
                });

        List<Cart_details> cartDetails =
                cartDetailsRepository.findByCart(cart);

        BigDecimal total =
                BigDecimal.ZERO;

        for(Cart_details c : cartDetails){

            total = total.add(
                    c.getPrice().multiply(
                            BigDecimal.valueOf(c.getQty())
                    )
            );
        }

        model.addAttribute("cartDetails", cartDetails);

        model.addAttribute("total", total);

        model.addAttribute(
                "payments",
                paymentRepository.findAll()
        );

        model.addAttribute(
                "transports",
                transportRepository.findAll()
        );

        return "user/checkout";
    }

    // ================= PLACE ORDER =================

    @PostMapping("/place-order")
    public String placeOrder(@RequestParam String namereceiver,
                             @RequestParam String phone,
                             @RequestParam String address,
                             @RequestParam Long paymentId,
                             @RequestParam Long transportId,
                             Authentication authentication){

        // ================= USER =================

        String username =
                authentication.getName();

        Users user =
                usersRepository.findByUsername(username)
                        .orElse(null);

        // ================= CART =================

        Cart cart = cartRepository
                .findByUser(user)
                .orElseGet(() -> {

                    Cart newCart = new Cart();

                    newCart.setUser(user);
                    newCart.setCreateddate(new Date());
                    newCart.setUpdateddate(new Date());
                    newCart.setIsactive(1);

                    return cartRepository.save(newCart);
                });

        List<Cart_details> cartDetails =
                cartDetailsRepository.findByCart(cart);

        // ================= TOTAL =================

        BigDecimal total =
                BigDecimal.ZERO;

        for(Cart_details c : cartDetails){

            total = total.add(
                    c.getPrice().multiply(
                            BigDecimal.valueOf(c.getQty())
                    )
            );
        }

        // ================= CREATE ORDER =================

        Orders order =
                new Orders();

        order.setIdorders(
                "G7-" + UUID.randomUUID()
                        .toString()
                        .substring(0,8)
                        .toUpperCase()
        );

        order.setOrdersdate(new Date());

        order.setNamereceiver(namereceiver);

        order.setPhone(phone);

        order.setAddress(address);

        order.setStatus("PENDING");

        order.setTotalmoney(total);

        order.setIsactive(1);

        order.setIsdelete(0);

        order.setUser(user);

        // ================= PAYMENT =================

        Payment_method payment =
                paymentRepository.findById(paymentId)
                        .orElse(null);

        order.setPayment(payment);

        // ================= TRANSPORT =================

        Transport_method transport =
                transportRepository.findById(transportId)
                        .orElse(null);

        order.setTransport(transport);

        // SAVE ORDER

        ordersRepository.save(order);

        // ================= SAVE ORDER DETAILS =================

        for(Cart_details c : cartDetails){

            Orders_details od =
                    new Orders_details();

            od.setOrders(order);

            od.setProduct(c.getProduct());

            od.setQty(c.getQty());

            od.setPrice(c.getPrice());

            od.setTotal(
                    c.getPrice().multiply(
                            BigDecimal.valueOf(c.getQty())
                    )
            );

            od.setSize(c.getSize());

            od.setColor(c.getColor());

            detailsRepository.save(od);
        }

        // ================= TRACKING =================

        Order_tracking tracking =
                new Order_tracking();

        tracking.setOrders(order);

        tracking.setStatus("PENDING");

        tracking.setLocation("G7 Fashion");

        tracking.setNote(
                "Đơn hàng đang chờ xác nhận"
        );

        tracking.setUpdatedtime(new Date());

        trackingRepository.save(tracking);

        // ================= CLEAR CART =================

        cartDetailsRepository.deleteAll(cartDetails);

        // ================= SUCCESS =================

        return "redirect:/user/orders";
    }

}