package G7_TTN.reponsitory;

import G7_TTN.entity.Cart;
import G7_TTN.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartReponsitory
        extends JpaRepository<Cart, Long> {

    // tìm cart theo user
    Optional<Cart> findByUser(Users user);

    // tìm cart active
    Optional<Cart> findByUserAndIsactive(
            Users user,
            Integer isactive
    );

}