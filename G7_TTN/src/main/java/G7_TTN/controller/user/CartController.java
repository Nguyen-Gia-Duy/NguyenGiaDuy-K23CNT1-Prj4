package G7_TTN.controller.user;

import G7_TTN.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/cart")
public class CartController {

    private final CartService cartService;

    // ================= VIEW CART =================
    @GetMapping
    public String cart(Authentication authentication, Model model) {

        String username = authentication.getName();

        model.addAttribute(
                "items",
                cartService.getCartDetails(username)
        );

        model.addAttribute(
                "total",
                cartService.getTotal(username)
        );

        return "user/cart/index";
    }

    // ================= ADD TO CART (VARIANT FIXED) =================
    @GetMapping("/add/{productId}")
    public String addToCart(
            @PathVariable Long productId,
            @RequestParam(required = false) Long sizeId,
            @RequestParam(required = false) Long colorId,
            @RequestParam(defaultValue = "1") Integer qty,
            Authentication authentication
    ) {

        cartService.addToCart(
                authentication.getName(),
                productId,
                sizeId,
                colorId,
                qty
        );

        return "redirect:/cart";
    }
    // ================= UPDATE QTY =================
    @GetMapping("/update")
    public String updateQty(
            @RequestParam Long detailId,
            @RequestParam Integer qty
    ) {

        cartService.updateQty(detailId, qty);

        return "redirect:/cart";
    }

    // ================= DELETE ITEM =================
    @GetMapping("/delete/{detailId}")
    public String delete(@PathVariable Long detailId) {

        cartService.removeItem(detailId);

        return "redirect:/cart";
    }

    // ================= CLEAR CART =================
    @GetMapping("/clear")
    public String clear(Authentication authentication) {

        cartService.clearCart(authentication.getName());

        return "redirect:/cart";
    }
}