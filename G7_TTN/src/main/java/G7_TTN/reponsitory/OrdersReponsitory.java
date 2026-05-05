package G7_TTN.reponsitory;


import G7_TTN.entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrdersReponsitory  extends JpaRepository<Orders, Long> {
}
