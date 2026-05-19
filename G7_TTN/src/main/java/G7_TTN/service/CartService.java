package G7_TTN.service;

import G7_TTN.entity.*;
import G7_TTN.reponsitory.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartReponsitory cartRepository;
    private final CartDetailsRepository cartDetailRepository;
    private final ProductRepository productRepository;
    private final UserReponsitory userRepository;
    private final SizeReponsitory sizeRepository;
    private final ColorRepository colorRepository;

    // =========================================
    // LẤY HOẶC TẠO CART
    // =========================================
    public Cart getOrCreateCart(String username) {

        Users user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy user"));

        Optional<Cart> optionalCart =
                cartRepository.findByUserAndIsactive(user, 1);

        if (optionalCart.isPresent()) {
            return optionalCart.get();
        }

        Cart cart = new Cart();
        cart.setUser(user);
        cart.setCreateddate(new Date());
        cart.setUpdateddate(new Date());
        cart.setIsactive(1);

        return cartRepository.save(cart);
    }

    // =========================================
    // THÊM VÀO GIỎ (THEO SIZE + COLOR)
    // =========================================
    public void addToCart(
            String username,
            Long productId,
            Long sizeId,
            Long colorId,
            Integer qty
    ) {

        Cart cart = getOrCreateCart(username);

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy sản phẩm"));

        Size size = null;
        if (sizeId != null) {
            size = sizeRepository.findById(sizeId).orElse(null);
        }

        Color color = null;
        if (colorId != null) {
            color = colorRepository.findById(colorId).orElse(null);
        }

        // =====================================
        // CHECK TRÙNG ITEM (QUAN TRỌNG)
        // =====================================
        Optional<Cart_details> optionalDetail =
                cartDetailRepository.findByCartAndProductAndSizeAndColor(
                        cart, product, size, color
                );

        if (optionalDetail.isPresent()) {

            Cart_details detail = optionalDetail.get();

            detail.setQty(detail.getQty() + qty);
            detail.setPrice(calculatePrice(product));

            cartDetailRepository.save(detail);

        } else {

            Cart_details detail = new Cart_details();
            detail.setCart(cart);
            detail.setProduct(product);
            detail.setSize(size);
            detail.setColor(color);
            detail.setQty(qty);
            detail.setPrice(calculatePrice(product));

            cartDetailRepository.save(detail);
        }

        cart.setUpdateddate(new Date());
        cartRepository.save(cart);
    }

    // =========================================
    // TẠO CART DETAIL
    // =========================================
    private Cart_details createCartDetail(
            Cart cart,
            Product product,
            Size size,
            Color color,
            Integer qty
    ) {

        Cart_details detail = new Cart_details();

        detail.setCart(cart);
        detail.setProduct(product);
        detail.setSize(size);
        detail.setColor(color);
        detail.setQty(qty);
        detail.setPrice(calculatePrice(product));

        return detail;
    }

    // =========================================
    // TÍNH GIÁ SAU SALE
    // =========================================
    private BigDecimal calculatePrice(Product product) {

        BigDecimal totalPrice = product.getPrice();

        if (product.getSale() != null) {

            BigDecimal discount = totalPrice.multiply(
                            BigDecimal.valueOf(product.getSale().getDiscountpercent())
                    )
                    .divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);

            totalPrice = totalPrice.subtract(discount);
        }

        return totalPrice;
    }

    // =========================================
    // LẤY GIỎ HÀNG
    // =========================================
    public List<Cart_details> getCartDetails(String username) {

        Cart cart = getOrCreateCart(username);

        return cartDetailRepository.findByCart(cart);
    }

    // =========================================
    // TỔNG TIỀN
    // =========================================
    public BigDecimal getTotal(String username) {

        List<Cart_details> details = getCartDetails(username);

        BigDecimal total = BigDecimal.ZERO;

        for (Cart_details detail : details) {

            BigDecimal itemTotal =
                    detail.getPrice().multiply(
                            BigDecimal.valueOf(detail.getQty())
                    );

            total = total.add(itemTotal);
        }

        return total;
    }

    // =========================================
    // CẬP NHẬT SỐ LƯỢNG
    // =========================================
    public void updateQty(Long detailId, Integer qty) {

        Cart_details detail = cartDetailRepository.findById(detailId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy cart detail"));

        if (qty <= 0) {
            cartDetailRepository.delete(detail);
        } else {
            detail.setQty(qty);
            cartDetailRepository.save(detail);
        }
    }

    // =========================================
    // XOÁ ITEM
    // =========================================
    public void removeItem(Long detailId) {
        cartDetailRepository.deleteById(detailId);
    }

    // =========================================
    // XOÁ GIỎ
    // =========================================
    public void clearCart(String username) {

        Cart cart = getOrCreateCart(username);

        List<Cart_details> details =
                cartDetailRepository.findByCart(cart);

        cartDetailRepository.deleteAll(details);
    }

    public List<Cart_details> getCartItems(String username) {
        return getCartDetails(username);
    }
}