package G7_TTN.reponsitory;

import G7_TTN.entity.Order_tracking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderTrackingRepository extends JpaRepository<Order_tracking, Long> {

    List<Order_tracking> findByOrdersIdOrderByUpdatedtimeDesc(Long id);
}