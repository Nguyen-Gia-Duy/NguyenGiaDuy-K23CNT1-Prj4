package G7_TTN.reponsitory;

import G7_TTN.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CartDetailsRepository extends JpaRepository<Cart_details, Long> {

    List<Cart_details> findByCart(Cart cart);

    Optional<Cart_details> findByCartAndProductAndSizeAndColor(
            Cart cart,
            Product product,
            Size size,
            Color color
    );
}