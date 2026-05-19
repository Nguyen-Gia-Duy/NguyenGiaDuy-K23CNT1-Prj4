package G7_TTN.service;

import G7_TTN.entity.*;
import G7_TTN.reponsitory.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrdersReponsitory ordersRepository;
    private final OrdersDetailsRepository ordersDetailsRepository;
    private final UserReponsitory userRepository;
    private final PaymentMethodRepository paymentRepository;
    private final TransportMethodRepository transportRepository;
    private final CartService cartService;

    // ================= CREATE ORDER =================
    public void createOrder(
            String username,
            Long paymentId,
            Long transportId,
            String receiverName,
            String address,
            String phone
    ) {

        Users user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Payment_method payment = paymentRepository.findById(paymentId)
                .orElseThrow();

        Transport_method transport = transportRepository.findById(transportId)
                .orElseThrow();

        List<Cart_details> cartItems = cartService.getCartDetails(username);

        if (cartItems.isEmpty()) {
            throw new RuntimeException("Cart trống");
        }

        Orders order = new Orders();
        order.setUser(user);
        order.setPayment(payment);
        order.setTransport(transport);

        order.setOrdersdate(new Date());
        order.setStatus("PENDING");
        order.setIsactive(1);
        order.setIsdelete(0);

        order.setNamereceiver(receiverName);
        order.setAddress(address);
        order.setPhone(phone);

        order.setTotalmoney(cartService.getTotal(username));

        ordersRepository.save(order);

        // ================= ORDER DETAILS =================
        for (Cart_details item : cartItems) {

            Orders_details detail = new Orders_details();

            detail.setOrders(order);
            detail.setProduct(item.getProduct());
            detail.setSize(item.getSize());
            detail.setColor(item.getColor());

            detail.setQty(item.getQty());
            detail.setPrice(item.getPrice());
            detail.setTotal(item.getPrice().multiply(
                    java.math.BigDecimal.valueOf(item.getQty())
            ));

            ordersDetailsRepository.save(detail);
        }

        cartService.clearCart(username);
    }

    // ================= GET ORDERS USER =================
    public List<Orders> getOrdersByUsername(String username) {

        Users user = userRepository.findByUsername(username)
                .orElseThrow();

        return ordersRepository.findByUserId(user.getId());
    }

    // ================= ORDER DETAILS =================
    public List<Orders_details> getOrderDetails(Long orderId) {
        return ordersDetailsRepository.findByOrders_Id(orderId);
    }

    // ================= CHANGE STATUS =================
    public void updateStatus(Long orderId, String status) {

        Orders order = ordersRepository.findById(orderId)
                .orElseThrow();

        order.setStatus(status);
        order.setUpdateddate(new Date());

        ordersRepository.save(order);
    }
}