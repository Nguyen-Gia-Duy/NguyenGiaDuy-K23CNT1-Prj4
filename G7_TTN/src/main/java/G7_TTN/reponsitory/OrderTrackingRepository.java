package G7_TTN.reponsitory;


import G7_TTN.entity.Order_tracking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderTrackingRepository  extends JpaRepository<Order_tracking, Long> {
}
